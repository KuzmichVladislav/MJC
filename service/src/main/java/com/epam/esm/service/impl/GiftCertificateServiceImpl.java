package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParameterDto;
import com.epam.esm.dto.PageWrapper;
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
import com.epam.esm.util.TotalPageCountCalculator;
import com.epam.esm.validator.GiftCertificateValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The Class GiftCertificateServiceImpl is the implementation of the {@link GiftCertificateService} interface.
 */
@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;
    private final TagService tagService;
    private final ModelMapper modelMapper;
    private final ListConverter listConverter;
    private final TotalPageCountCalculator totalPageCountCalculator;
    private final GiftCertificateValidator giftCertificateValidator;

    @Override
    @Transactional
    public GiftCertificateDto add(GiftCertificateDto giftCertificateDto) {
        giftCertificateValidator.checkGiftCertificateFields(giftCertificateDto);
        findAndSetTags(giftCertificateDto);
        return convertToGiftCertificateDto(giftCertificateDao.add(convertToGiftCertificateEntity(giftCertificateDto)));
    }

    @Override
    public GiftCertificateDto findById(long id) {
        return convertToGiftCertificateDto(giftCertificateDao.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ExceptionKey.CERTIFICATE_NOT_FOUND, String.valueOf(id))));
    }

    @Override
    public PageWrapper<GiftCertificateDto> findAll(GiftCertificateQueryParameterDto queryParameterDto) {
        List<GiftCertificateDto> giftCertificates =
                listConverter.convertList(giftCertificateDao.findAll(modelMapper.map(queryParameterDto, GiftCertificateQueryParameter.class)),
                        this::convertToGiftCertificateDto);
        long totalNumberOfItems = giftCertificateDao.getTotalNumberOfItems(modelMapper.map(queryParameterDto, GiftCertificateQueryParameter.class));
        int totalPage = totalPageCountCalculator.getTotalPage(queryParameterDto, totalNumberOfItems);
        return new PageWrapper<>(giftCertificates, totalPage);
    }

    @Override
    @Transactional
    public GiftCertificateDto update(long id, GiftCertificateDto giftCertificateDto) {
        if (giftCertificateDto.getId() != id && giftCertificateDto.getId() != 0) {
            throw new RequestValidationException(ExceptionKey.CERTIFICATE_ID_IS_NOT_VALID, String.valueOf(giftCertificateDto.getId()));
        } else {
            giftCertificateDto.setId(id);
        }
        GiftCertificateDto existing = findById(id);
        GiftCertificateDto updatedGiftCertificateDto = copyNonNullProperties(giftCertificateDto, existing);
        findAndSetTags(updatedGiftCertificateDto);
        giftCertificateDao.update(modelMapper.map(updatedGiftCertificateDto, GiftCertificate.class));
        return updatedGiftCertificateDto;
    }

    @Override
    public void removeById(long id) {
        GiftCertificate giftCertificate = giftCertificateDao.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ExceptionKey.CERTIFICATE_NOT_FOUND, String.valueOf(id)));
        if (giftCertificate.isRemoved()) {
            throw new ResourceNotFoundException(ExceptionKey.CERTIFICATE_NOT_FOUND, String.valueOf(id));
        }
        giftCertificateDao.remove(giftCertificate);
    }

    private GiftCertificateDto copyNonNullProperties(GiftCertificateDto giftCertificateDto, GiftCertificateDto existing) {
        if (giftCertificateDto.getName() != null) {
            existing.setName(giftCertificateDto.getName());
        }
        if (giftCertificateDto.getDescription() != null) {
            existing.setDescription(giftCertificateDto.getDescription());
        }
        if (giftCertificateDto.getDuration() != null) {
            existing.setDuration(giftCertificateDto.getDuration());
        }
        if (giftCertificateDto.getPrice() != null) {
            existing.setPrice(giftCertificateDto.getPrice());
        }
        if (giftCertificateDto.getTags() != null) {
            existing.setTags(giftCertificateDto.getTags());
        }
        return existing;
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
}
