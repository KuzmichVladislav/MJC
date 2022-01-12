package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParameterDto;
import com.epam.esm.dto.QueryParameterDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ListConverter;
import com.epam.esm.util.TotalPageCountCalculator;
import com.epam.esm.validator.GiftCertificateValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.hateoas.PagedModel;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class GiftCertificateServiceImplTest {

    @Mock
    private GiftCertificateDao giftCertificateDao;
    @Mock
    private TagService tagService;
    private Validator validator;
    private ListConverter listConverter;
    private ModelMapper modelMapper;
    private GiftCertificateService giftCertificateService;
    private GiftCertificateDto giftCertificateDto;
    private GiftCertificate giftCertificate;
    private TagDto tagDto;
    private TotalPageCountCalculator totalPageCountCalculator;
    private GiftCertificateQueryParameterDto queryParameter;
    private GiftCertificateValidator giftCertificateValidator;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ValidatorFactory config = Validation.buildDefaultValidatorFactory();
        validator = config.getValidator();
        listConverter = new ListConverter();
        totalPageCountCalculator = new TotalPageCountCalculator();
        modelMapper = new ModelMapper();
        giftCertificateValidator = new GiftCertificateValidator();
        modelMapper.getConfiguration()
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setSkipNullEnabled(true)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true);
        giftCertificateService = new GiftCertificateServiceImpl(giftCertificateDao,
                tagService, modelMapper, listConverter, totalPageCountCalculator, giftCertificateValidator);
        giftCertificateDto = GiftCertificateDto.builder()
                .id(1L)
                .name("name")
                .description("description")
                .price(new BigDecimal(1))
                .duration(1)
                .createDate(LocalDateTime.of(2021, Month.DECEMBER, 11, 20, 24, 43))
                .lastUpdateDate(LocalDateTime.of(2021, Month.DECEMBER, 11, 20, 24, 43))
                .tags(Collections.singletonList(new TagDto(1L, "name")))
                .build();
        giftCertificate = GiftCertificate.builder()
                .id(1L)
                .name("name")
                .description("description")
                .price(new BigDecimal(1))
                .duration(1)
                .createDate(LocalDateTime.of(2021, Month.DECEMBER, 11, 20, 24, 43))
                .lastUpdateDate(LocalDateTime.of(2021, Month.DECEMBER, 11, 20, 24, 43))
                .tags(Collections.singletonList(new Tag(1L, "name")))
                .build();
        tagDto = new TagDto(1L, "name");
        queryParameter = GiftCertificateQueryParameterDto.giftCertificateQueryParameterDtoBuilder()
                .name(Optional.ofNullable(null))
                .description(Optional.ofNullable(null))
                .tagNames(Optional.ofNullable(null))
                .sortParameter(GiftCertificateQueryParameterDto.SortParameter.NAME)
                .page(1)
                .size(10)
                .firstValue(1)
                .sortingDirection(QueryParameterDto.SortingDirection.ASC)
                .build();
    }

    @Test
    void testAdd_AllFieldsAreValid_CreatesGiftCertificate() {
        // Given
        when(giftCertificateDao.add(any())).thenReturn(giftCertificate);
        when(tagService.findByName("name")).thenReturn(Optional.ofNullable(tagDto));
        when(tagService.add(any())).thenReturn(tagDto);
        // When
        GiftCertificateDto result = giftCertificateService.add(giftCertificateDto);
        // Then
        Assertions.assertEquals(giftCertificateDto.getId(), result.getId());
        Assertions.assertEquals(giftCertificateDto.getName(), result.getName());
        Assertions.assertEquals(giftCertificateDto.getDuration(), result.getDuration());
        Assertions.assertEquals(giftCertificateDto.getDescription(), result.getDescription());
    }

    @ParameterizedTest
    @ValueSource(strings = {">name", "<name", "~name", "b", "Name with space", "NameMoreThen16Char"})
    void testAdd_InvalidName_ExceptionThrown(String name) {
        // Given
        giftCertificateDto.setName(name);
        // When
        Set<ConstraintViolation<GiftCertificateDto>> constraintViolations = validator
                .validate(giftCertificateDto);
        // Then
        Assertions.assertTrue(constraintViolations.size() > 0);
    }

    @ParameterizedTest
    @ValueSource(strings = {">description", "<description", "~description", "Description more then 250 characters " +
            "more then 250 characters more then 250 characters more then 250 characters more then 250 characters " +
            "more then 250 characters more then 250 characters more then 250 characters more then 250 characters " +
            "more then 250 characters more then 250 characters more then 250 characters more then 250 characters " +
            "more then 250 characters more then 250 characters more then 250 characters more then 250 characters " +
            "more then 250 characters more then 250 characters more then 250 characters more then 250 characters " +
            "more then 250 characters more then 250 characters more then 250 characters more then 250 characters"})
    void testAdd_InvalidDescription_ExceptionThrown(String description) {
        // Given
        giftCertificateDto.setDescription(description);
        // When
        Set<ConstraintViolation<GiftCertificateDto>> constraintViolations = validator
                .validate(giftCertificateDto);
        // Then
        Assertions.assertTrue(constraintViolations.size() > 0);
    }

    @ParameterizedTest
    @ValueSource(ints = {-500, -1, 1_000_001, 2_000_000})
    void testAdd_InvalidPrice_ExceptionThrown(int price) {
        // Given
        giftCertificateDto.setPrice(new BigDecimal(price));
        // When
        Set<ConstraintViolation<GiftCertificateDto>> constraintViolations = validator
                .validate(giftCertificateDto);
        // Then
        Assertions.assertTrue(constraintViolations.size() > 0);
    }

    @ParameterizedTest
    @ValueSource(ints = {-500, -1, 0, 367, 500})
    void testAdd_InvalidDuration_ExceptionThrown(int duration) {
        // Given
        giftCertificateDto.setDuration(duration);
        // When
        Set<ConstraintViolation<GiftCertificateDto>> constraintViolations = validator
                .validate(giftCertificateDto);
        // Then
        Assertions.assertTrue(constraintViolations.size() > 0);
    }

    @Test
    void testFindById_ValidId_findsGiftCertificate() {
        // Given
        when(giftCertificateDao.findById(1L)).thenReturn(Optional.ofNullable(giftCertificate));
        // When
        GiftCertificateDto result = giftCertificateService.findById(1L);
        // Then
        Assertions.assertEquals(giftCertificateDto, result);
    }

    @Test
    void testFindById_InvalidId_ExceptionThrown() {
        // Given
        // When
        // Then
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> giftCertificateService.findById(-10L));
    }

    @Test
    void testFindAll_GiftCertificatesExist_findsGiftCertificates() {
        // Given
        when(giftCertificateDao.findAll(any())).thenReturn(Collections.singletonList(giftCertificate));
        when(giftCertificateDao.getTotalNumberOfItems(any())).thenReturn(20L);
        // When
        PagedModel<GiftCertificateDto> result = giftCertificateService.findAll(queryParameter);
        // Then
        Assertions.assertEquals(Collections.singletonList(giftCertificateDto), new ArrayList<>(result.getContent()));
    }


    @Test
    void testUpdate_AllFieldsAreValid_CreatesGiftCertificate() {
        // Given
        GiftCertificate updatedGiftCertificate = GiftCertificate.builder()
                .id(1L)
                .name("newName")
                .description("New description")
                .price(new BigDecimal(1))
                .duration(1)
                .createDate(LocalDateTime.of(2021, Month.DECEMBER, 11, 20, 24, 43))
                .lastUpdateDate(LocalDateTime.of(2021, Month.DECEMBER, 11, 20, 24, 43))
                .tags(Collections.singletonList(new Tag(1L, "name")))
                .build();
        when(giftCertificateDao.findById(1L)).thenReturn(Optional.ofNullable(updatedGiftCertificate));
        when(tagService.findByName("name")).thenReturn(Optional.ofNullable(tagDto));
        when(tagService.add(tagDto)).thenReturn(tagDto);
        GiftCertificateDto updatedGiftCertificateDto = GiftCertificateDto.builder()
                .id(1L)
                .name("newName")
                .description("New description")
                .price(new BigDecimal(1))
                .duration(1)
                .createDate(LocalDateTime.of(2021, Month.DECEMBER, 11, 20, 24, 43))
                .lastUpdateDate(LocalDateTime.of(2021, Month.DECEMBER, 11, 20, 24, 43))
                .tags(Collections.singletonList(new TagDto(1L, "name")))
                .build();
        // When
        GiftCertificateDto result = giftCertificateService.update(1L, updatedGiftCertificateDto);
        // Then
        Assertions.assertNotEquals(giftCertificateDto, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {">name", "<name", "~name", "ab", "Name with space", "NameMoreThen16Char"})
    void testUpdate_InvalidName_ExceptionThrown(String name) {
        // Given
        // When
        // Then
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> giftCertificateService.update(1L, GiftCertificateDto.builder().name(name).build()));
    }

    @ParameterizedTest
    @ValueSource(strings = {">description", "<description", "~description", "Description more then 250 characters " +
            "more then 250 characters more then 250 characters more then 250 characters more then 250 characters " +
            "more then 250 characters more then 250 characters more then 250 characters more then 250 characters " +
            "more then 250 characters more then 250 characters more then 250 characters more then 250 characters " +
            "more then 250 characters more then 250 characters more then 250 characters more then 250 characters " +
            "more then 250 characters more then 250 characters more then 250 characters more then 250 characters " +
            "more then 250 characters more then 250 characters more then 250 characters more then 250 characters"})
    void testUpdate_InvalidDescription_ExceptionThrown(String description) {
        // Given
        // When
        // Then
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> giftCertificateService.update(1L, GiftCertificateDto.builder().name("name").description(description).build()));
    }

    @ParameterizedTest
    @ValueSource(ints = {-500, -1, 1_000_001, 2_000_000})
    void testUpdate_InvalidPrice_ExceptionThrown(int price) {
        // Given
        // When
        // Then
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> giftCertificateService.update(1L, GiftCertificateDto.builder()
                        .name("name")
                        .description("description")
                        .price(new BigDecimal(price))
                        .build()));
    }

    @ParameterizedTest
    @ValueSource(ints = {-500, -1, 0, 367, 500})
    void testUpdate_InvalidDuration_ExceptionThrown(int duration) {
        // Given
        // When
        // Then
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> giftCertificateService.update(1L, GiftCertificateDto.builder()
                        .name("name")
                        .price(new BigDecimal(1))
                        .description("description")
                        .duration(duration)
                        .build()));
    }

    @Test
    void testRemoveById_InvalidId_ExceptionThrown() {
        // Given
        // When
        // Then
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> giftCertificateService.removeById(-10L));
    }
}
