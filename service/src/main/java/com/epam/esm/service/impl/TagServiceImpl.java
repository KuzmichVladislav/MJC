package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.util.MapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;
    private final ModelMapper modelMapper;
    private final MapperUtil mapperUtilInstance;

    @Autowired
    public TagServiceImpl(TagDao tagDao, ModelMapper modelMapper, MapperUtil mapperUtilInstance) {
        this.tagDao = tagDao;
        this.modelMapper = modelMapper;
        this.mapperUtilInstance = mapperUtilInstance;
    }

    @Override
    public TagDto add(TagDto tagDto) {
        Tag tag = modelMapper.map(tagDto, Tag.class);
        tagDto.setId(tagDao.add(tag).getId());
        return tagDto;
    }

    @Override
    public TagDto findById(long id) {
        return convertToTagDto(tagDao.findById(id).orElse(null));// TODO: 12/7/2021
    }

    @Override
    public List<TagDto> findAll() {
        return mapperUtilInstance.convertList(tagDao.findAll(), this::convertToTagDto);
    }

    @Override
    public boolean removeById(long id) {
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
