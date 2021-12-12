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
import com.epam.esm.util.ListConvertor;
import com.epam.esm.validator.RequestValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;

    private final TagService tagService;

    private final ModelMapper modelMapper;

    private final ListConvertor mapperUtilInstance;

    private final RequestValidator requestValidator;

    private final TransactionTemplate transactionTemplate;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao,
                                      TagService tagService, ModelMapper modelMapper,
                                      ListConvertor mapperUtilInstance,
                                      RequestValidator requestValidator,
                                      PlatformTransactionManager transactionManager) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagService = tagService;
        this.modelMapper = modelMapper;
        this.mapperUtilInstance = mapperUtilInstance;
        this.requestValidator = requestValidator;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }


    @Override
    public GiftCertificateDto add(GiftCertificateDto giftCertificateDto) {
        TransactionCallback<GiftCertificateDto> transactionCallback = status -> {
            findAndSetTags(giftCertificateDto);
            giftCertificateValidation(giftCertificateDto);
            giftCertificateDto.setCreateDate(LocalDateTime.now());
            giftCertificateDto.setLastUpdateDate(LocalDateTime.now());
            giftCertificateDto.setId(giftCertificateDao
                    .add(convertToGiftCertificateEntity(giftCertificateDto)).getId());
            addToGiftCertificateTagInclude(giftCertificateDto);
            return giftCertificateDto;
        };
        return transactionTemplate.execute(transactionCallback);
    }

    @Override
    public GiftCertificateDto findById(long id) {
        TransactionCallback<GiftCertificateDto> transactionCallback =
                status -> convertToGiftCertificateDto(giftCertificateDao
                        .findById(id).orElseThrow(() ->
                                new ResourceNotFoundException(ExceptionKey.GIFT_CERTIFICATE_NOT_FOUND.getKey(),
                                        String.valueOf(id))));
        return transactionTemplate.execute(transactionCallback);
    }

    @Override
    public List<GiftCertificateDto> findAll() {
        TransactionCallback<List<GiftCertificateDto>> transactionCallback =
                status -> mapperUtilInstance.convertList(giftCertificateDao.findAll(),
                        this::convertToGiftCertificateDto);
        return transactionTemplate.execute(transactionCallback);
    }

    @Override
    public GiftCertificateDto update(GiftCertificateDto giftCertificateDto) {
        TransactionCallback<GiftCertificateDto> transactionCallback = status -> {
            long giftCertificateId = findById(giftCertificateDto.getId()).getId();
            giftCertificateValidation(giftCertificateDto);
            giftCertificateDto.setLastUpdateDate(LocalDateTime.now());
            giftCertificateDao.removeFromTableGiftCertificateTagInclude(giftCertificateId);
            findAndSetTags(giftCertificateDto);
            addToGiftCertificateTagInclude(giftCertificateDto);
            giftCertificateDao.update(modelMapper.map(giftCertificateDto, GiftCertificate.class));
            return giftCertificateDto;
        };
        return transactionTemplate.execute(transactionCallback);
    }

    @Override
    public List<TagDto> findTagsByCertificateId(long giftCertificateId) {
        return tagService.findByCertificateId(giftCertificateId);
    }

    @Override
    public List<GiftCertificateDto> findAll(RequestSqlParamDto requestParamsDto) {
        TransactionCallback<List<GiftCertificateDto>> transactionCallback = status -> {
            RequestSqlParam requestParam = modelMapper.map(requestParamsDto, RequestSqlParam.class);
            return mapperUtilInstance.convertList(giftCertificateDao.findAllSorted(requestParam),
                    this::convertToGiftCertificateDto);
        };
        return transactionTemplate.execute(transactionCallback);
    }

    @Override
    public boolean removeById(long id) {
        return giftCertificateDao.removeById(id);
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
        giftCertificate.setTags(mapperUtilInstance.convertList(giftCertificateDto.getTags(),
                tagDto -> modelMapper.map(tagDto, Tag.class)));
        return giftCertificate;
    }

    private GiftCertificateDto convertToGiftCertificateDto(GiftCertificate giftCertificate) {
        GiftCertificateDto giftCertificateDto = modelMapper.map(giftCertificate, GiftCertificateDto.class);
        giftCertificateDto.setTags(findTagsByCertificateId(giftCertificateDto.getId()));
        return giftCertificateDto;
    }

    private void giftCertificateValidation(GiftCertificateDto giftCertificateDto) {
        String name = giftCertificateDto.getName();
        if (name != null) {
            requestValidator.checkName(name);
        }
        Integer duration = giftCertificateDto.getDuration();
        if (duration != null) {
            requestValidator.checkDuration(duration);
        }
        BigDecimal price = giftCertificateDto.getPrice();
        if (price != null) {
            requestValidator.checkPrice(price);
        }
        List<TagDto> tagDtoList = giftCertificateDto.getTags();
        if (tagDtoList != null) {
            tagDtoList.stream()
                    .map(TagDto::getName)
                    .forEach(requestValidator::checkName);
        }
    }
}
