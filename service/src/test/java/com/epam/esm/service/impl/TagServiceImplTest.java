package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.RequestValidationException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class TagServiceImplTest {

    @Mock
    private TagRepository tagRepository;
    @Mock
    private GiftCertificateRepository giftCertificateRepository;
    private TagService tagService;
    private Tag tag;
    private TagDto tagDto;
    private Validator validator;
    private Pageable pageable;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ValidatorFactory config = Validation.buildDefaultValidatorFactory();
        validator = config.getValidator();
        ModelMapper modelMapper = new ModelMapper();
        tagService = new TagServiceImpl(modelMapper,
                giftCertificateRepository,
                tagRepository);
        tag = Tag.builder()
                .id(1L)
                .name("name")
                .build();
        tagDto = TagDto.builder()
                .id(1L)
                .name("name")
                .build();
        pageable = PageRequest.of(1, 20, Sort.Direction.ASC, "id");

    }

    @Test
    void testAdd_AllFieldsAreValid_CreatesTag() {
        // Given
        when(tagRepository.save(tag)).thenReturn(tag);
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
        when(tagRepository.findById(anyLong())).thenReturn(Optional.ofNullable(tag));
        // When
        TagDto result = tagService.findById(1L);
        // Then
        Assertions.assertEquals(tagDto, result);
    }

    @Test
    void testFindById_InvalidId_ExceptionThrown() {
        // Given
        // When
        // Then
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> tagService.findById(-1L));
    }

    @Test
    void testFindMostUsedUserTag_ValidId_findsTag() {
        // Given
        when(tagRepository.findMostUsedUserTag(anyLong())).thenReturn(Optional.ofNullable(tag));
        // When
        TagDto result = tagService.findMostUsedUserTag(1L);
        // Then
        Assertions.assertEquals(tagDto, result);
    }

    @Test
    void testFindMostUsedUserTag_InvalidId_ExceptionThrown() {
        // Given
        // When
        // Then
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> tagService.findMostUsedUserTag(-1L));
    }

    @Test
    void testFindAll_TagsExist_findsTags() {
        // Given
        when(tagRepository.findAll(pageable)).thenReturn(new PageImpl<>(Collections.singletonList(tag)));
        // When
        Page<TagDto> result = tagService.findAll(pageable);
        // Then
        Assertions.assertEquals(1, result.getContent().size());
    }

    @Test
    void testRemoveById_InvalidId_ExceptionThrown() {
        // Given
        when(tagRepository.findAll(pageable)).thenReturn(new PageImpl<>(Collections.singletonList(tag)));
        // When
        // Then
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> tagService.removeById(1L));
    }

    @Test
    void testRemoveById_Tag_BelongsToCertificate_ExceptionThrown() {
        // Given
        when(giftCertificateRepository.existsByTags_Id(1L)).thenReturn(true);
        // When
        // Then
        Assertions.assertThrows(RequestValidationException.class,
                () -> tagService.removeById(1L));
    }

    @Test
    void testFindByName_ValidId_findsTag() {
        // Given
        when(tagRepository.findByName("name")).thenReturn(Optional.ofNullable(tag));
        // When
        Optional<TagDto> result = tagService.findByName("name");
        // Then
        Assertions.assertEquals(Optional.ofNullable(tagDto), result);
    }
}
