package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.*;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateQueryParameter;
import com.epam.esm.entity.QueryParameter;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.RequestValidationException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ListConverter;
import com.epam.esm.util.TotalPageCountCalculator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The Class GiftCertificateServiceImpl is the implementation of the {@link GiftCertificateService} interface.
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;
    private final TagService tagService;
    private final ModelMapper modelMapper;
    private final ListConverter listConverter;
    private final TotalPageCountCalculator totalPageCountCalculator;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao,
                                      TagService tagService,
                                      ModelMapper modelMapper,
                                      ListConverter listConverter,
                                      TotalPageCountCalculator totalPageCountCalculator) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagService = tagService;
        this.modelMapper = modelMapper;
        this.listConverter = listConverter;
        this.totalPageCountCalculator = totalPageCountCalculator;
    }

    @Override
    @Transactional
    public GiftCertificateDto add(GiftCertificateDto giftCertificateDto) {
        checkGiftCertificateFields(giftCertificateDto);
        findAndSetTags(giftCertificateDto);
        return convertToGiftCertificateDto(giftCertificateDao.add(convertToGiftCertificateEntity(giftCertificateDto)));
    }

    @Override
    public GiftCertificateDto findById(long id) {
        Optional<GiftCertificate> byId = giftCertificateDao
                .findById(id);
        return convertToGiftCertificateDto(byId.orElseThrow(() ->
                new ResourceNotFoundException(ExceptionKey.CERTIFICATE_NOT_FOUND, String.valueOf(id))));
    }

    @Override
    public PageWrapper<GiftCertificateDto> findAll(GiftCertificateQueryParameterDto queryParameterDto) {
        long totalNumberOfItems = giftCertificateDao.getTotalNumberOfItems();
        int totalPage = totalPageCountCalculator.getTotalPage(queryParameterDto, totalNumberOfItems);
        List<GiftCertificateDto> giftCertificates =
                listConverter.convertList(giftCertificateDao.findAll(modelMapper.map(queryParameterDto, GiftCertificateQueryParameter.class)),
                        this::convertToGiftCertificateDto);
        return new PageWrapper<>(giftCertificates, totalPage);
    }

    @Override
    @Transactional
    public GiftCertificateDto update(long id, GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.setId(id);
        findAndSetTags(giftCertificateDto);
        GiftCertificateDto existing = findById(id);
        copyNonNullProperties(giftCertificateDto, existing);
        giftCertificateDao.update(modelMapper.map(existing, GiftCertificate.class));
        return findById(id);
    }

    @Override
    @Transactional
    public boolean removeById(long id) {
        return giftCertificateDao.remove(modelMapper.map(findById(id), GiftCertificate.class));
    }

    private void copyNonNullProperties(GiftCertificateDto giftCertificateDto, GiftCertificateDto existing) {
        giftCertificateDto.setCreateDate(existing.getCreateDate());
        if (giftCertificateDto.getName() == null) {
            giftCertificateDto.setName(existing.getName());
        }
        if (giftCertificateDto.getDescription() == null) {
            giftCertificateDto.setDescription(existing.getDescription());
        }
        if (giftCertificateDto.getDuration() == null) {
            giftCertificateDto.setDuration(existing.getDuration());
        }
        if (giftCertificateDto.getPrice() == null) {
            giftCertificateDto.setPrice(existing.getPrice());
        }
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
        return modelMapper.map(giftCertificate, GiftCertificateDto.class);
    }

    private void findAndSetTags(GiftCertificateDto giftCertificateDto) {
        List<TagDto> tags = giftCertificateDto.getTags();
        if (tags != null) {
            tags = tags.stream()
                    .map(t -> tagService.findByName(t.getName()).orElseGet(() -> tagService.add(t)))
                    .collect(Collectors.toList());
        }
        giftCertificateDto.setTags(tags);
    }

    private void checkGiftCertificateFields(GiftCertificateDto giftCertificateDto) {
        if (giftCertificateDto.getName() == null) {
            throw new RequestValidationException(ExceptionKey.TAG_NAME_MIGHT_NOT_BE_NULL);
        }
        if (giftCertificateDto.getDescription() == null) {
            throw new RequestValidationException(ExceptionKey.CERTIFICATE_DESCRIPTION_MIGHT_NOT_BE_NULL);
        }
        if (giftCertificateDto.getPrice() == null) {
            throw new RequestValidationException(ExceptionKey.CERTIFICATE_PRICE_MIGHT_NOT_BE_NULL);
        }
        if (giftCertificateDto.getDuration() == null) {
            throw new RequestValidationException(ExceptionKey.CERTIFICATE_DURATION_MIGHT_NOT_BE_NULL);
        }
    }
}
