package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.RequestValidationException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * The Class TagServiceImpl is the implementation of the {@link TagService} interface.
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final ModelMapper modelMapper;
    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;

    @Override
    @Transactional
    public TagDto add(TagDto tagDto) {
        String tagName = tagDto.getName();
        if (tagRepository.findByName(tagName).isEmpty()) {
            Tag tag = modelMapper.map(tagDto, Tag.class);
            tagDto.setId(tagRepository.save(tag).getId());
            return tagDto;
        } else {
            throw new RequestValidationException(ExceptionKey.TAG_EXISTS, tagName);
        }
    }

    @Override
    public TagDto findById(long id) {
        return convertToTagDto(tagRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ExceptionKey.TAG_NOT_FOUND, String.valueOf(id))));
    }


    @Override
    public TagDto findMostUsedUserTag(long id) {
        return convertToTagDto(tagRepository.findMostUsedUserTag(id).orElseThrow(() ->
                new ResourceNotFoundException(ExceptionKey.TAG_NOT_FOUND, String.valueOf(id))));
    }

    @Override
    @Transactional
    public void removeById(long id) {
        if (giftCertificateRepository.existsByTags_Id(id)) {
            throw new RequestValidationException(ExceptionKey.TAG_BELONGS_TO_CERTIFICATE, String.valueOf(id));
        }
        tagRepository.delete(tagRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ExceptionKey.TAG_NOT_FOUND, String.valueOf(id))));
    }

    @Override
    public Page<TagDto> findAll(Pageable pageable) {
        return tagRepository.findAll(pageable).map(this::convertToTagDto);
    }

    @Override
    public Optional<TagDto> findByName(String name) {
        return tagRepository.findByName(name).map(this::convertToTagDto);
    }

    private TagDto convertToTagDto(Tag tag) {
        return modelMapper.map(tag, TagDto.class);
    }
}
