package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.RequestValidationException;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ListConverter;
import com.epam.esm.validator.TagRequestValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class TagServiceImplTest {

    @Mock
    private TagDao tagDao;
    private ListConverter listConverter;
    private ModelMapper modelMapper;
    private TagRequestValidator tagRequestValidator;
    private TagService tagService;
    private Tag tag;
    private TagDto tagDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        listConverter = new ListConverter();
        modelMapper = new ModelMapper();
        tagRequestValidator = new TagRequestValidator();
        tagService = new TagServiceImpl(tagDao, modelMapper, listConverter, tagRequestValidator);
        tag = Tag.builder()
                .id(1L)
                .name("name")
                .build();
        tagDto = TagDto.builder()
                .id(1L)
                .name("name")
                .build();
    }

    @Test
    void testAdd() {
        when(tagDao.add(tag)).thenReturn(tag);
        TagDto result = tagService.add(tagDto);
        Assertions.assertEquals(tagDto, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {">name", "<name", "~name", "ab", "Name with space", "NameMoreThen16Char"})
    void testAddExceptionName(String name) {
        Assertions.assertThrows(RequestValidationException.class,
                () -> tagService.add(TagDto.builder().name(name).build()));
    }

    @Test
    void testFindById() {
        when(tagDao.findById(anyLong())).thenReturn(Optional.ofNullable(tag));
        TagDto result = tagService.findById(1L);
        Assertions.assertEquals(tagDto, result);
    }

    @Test
    void testFindAll() {
        when(tagDao.findAll()).thenReturn(Collections.singletonList(tag));
        List<TagDto> result = tagService.findAll();
        Assertions.assertEquals(Collections.singletonList(tagDto), result);
    }

    @Test
    void testRemoveById() {
        when(tagDao.removeById(1L)).thenReturn(true);
        boolean result = tagService.removeById(1L);
        Assertions.assertTrue(result);
    }

    @Test
    void testFindByCertificateId() {
        when(tagDao.findByCertificateId(1L)).thenReturn(Collections.singletonList(tag));
        List<TagDto> result = tagService.findByCertificateId(1L);
        Assertions.assertEquals(Collections.singletonList(tagDto), result);
    }

    @Test
    void testFindByName() {
        when(tagDao.findByName("name")).thenReturn(Optional.ofNullable(tag));
        Optional<TagDto> result = tagService.findByName("name");
        Assertions.assertEquals(Optional.ofNullable(tagDto), result);
    }
}