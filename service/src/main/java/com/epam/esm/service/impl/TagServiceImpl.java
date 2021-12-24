package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.RequestValidationException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ListConverter;
import com.epam.esm.validator.TagRequestValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * The Class GiftCertificateServiceImpl is the implementation of the {@link TagService} interface.
 */
@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;
    private final ModelMapper modelMapper;
    private final ListConverter listConverter;
    private final TagRequestValidator tagRequestValidator;

    @Autowired
    public TagServiceImpl(TagDao tagDao,
                          ModelMapper modelMapper,
                          ListConverter listConverter,
                          TagRequestValidator tagRequestValidator) {
        this.tagDao = tagDao;
        this.modelMapper = modelMapper;
        this.listConverter = listConverter;
        this.tagRequestValidator = tagRequestValidator;
    }

    @Override
    @Transactional
    public TagDto add(TagDto tagDto) {
        String tagName = tagDto.getName();
        if (findByName(tagName).isEmpty()) {
            tagRequestValidator.checkName(tagName);
            Tag tag = modelMapper.map(tagDto, Tag.class);
            tagDto.setId(tagDao.add(tag).getId());
            return tagDto;
        } else {
            throw new RequestValidationException(ExceptionKey.TAG_EXISTS.getKey(),
                    String.valueOf(tagName));
        }
    }

    @Override
    public TagDto findById(String id) {
        long longId;
        try {
            longId = Long.parseLong(id);
            tagRequestValidator.checkId(longId);
        }catch (NumberFormatException e){
            throw new RequestValidationException(ExceptionKey.CERTIFICATE_ID_IS_NOT_VALID.getKey(), String.valueOf(id));
        }
        return convertToTagDto(tagDao.findById(longId).orElseThrow(() ->
                new ResourceNotFoundException(ExceptionKey.TAG_NOT_FOUND.getKey(), id)));
    }

    @Override
    public List<TagDto> findAll() {
        return listConverter.convertList(tagDao.findAll(), this::convertToTagDto);
    }

    @Override
    @Transactional
    public boolean removeById(String id) {
        TagDto byId = findById(id);
        return tagDao.remove(modelMapper.map(byId, Tag.class));
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
