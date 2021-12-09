package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.util.MapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;
    private final TagService tagService;
    private final TagDao tagDao;
    private final ModelMapper modelMapper;
    private final MapperUtil mapperUtilInstance;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao,
                                      TagService tagService, TagDao tagDao,
                                      ModelMapper modelMapper, MapperUtil mapperUtilInstance) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagService = tagService;
        this.tagDao = tagDao;
        this.modelMapper = modelMapper;
        this.mapperUtilInstance = mapperUtilInstance;
    }

    @Override
    public GiftCertificateDto add(GiftCertificateDto giftCertificateDto) {
        // TODO: 12/8/2021 validate
        giftCertificateDto.setCreateDate(LocalDateTime.now());
        giftCertificateDto.setLastUpdateDate(LocalDateTime.now());
        GiftCertificate giftCertificate = giftCertificateDao.add(convertToGiftCertificateEntity(giftCertificateDto));
        long giftCertificateId = giftCertificate.getId();
        giftCertificateDto.setId(giftCertificateId);
        addGiftCertificateTags(giftCertificate, giftCertificateId);
        return giftCertificateDto;
    }

    @Override
    public GiftCertificateDto findById(long id) {
        GiftCertificate giftCertificate = giftCertificateDao
                .findById(id).orElse(null);// TODO: 12/7/2021
        return convertToGiftCertificateDto(giftCertificate);
    }

    @Override
    public List<GiftCertificateDto> findAll() {
        return mapperUtilInstance.convertList(giftCertificateDao.findAll(),
                this::convertToGiftCertificateDto);
    }

    @Override
    public GiftCertificateDto update(GiftCertificateDto giftCertificateDto) {
        long giftCertificateId = giftCertificateDto.getId();
        giftCertificateDto.setLastUpdateDate(LocalDateTime.now());
        giftCertificateDao.removeFromTableGiftCertificateIncludeTag(giftCertificateId);
        addGiftCertificateTags(convertToGiftCertificateEntity(giftCertificateDto), giftCertificateId);
        return giftCertificateDto;
    }

    @Override
    public List<TagDto> findByCertificateId(long giftCertificateId) {
        return tagService.findByCertificateId(giftCertificateId);
    }

    @Override
    public List<GiftCertificateDto> findAll(Optional<String> partOfName, Optional<List<String>> tagNames, Optional<List<String>> sortParams, Optional<String> sortOrder) {
        String partOfNamePostfix = "";
        String tagNamesPostfix = "";
        String orderParamPostfix = "";

        if (partOfName.isPresent()) {
            partOfNamePostfix = "WHERE gift_certificate.name LIKE '%" + partOfName.get() + "%'";
            tagNamesPostfix = tagNamesPostfixWitQuotes(tagNames, tagNamesPostfix, " AND (t.name = ");
        } else {
            tagNamesPostfix = tagNamesPostfixWitQuotes(tagNames, tagNamesPostfix, "WHERE (t.name = ");
        }
        if (sortParams.isPresent()) {
            String sortOrderParam = sortOrder.orElse("ASC");
            if (!sortOrderParam.equals("DESC") && !sortOrderParam.equals("ASC")) {
                sortOrderParam = "";
            }
            orderParamPostfix = String.format(" ORDER BY %s %s", String.join(", ",
                    sortParams.get()), sortOrderParam);
        }
        String sqlQueryPostfix = partOfNamePostfix + tagNamesPostfix + orderParamPostfix;
        System.out.println(sqlQueryPostfix);
        return mapperUtilInstance.convertList(giftCertificateDao.findAllSorted(sqlQueryPostfix),
                this::convertToGiftCertificateDto);
    }// FIXME: 12/9/2021

    private String tagNamesPostfixWitQuotes(Optional<List<String>> tagNames, String tagNamesPostfix, String s2) {
        if (tagNames.isPresent()) {
            List<String> tagNamesWitQuotes = tagNames.get().stream()
                    .map(s -> "'" + s + "'").collect(Collectors.toList());
            tagNamesPostfix = s2 +
                    String.join(" OR t.name = ", tagNamesWitQuotes) + ")";
        }
        return tagNamesPostfix;
    }

    @Override
    public boolean removeById(long id) {
        return giftCertificateDao.removeById(id);
    }

    private void addGiftCertificateTags(GiftCertificate giftCertificate, long giftCertificateId) {
        List<Tag> tagList = giftCertificate.getTagList();
        tagList.stream()
                .distinct()
                .map(tag -> tagService.findByName(tag.getName()).orElseGet(() ->
                        tagDao.add(tag)).getId())// FIXME: 12/8/2021 can I use tagDao?
                .forEach(id -> giftCertificateDao.addTagToCertificate(giftCertificateId, id));
    }

    private GiftCertificate convertToGiftCertificateEntity(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
        giftCertificate.setTagList(mapperUtilInstance.convertList(giftCertificateDto.getTagDtoList(),
                this::convertToTagEntity));
        return giftCertificate;
    }

    private Tag convertToTagEntity(TagDto tagDto) {
        return modelMapper.map(tagDto, Tag.class);
    }

    private GiftCertificateDto convertToGiftCertificateDto(GiftCertificate giftCertificate) {
        GiftCertificateDto giftCertificateDto = modelMapper.map(giftCertificate, GiftCertificateDto.class);
        giftCertificateDto.setTagDtoList(findByCertificateId(giftCertificateDto.getId()));
        return giftCertificateDto;
    }
}
