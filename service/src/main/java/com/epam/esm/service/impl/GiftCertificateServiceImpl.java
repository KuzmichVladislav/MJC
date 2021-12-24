package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParameterDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateQueryParameter;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.RequestValidationException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ListConverter;
import com.epam.esm.validator.GiftCertificateRequestValidator;
import com.epam.esm.validator.TagRequestValidator;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The Class GiftCertificateServiceImpl is the implementation of the {@link GiftCertificateService} interface.
 */
@Service
@NoArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private GiftCertificateDao giftCertificateDao;
    private TagService tagService;
    private ModelMapper modelMapper;
    private ListConverter listConverter;
    private GiftCertificateRequestValidator giftCertificateRequestValidator;
    private TagRequestValidator tagRequestValidator;

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
        findAndSetTags(giftCertificateDto);
        return convertToGiftCertificateDto(giftCertificateDao.add(convertToGiftCertificateEntity(giftCertificateDto)));
    }

    @Override
    public GiftCertificateDto findById(String id) {
        long longId;
        try {
            longId = Long.parseLong(id);
            giftCertificateRequestValidator.checkId(longId);
        } catch (NumberFormatException e) {
            throw new RequestValidationException(ExceptionKey.CERTIFICATE_ID_IS_NOT_VALID.getKey(), String.valueOf(id));
        }
        Optional<GiftCertificate> byId = giftCertificateDao
                .findById(longId);
        return convertToGiftCertificateDto(byId.orElseThrow(() ->
                new ResourceNotFoundException(ExceptionKey.CERTIFICATE_NOT_FOUND.getKey(), id)));
    }

    @Override
    public List<GiftCertificateDto> findAll() {
        return listConverter.convertList(giftCertificateDao.findAll(),
                this::convertToGiftCertificateDto);
    }

    @Override
    @Transactional
    public GiftCertificateDto update(String id, GiftCertificateDto giftCertificateDto) {
        long longId;
        try {
            longId = Long.parseLong(id);
            giftCertificateRequestValidator.checkId(longId);
        } catch (NumberFormatException e) {
            throw new RequestValidationException(ExceptionKey.CERTIFICATE_ID_IS_NOT_VALID.getKey(), String.valueOf(id));
        }
        giftCertificateDto.setId(longId);
        giftCertificateDto.setLastUpdateDate(LocalDateTime.now());
        findAndSetTags(giftCertificateDto);
        GiftCertificateDto existing = findById(id);
        copyNonNullProperties(giftCertificateDto, existing);
        giftCertificateDao.update(modelMapper.map(existing, GiftCertificate.class));
        return existing;
    }


    @Override
    public List<GiftCertificateDto> findByParameters(GiftCertificateQueryParameterDto requestParameterDto) {
        GiftCertificateQueryParameter requestParameter = modelMapper.map(requestParameterDto, GiftCertificateQueryParameter.class);
        return listConverter.convertList(giftCertificateDao.findByParameters(requestParameter),
                this::convertToGiftCertificateDto);
    }

    @Override
    @Transactional
    public boolean removeById(String id) {
        return giftCertificateDao.remove(modelMapper.map(findById(id), GiftCertificate.class));
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

    private void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
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
}
