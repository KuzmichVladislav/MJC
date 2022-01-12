package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.QueryParameterDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ListConverter;
import com.epam.esm.util.TotalPageCountCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.PagedModel;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class TagServiceImplTest {

    @Mock
    private TagDao tagDao;
    private ListConverter listConverter;
    private ModelMapper modelMapper;
    private TagService tagService;
    private Tag tag;
    private TagDto tagDto;
    private TotalPageCountCalculator totalPageCountCalculator;
    private QueryParameterDto queryParameter;
    private Validator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ValidatorFactory config = Validation.buildDefaultValidatorFactory();
        validator = config.getValidator();
        listConverter = new ListConverter();
        modelMapper = new ModelMapper();
        totalPageCountCalculator = new TotalPageCountCalculator();
        tagService = new TagServiceImpl(tagDao, modelMapper, listConverter, totalPageCountCalculator);
        tag = Tag.builder()
                .id(1L)
                .name("name")
                .build();
        tagDto = TagDto.builder()
                .id(1L)
                .name("name")
                .build();
        queryParameter = QueryParameterDto.builder()
                .page(1)
                .size(10)
                .firstValue(1)
                .sortingDirection(QueryParameterDto.SortingDirection.ASC)
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
    @ValueSource(strings = {">name", "<name", "~name", "a", "Name with space", "NameMoreThen16Char"})
    void testAdd_InvalidName_ExceptionThrown(String name) {
        // Given
        tagDto.setName(name);
        // When
        Set<ConstraintViolation<TagDto>> constraintViolations = validator
                .validate(tagDto);
        // Then
        Assertions.assertTrue(constraintViolations.size() > 0);
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
        when(tagDao.findAll(any())).thenReturn(Collections.singletonList(tag));
        when(tagDao.getTotalNumberOfItems()).thenReturn(20L);
        // When
        PagedModel<TagDto> result = tagService.findAll(queryParameter);
        // Then
        Assertions.assertEquals(Collections.singletonList(tagDto), new ArrayList<>(result.getContent()));
    }

    @Test
    void testRemoveById_InvalidId_ExceptionThrown() {
        // Given
        // When
        // Then
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> tagService.removeById(-10L));
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
