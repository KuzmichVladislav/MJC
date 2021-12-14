package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.RequestSqlParamDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.RequestSqlParam;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ListConverter;
import com.epam.esm.validator.GiftCertificateRequestValidator;
import com.epam.esm.validator.TagRequestValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;

    private final TagService tagService;

    private final ModelMapper modelMapper;

    private final ListConverter listConverter;

    private final GiftCertificateRequestValidator giftCertificateRequestValidator;

    private final TagRequestValidator tagRequestValidator;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao,
                                      TagService tagService,
                                      ModelMapper modelMapper,
                                      ListConverter listConverter,
                                      GiftCertificateRequestValidator giftCertificateRequestValidator,
                                      TagRequestValidator tagRequestValidator) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagService = tagService;
        this.modelMapper = modelMapper;
        this.listConverter = listConverter;
        this.giftCertificateRequestValidator = giftCertificateRequestValidator;
        this.tagRequestValidator = tagRequestValidator;
    }

    @Override
    @Transactional
    public GiftCertificateDto add(GiftCertificateDto giftCertificateDto) {
        giftCertificateRequestValidator.validateGiftCertificate(giftCertificateDto);
        tagRequestValidator.validateTags(giftCertificateDto);
        giftCertificateDto.setCreateDate(LocalDateTime.now());
        giftCertificateDto.setLastUpdateDate(LocalDateTime.now());
        giftCertificateDto.setId(giftCertificateDao
                .add(convertToGiftCertificateEntity(giftCertificateDto)).getId());
        if (giftCertificateDto.getTags() != null) {
            findAndSetTags(giftCertificateDto);
            addToGiftCertificateTagInclude(giftCertificateDto);
        }
        return giftCertificateDto;
    }

    @Override
    @Transactional
    public GiftCertificateDto findById(long id) {
        giftCertificateRequestValidator.checkId(id);
        return convertToGiftCertificateDto(giftCertificateDao
                .findById(id).orElseThrow(() ->
                        new ResourceNotFoundException(ExceptionKey.CERTIFICATE_NOT_FOUND.getKey(),
                                String.valueOf(id))));
    }

    @Override
    public List<GiftCertificateDto> findAll() {
        return listConverter.convertList(giftCertificateDao.findAll(),
                this::convertToGiftCertificateDto);
    }

    @Override
    @Transactional
    public GiftCertificateDto update(GiftCertificateDto giftCertificateDto) {
        long giftCertificateId = findById(giftCertificateDto.getId()).getId();
        giftCertificateRequestValidator.checkId(giftCertificateId);
        giftCertificateRequestValidator.validateGiftCertificate(giftCertificateDto);
        tagRequestValidator.validateTags(giftCertificateDto);
        giftCertificateDto.setLastUpdateDate(LocalDateTime.now());
        giftCertificateDao.removeFromTableGiftCertificateTagInclude(giftCertificateId);
        if (giftCertificateDto.getTags() != null) {
            findAndSetTags(giftCertificateDto);
            addToGiftCertificateTagInclude(giftCertificateDto);
        }
        giftCertificateDao.update(modelMapper.map(giftCertificateDto, GiftCertificate.class));
        return findById(giftCertificateId);
    }

    @Override
    public List<GiftCertificateDto> findByParameters(RequestSqlParamDto requestParamsDto) {
        RequestSqlParam requestParam = modelMapper.map(requestParamsDto, RequestSqlParam.class);
        return listConverter.convertList(giftCertificateDao.findByParameters(requestParam),
                this::convertToGiftCertificateDto);

    }

    @Override
    public boolean removeById(long id) {
        giftCertificateRequestValidator.checkId(id);
        boolean isRemoved = giftCertificateDao.removeById(id);
        if (!isRemoved) {
            throw new ResourceNotFoundException(ExceptionKey.CERTIFICATE_NOT_FOUND.getKey(),
                    String.valueOf(id));
        }
        return isRemoved;
    }

    private void findAndSetTags(GiftCertificateDto giftCertificateDto) {
        List<TagDto> tags = giftCertificateDto.getTags().stream()
                .distinct()
                .map(tag -> tagService.findByName(tag.getName()).orElseGet(() -> tagService.add(tag)))
                .collect(Collectors.toList());
        giftCertificateDto.setTags(tags);
    }

    private void addToGiftCertificateTagInclude(GiftCertificateDto giftCertificateDto) {
        long giftCertificateId = giftCertificateDto.getId();
        giftCertificateDto.getTags()
                .forEach(tag -> giftCertificateDao.addTagToCertificate(giftCertificateId, tag.getId()));
    }

    private GiftCertificate convertToGiftCertificateEntity(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
        if (giftCertificateDto.getTags() != null) {
            giftCertificate.setTags(listConverter.convertList(giftCertificateDto.getTags(),
                    tagDto -> modelMapper.map(tagDto, Tag.class)));
        }
        return giftCertificate;
    }

    private GiftCertificateDto convertToGiftCertificateDto(GiftCertificate giftCertificate) {
        GiftCertificateDto giftCertificateDto = modelMapper.map(giftCertificate, GiftCertificateDto.class);
        giftCertificateDto.setTags(findTagsByCertificateId(giftCertificateDto.getId()));
        return giftCertificateDto;
    }

    private List<TagDto> findTagsByCertificateId(long giftCertificateId) {
        return tagService.findByCertificateId(giftCertificateId);
    }
}
