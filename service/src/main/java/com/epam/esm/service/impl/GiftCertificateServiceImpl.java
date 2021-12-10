package com.epam.esm.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.RequestParamDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.util.MapperUtil;
import com.epam.esm.validator.RequestValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;

    private final TagService tagService;

    private final TagDao tagDao;

    private final ModelMapper modelMapper;

    private final MapperUtil mapperUtilInstance;

    private final RequestValidator requestValidator;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao,
            TagService tagService, TagDao tagDao,
            ModelMapper modelMapper, MapperUtil mapperUtilInstance,
            RequestValidator requestValidator) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagService = tagService;
        this.tagDao = tagDao;
        this.modelMapper = modelMapper;
        this.mapperUtilInstance = mapperUtilInstance;
        this.requestValidator = requestValidator;
    }

    @Override
    public GiftCertificateDto add(GiftCertificateDto giftCertificateDto) {
        findAndSetTags(giftCertificateDto);
        giftCertificateValidation(giftCertificateDto);
        giftCertificateDto.setCreateDate(LocalDateTime.now());
        giftCertificateDto.setLastUpdateDate(LocalDateTime.now());
        final long id = giftCertificateDao.add(modelMapper.map(giftCertificateDto, GiftCertificate.class)).getId();
        giftCertificateDto.setId(id);
        addToGiftCertificateIncludeTag(giftCertificateDto);
        //        GiftCertificate giftCertificate = giftCertificateDao
        //                .add(convertToGiftCertificateEntity(giftCertificateDto));
        //        long giftCertificateId = giftCertificate.getId();
        //        giftCertificateDto.setId(giftCertificateId);
//        addGiftCertificateTags(giftCertificate, giftCertificateId);
        return giftCertificateDto;
    }

    @Override
    public GiftCertificateDto findById(long id) {
        return convertToGiftCertificateDto(giftCertificateDao
                .findById(id).orElseThrow(() ->
                        new ResourceNotFoundException(ExceptionKey.GIFT_CERTIFICATE_NOT_FOUND.getKey(),
                                String.valueOf(id))));
    }

    @Override
    public List<GiftCertificateDto> findAll() {
        return mapperUtilInstance.convertList(giftCertificateDao.findAll(),
                this::convertToGiftCertificateDto);
    }

    @Override
    public GiftCertificateDto update(GiftCertificateDto giftCertificateDto) {
        long giftCertificateId = giftCertificateDto.getId();
        findById(giftCertificateId);
        giftCertificateValidation(giftCertificateDto);
        giftCertificateDto.setLastUpdateDate(LocalDateTime.now());
        giftCertificateDao.removeFromTableGiftCertificateIncludeTag(giftCertificateId);
// TODO: 12/10/2021          addGiftCertificateTags(convertToGiftCertificateEntity(giftCertificateDto), giftCertificateId);
        return giftCertificateDto;
    }

    @Override
    public List<TagDto> findTagsByCertificateId(long giftCertificateId) {
        return tagService.findByCertificateId(giftCertificateId);
    }

    @Override
    public List<GiftCertificateDto> findAll(RequestParamDto requestParams) {
        // TODO: 12/9/2021 validation?
        String sqlQueryPostfix = mapperUtilInstance.mapRequestParam(requestParams);
        return mapperUtilInstance.convertList(giftCertificateDao.findAllSorted(sqlQueryPostfix),
                this::convertToGiftCertificateDto);
    }

    @Override
    public boolean removeById(long id) {
        return giftCertificateDao.removeById(id);
    }

    private void findAndSetTags(GiftCertificateDto giftCertificateDto) {
        final var tagsFromDto = giftCertificateDto.getTagDtoList();
        final var tags = tagsFromDto.stream()
                .distinct()
                .map(tag -> tagService.findByName(tag.getName()).orElse(tagService.add(tag)))
                .collect(Collectors.toList());
        giftCertificateDto.setTagDtoList(tags);
    }

    private void addToGiftCertificateIncludeTag(GiftCertificateDto giftCertificateDto) {
        long giftCertificateId = giftCertificateDto.getId();
        giftCertificateDto.getTagDtoList()
                .forEach(tag -> giftCertificateDao.addTagToCertificate(giftCertificateId, tag.getId()));
    }

//    private void addGiftCertificateTags(GiftCertificate giftCertificate, long giftCertificateId) {
//        List<Tag> tagList = giftCertificate.getTagList();
//        tagList.stream()
//                .distinct()
//                .map(tag -> tagService.findByName(tag.getName()).orElseGet(() ->
//                        tagDao.add(tag)).getId())// FIXME: 12/8/2021 can I use tagDao?
//                .forEach(id -> giftCertificateDao.addTagToCertificate(giftCertificateId, id));
//    }

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
        giftCertificateDto.setTagDtoList(findTagsByCertificateId(giftCertificateDto.getId()));
        return giftCertificateDto;
    }

    private void giftCertificateValidation(GiftCertificateDto giftCertificateDto) {
        if (!requestValidator.checkName(giftCertificateDto.getName())) {
            throw new ValidationException(String.format("Field name %s is not valid",
                    giftCertificateDto.getName()));
        }
        if (!requestValidator.checkPrice(giftCertificateDto.getPrice().toString())) {
            throw new ValidationException(String.format("Field price %s is not valid",
                    giftCertificateDto.getPrice()));
        }
        if (!requestValidator.checkDuration(giftCertificateDto.getDuration())) {
            throw new ValidationException(String.format("Field duration %d is not valid",
                    giftCertificateDto.getDuration()));
        }
        if (giftCertificateDto.getTagDtoList().stream().anyMatch(t ->
                !requestValidator.checkName(t.getName()))) {
            throw new ValidationException("Tag names is not valid");
        }
    }
}
