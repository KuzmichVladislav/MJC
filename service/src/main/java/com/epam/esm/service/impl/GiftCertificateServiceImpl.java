package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParameterDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateQueryParameter;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.RequestValidationException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.GiftCertificateValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The Class GiftCertificateServiceImpl is the implementation of the {@link GiftCertificateService} interface.
 */
@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final TagService tagService;
    private final ModelMapper modelMapper;
    private final GiftCertificateValidator giftCertificateValidator;
    private final GiftCertificateRepository giftCertificateRepository;

    @Override
    @Transactional
    public GiftCertificateDto add(GiftCertificateDto giftCertificateDto) {
        giftCertificateValidator.checkGiftCertificateFields(giftCertificateDto);
        findAndSetTags(giftCertificateDto);
        return convertToGiftCertificateDto(giftCertificateRepository.save(convertToGiftCertificateEntity(giftCertificateDto)));
    }

    @Override
    public GiftCertificateDto findById(long id) {
        return convertToGiftCertificateDto(giftCertificateRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ExceptionKey.CERTIFICATE_NOT_FOUND, String.valueOf(id))));
    }

    @Override
    public PagedModel<GiftCertificateDto> findAll(GiftCertificateQueryParameterDto giftCertificateQueryParameterDto) {
        long totalNumberOfItems = giftCertificateRepository.getTotalNumberOfItems(modelMapper.map(giftCertificateQueryParameterDto,
                GiftCertificateQueryParameter.class));
        if (totalNumberOfItems == 0) {
            return PagedModel.of(new ArrayList<>(), new PagedModel.PageMetadata(0, 1, 0, 1));
        }
        int totalPage = getTotalPage(giftCertificateQueryParameterDto, totalNumberOfItems);
        List<GiftCertificateDto> giftCertificates =
                convertList(giftCertificateRepository.findAllGiftCertificates(modelMapper.map(giftCertificateQueryParameterDto,
                        GiftCertificateQueryParameter.class)),
                        this::convertToGiftCertificateDto);
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(giftCertificateQueryParameterDto.getSize(),
                giftCertificateQueryParameterDto.getPage(), totalNumberOfItems, totalPage);
        return PagedModel.of(giftCertificates, pageMetadata);
    }

    @Override
    @Transactional
    public GiftCertificateDto update(long id, GiftCertificateDto giftCertificateDto) {
        if (giftCertificateDto.getId() != id && giftCertificateDto.getId() != 0) {
            throw new RequestValidationException(ExceptionKey.CERTIFICATE_ID_DOES_NOT_MATCH,
                    String.valueOf(giftCertificateDto.getId()));
        } else {
            giftCertificateDto.setId(id);
        }
        GiftCertificateDto existing = findById(id);
        GiftCertificateDto updatedGiftCertificateDto = copyNonNullProperties(giftCertificateDto, existing);
        findAndSetTags(updatedGiftCertificateDto);
        giftCertificateRepository.save(modelMapper.map(updatedGiftCertificateDto, GiftCertificate.class));
        return updatedGiftCertificateDto;
    }

    @Override
    @Transactional
    public void removeById(long id) {
        GiftCertificate giftCertificate = giftCertificateRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ExceptionKey.CERTIFICATE_NOT_FOUND, String.valueOf(id)));
        if (giftCertificate.isRemoved()) {
            throw new ResourceNotFoundException(ExceptionKey.CERTIFICATE_NOT_FOUND, String.valueOf(id));
        }
        giftCertificateRepository.delete(giftCertificate);
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
            giftCertificate.setTags(convertList(giftCertificateDto.getTags(),
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

    private int getTotalPage(GiftCertificateQueryParameterDto giftCertificateQueryParameterDto, long totalNumberOfItems) {
        int page = giftCertificateQueryParameterDto.getPage();
        int size = giftCertificateQueryParameterDto.getSize();
        int totalPage = (int) Math.ceil(totalNumberOfItems / (double) size);
        if (page > totalPage) {
            throw new ResourceNotFoundException(ExceptionKey.SPECIFIED_PAGE_DOES_NOT_EXIST, String.valueOf(page));
        }
        giftCertificateQueryParameterDto.setFirstValue(page * size);
        return totalPage - 1;
    }

    private <R, E> List<R> convertList(List<E> list, Function<E, R> converter) {
        return list.stream().map(converter).collect(Collectors.toList());
    }
}
