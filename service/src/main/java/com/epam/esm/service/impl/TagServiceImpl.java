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
    private final ListConvertor listConvertor;
    private final RequestValidator requestValidator;

    @Autowired
    public TagServiceImpl(TagDao tagDao,
                          ModelMapper modelMapper,
                          ListConvertor listConvertor,
                          RequestValidator requestValidator) {
        this.tagDao = tagDao;
        this.modelMapper = modelMapper;
        this.listConvertor = listConvertor;
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
        return listConvertor.convertList(tagDao.findAll(), this::convertToTagDto);
    }

    @Override
    public boolean removeById(long id) {
        requestValidator.checkId(id);
        boolean isRemoved = tagDao.removeById(id);
        if(!isRemoved){
            throw new ResourceNotFoundException(ExceptionKey.TAG_NOT_FOUND.getKey(), String.valueOf(id));
        }
        return isRemoved;
    }

    @Override
    public List<TagDto> findByCertificateId(long giftCertificateId) {
        return listConvertor.convertList(tagDao.findByCertificateId(giftCertificateId),
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
