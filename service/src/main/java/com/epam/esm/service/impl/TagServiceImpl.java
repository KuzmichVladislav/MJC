package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.RequestValidationException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ListConvertor;
import com.epam.esm.validator.RequestValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;
    private final ModelMapper modelMapper;
    private final ListConvertor mapperUtilInstance;
    private final RequestValidator requestValidator;

    @Autowired
    public TagServiceImpl(TagDao tagDao,
                          ModelMapper modelMapper,
                          ListConvertor mapperUtilInstance,
                          RequestValidator requestValidator) {
        this.tagDao = tagDao;
        this.modelMapper = modelMapper;
        this.mapperUtilInstance = mapperUtilInstance;
        this.requestValidator = requestValidator;
    }

    @Override
    public TagDto add(TagDto tagDto) {
        String tagName = tagDto.getName();
        if(findByName(tagName).isEmpty()) {
            Tag tag = modelMapper.map(tagDto, Tag.class);
            tagDto.setId(tagDao.add(tag).getId());
            return tagDto;
        }else{
            // TODO: 12/13/2021 400!!
            throw new RequestValidationException(ExceptionKey.TAG_EXISTS.getKey(),
                    String.valueOf(tagName));
        }
    }

    @Override
    public TagDto findById(long id) {
        requestValidator.checkId(id);
        return convertToTagDto(tagDao.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ExceptionKey.TAG_NOT_FOUND.getKey(),
                        String.valueOf(id))));
    }

    @Override
    public List<TagDto> findAll() {
        return mapperUtilInstance.convertList(tagDao.findAll(), this::convertToTagDto);
    }

    @Override
    public boolean removeById(long id) {
        // TODO: 12/13/2021 404!!!
        requestValidator.checkId(id);
        return tagDao.removeById(id);
    }

    @Override
    public List<TagDto> findByCertificateId(long giftCertificateId) {
        return mapperUtilInstance.convertList(tagDao.findByCertificateId(giftCertificateId),
                this::convertToTagDto);
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
