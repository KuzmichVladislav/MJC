package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.QueryParameterDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.QueryParameter;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.RequestValidationException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ListConverter;
import com.epam.esm.util.TotalPageCountCalculator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * The Class TagServiceImpl is the implementation of the {@link TagService} interface.
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;
    private final ModelMapper modelMapper;
    private final ListConverter listConverter;
    private final TotalPageCountCalculator totalPageCountCalculator;

    @Override
    @Transactional
    public TagDto add(TagDto tagDto) {
        String tagName = tagDto.getName();
        if (findByName(tagName).isEmpty()) {
            Tag tag = modelMapper.map(tagDto, Tag.class);
            tagDto.setId(tagDao.add(tag).getId());
            return tagDto;
        } else {
            throw new RequestValidationException(ExceptionKey.TAG_EXISTS, tagName);
        }
    }

    @Override
    public TagDto findById(long id) {
        return convertToTagDto(tagDao.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ExceptionKey.TAG_NOT_FOUND, String.valueOf(id))));
    }


    @Override
    public TagDto findMostUsedTag(long id) {
        return modelMapper.map(tagDao.findMostUsedTag(id).orElseThrow(() ->
                new ResourceNotFoundException(ExceptionKey.TAG_NOT_FOUND, String.valueOf(id))), TagDto.class);
    }

    @Override
    public PagedModel<TagDto> findAll(QueryParameterDto queryParameterDto) {
        long totalNumberOfItems = tagDao.getTotalNumberOfItems();
        int totalPage = totalPageCountCalculator.getTotalPage(queryParameterDto, totalNumberOfItems);
        List<TagDto> tags = listConverter.convertList(tagDao.findAll(modelMapper
                .map(queryParameterDto, QueryParameter.class)), this::convertToTagDto);
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(queryParameterDto.getSize(),
                queryParameterDto.getPage(), totalNumberOfItems, totalPage);
        return PagedModel.of(tags, pageMetadata);
    }

    @Override
    @Transactional
    public void removeById(long id) {
        if (tagDao.isPartOfGiftCertificate(id)) {
            throw new RequestValidationException(ExceptionKey.TAG_BELONGS_TO_CERTIFICATE, String.valueOf(id));
        }
        tagDao.remove(tagDao.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ExceptionKey.TAG_NOT_FOUND, String.valueOf(id))));
    }

    @Override
    public Optional<TagDto> findByName(String name) {
        return tagDao.findByName(name)
                .map(tag -> modelMapper.map(tag, TagDto.class));
    }

    private TagDto convertToTagDto(Tag tag) {
        return modelMapper.map(tag, TagDto.class);
    }
}
