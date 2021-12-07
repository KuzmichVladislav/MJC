package com.epam.esm.service.impl;

import com.epam.esm.configuration.MapperUtil;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public TagDto add(TagDto tagDto) {
        Tag tag = convertToTagEntity(tagDto);
        tagDto.setId(tagDao.add(tag).getId());
        return tagDto;
    }

    @Override
    public TagDto findById(long id) {
        return convertToTagDto(tagDao.findById(id).orElse(null));// TODO: 12/7/2021
    }

    @Override
    public List<TagDto> findAll() {
        return MapperUtil.convertList(tagDao.findAll(), this::convertToTagDto);
    }

    @Override
    public boolean removeById(long id) {
        return tagDao.removeById(id);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return tagDao.findTagByName(name);
    }

    private Tag convertToTagEntity(TagDto tagDto) {
        return modelMapper.map(tagDto, Tag.class);
    }

    private TagDto convertToTagDto(Tag tag) {
        return modelMapper.map(tag, TagDto.class);
    }
}
