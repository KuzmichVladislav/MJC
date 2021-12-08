package com.epam.esm.service.impl;

import com.epam.esm.util.MapperUtil;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;
    @Autowired
    private TagService tagService;
    @Autowired
    private TagDao tagDao;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MapperUtil mapperUtilInstance;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    public GiftCertificateDto add(GiftCertificateDto giftCertificateDto) {
        // findAndSetTags - GC with tags
        // set dates
        // validate
        GiftCertificate giftCertificate = convertToGiftCertificateEntity(giftCertificateDto);
        long giftCertificateId = giftCertificateDao.add(giftCertificate).getId();
        addGiftCertificateTags(giftCertificateDto, giftCertificateId);
        // GC.setId(gcCId)
        // return giftCertificateDto
        return convertToGiftCertificateDto(giftCertificate);// TODO: 12/7/2021 date is null?
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
        giftCertificateDao.removeFromTableGiftCertificateIncludeTag(giftCertificateId);
        addGiftCertificateTags(giftCertificateDto, giftCertificateId);
        GiftCertificate giftCertificate = convertToGiftCertificateEntity(giftCertificateDto);
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        return convertToGiftCertificateDto(giftCertificateDao.update(giftCertificate));
    }

    @Override
    public List<TagDto> findByCertificateId(long giftCertificateId) {
        return mapperUtilInstance.convertList(tagDao.findByCertificateId(giftCertificateId), this::convertToTagDto);
    }

    @Override
    public boolean removeById(long id) {
        return giftCertificateDao.removeById(id);
    }

    private void addGiftCertificateTags(GiftCertificateDto giftCertificateDto, long giftCertificateId) {
        List<Tag> tagList = mapperUtilInstance.convertList(giftCertificateDto.getTagDtoList(),
                this::convertToTagEntity); // TODO: 12/8/2021
        List<Tag> existingTags = tagDao.findAll();
        tagList.stream()
                .distinct()
                .filter(e -> !existingTags.contains(e))
                .map(tag -> tagService.findByName(tag.getName()).orElseGet(() ->
                        convertToTagEntity(tagService.add(convertToTagDto(tag)))).getId())
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

    private TagDto convertToTagDto(Tag tag) {
        return modelMapper.map(tag, TagDto.class);
    }
}
