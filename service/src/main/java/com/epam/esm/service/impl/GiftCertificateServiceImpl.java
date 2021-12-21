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
import org.apache.commons.lang3.math.NumberUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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
        giftCertificateDto.setId(giftCertificateDao
                .add(convertToGiftCertificateEntity(giftCertificateDto)).getId());
        if (giftCertificateDto.getTags() != null) {
            findAndSetTags(giftCertificateDto);
            addToGiftCertificateTagInclude(giftCertificateDto);
        }
        return giftCertificateDto;
    }

    @Override
    public GiftCertificateDto findById(String id) {
        long longId;
            try {
                longId = Long.parseLong(id);
                giftCertificateRequestValidator.checkId(longId);
            }catch (NumberFormatException e){
                throw new RequestValidationException(ExceptionKey.CERTIFICATE_ID_IS_NOT_VALID.getKey(), String.valueOf(id));
            }
        return convertToGiftCertificateDto(giftCertificateDao
                .findById(longId).orElseThrow(() ->
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
    public GiftCertificateDto update(String id, GiftCertificateDto giftCertificateDto) {
        long longId;
        try {
            longId = Long.parseLong(id);
            giftCertificateRequestValidator.checkId(longId);
        }catch (NumberFormatException e){
            throw new RequestValidationException(ExceptionKey.CERTIFICATE_ID_IS_NOT_VALID.getKey(), String.valueOf(id));
        }        giftCertificateRequestValidator.validateGiftCertificateForUpdate(giftCertificateDto);
        tagRequestValidator.validateTags(giftCertificateDto);
        giftCertificateDto.setId(longId);
        giftCertificateDto.setLastUpdateDate(LocalDateTime.now());
        giftCertificateDao.removeFromTableGiftCertificateTagInclude(longId);
        if (giftCertificateDto.getTags() != null) {
            findAndSetTags(giftCertificateDto);
            addToGiftCertificateTagInclude(giftCertificateDto);
        }
        giftCertificateDao.update(modelMapper.map(giftCertificateDto, GiftCertificate.class));
        return findById(id);
    }

    @Override
    public List<GiftCertificateDto> findByParameters(GiftCertificateQueryParameterDto requestParameterDto) {
        GiftCertificateQueryParameter requestParameter = modelMapper.map(requestParameterDto, GiftCertificateQueryParameter.class);
        return listConverter.convertList(giftCertificateDao.findByParameters(requestParameter),
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
