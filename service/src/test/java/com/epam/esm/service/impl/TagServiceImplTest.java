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
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

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
    void testAdd_AllFieldsAreValid_CreatesTag() {
        // Given
        when(tagDao.add(tag)).thenReturn(tag);
        // When
        TagDto result = tagService.add(tagDto);
        // Then
        Assertions.assertEquals(tagDto, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {">name", "<name", "~name", "ab", "Name with space", "NameMoreThen16Char"})
    void testAdd_InvalidName_ExceptionThrown(String name) {
        // Given
        // When
        // Then
        Assertions.assertThrows(RequestValidationException.class,
                () -> tagService.add(TagDto.builder().name(name).build()));
    }

    @Test
    void testFindById_ValidId_findsTag() {
        // Given
        when(tagDao.findById(anyLong())).thenReturn(Optional.ofNullable(tag));
        // When
        TagDto result = tagService.findById(1L);
        // Then
        Assertions.assertEquals(tagDto, result);
    }

    @Test
    void testFindAll_TagsExist_findsTags() {
        // Given
        when(tagDao.findAll()).thenReturn(Collections.singletonList(tag));
        // When
        List<TagDto> result = tagService.findAll();
        // Then
        Assertions.assertEquals(Collections.singletonList(tagDto), result);
    }

    @Test
    void testRemoveById_ValidId_True() {
        // Given
        when(tagDao.removeById(1L)).thenReturn(true);
        // When
        boolean result = tagService.removeById(1L);
        // Then
        Assertions.assertTrue(result);
    }

    @Test
    void testRemoveById_InvalidId_ExceptionThrown() {
        // Given
        // When
        // Then
        Assertions.assertThrows(RequestValidationException.class,
                () -> tagService.removeById(-10L));
    }

    @Test
    void testFindByCertificateId_ValidId_findsTag() {
        // Given
        when(tagDao.findByCertificateId(1L)).thenReturn(Collections.singletonList(tag));
        // When
        List<TagDto> result = tagService.findByCertificateId(1L);
        // Then
        Assertions.assertEquals(Collections.singletonList(tagDto), result);
    }

    @Test
    void testFindByName_ValidId_findsTag() {
        // Given
        when(tagDao.findByName("name")).thenReturn(Optional.ofNullable(tag));
        // When
        Optional<TagDto> result = tagService.findByName("name");
        // Then
        Assertions.assertEquals(Optional.ofNullable(tagDto), result);
    }
}
