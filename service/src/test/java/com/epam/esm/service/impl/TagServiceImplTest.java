package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.ListConvertor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class TagServiceImplTest {

    @Mock
    TagDao tagDao;
    @Mock
    ModelMapper modelMapper = new ModelMapper();
    @Mock
    ListConvertor mapperUtilInstance;
    @InjectMocks
    TagServiceImpl tagServiceImpl;
    Tag tag;
    TagDto tagDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tag = Tag.builder()
                .id(1L)
                .name("name")
                .build();
        tagDto = TagDto.builder()
                .id(1L)
                .name("name")
                .build();
        when(modelMapper.map(any(Tag.class), any())).thenReturn(tagDto);
        when(modelMapper.map(any(TagDto.class), any())).thenReturn(tag);
    }

    @Test
    void testAdd() {
        when(tagDao.add(tag)).thenReturn(tag);
        TagDto result = tagServiceImpl.add(tagDto);
        Assertions.assertEquals(tagDto, result);
    }

    @Test
    void testFindById() {
        when(tagServiceImpl.findById(1L)).thenReturn(tagDto);
        TagDto result = tagServiceImpl.findById(1L);
        Assertions.assertEquals(tagDto, result);
    }

    @Test
    void testFindAll() {
        when(tagDao.findAll()).thenReturn(Collections.singletonList(tag));
        when(mapperUtilInstance.convertList(any(), any())).thenReturn(Collections.singletonList(tagDto));
        List<TagDto> result = tagServiceImpl.findAll();
        Assertions.assertEquals(Collections.singletonList(tagDto), result);
    }

    @Test
    void testRemoveById() {
        when(tagDao.removeById(1L)).thenReturn(true);
        boolean result = tagServiceImpl.removeById(1L);
        Assertions.assertTrue(result);
    }

    @Test
    void testFindByCertificateId() {
        when(tagDao.findByCertificateId(1L)).thenReturn(Collections.singletonList(tag));
        when(mapperUtilInstance.convertList(any(), any())).thenReturn(Collections.singletonList(tagDto));
        List<TagDto> result = tagServiceImpl.findByCertificateId(1L);
        Assertions.assertEquals(Collections.singletonList(tagDto), result);
    }

    @Test
    void testFindByName() {
        when(tagDao.findByName("name")).thenReturn(Optional.ofNullable(tag));
        Optional<TagDto> result = tagServiceImpl.findByName("name");
        Assertions.assertEquals(Optional.ofNullable(tagDto), result);
    }
}