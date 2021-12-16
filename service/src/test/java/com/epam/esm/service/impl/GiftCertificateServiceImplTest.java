package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.RequestValidationException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ListConverter;
import com.epam.esm.validator.GiftCertificateRequestValidator;
import com.epam.esm.validator.TagRequestValidator;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class GiftCertificateServiceImplTest {

    @Mock
    private GiftCertificateDao giftCertificateDao;
    @Mock
    private TagService tagService;
    private ListConverter listConverter;
    private ModelMapper modelMapper;
    private GiftCertificateRequestValidator giftCertificateRequestValidator;
    private TagRequestValidator tagRequestValidator;
    private GiftCertificateService giftCertificateService;
    private GiftCertificateDto giftCertificateDto;
    private GiftCertificate giftCertificate;
    private TagDto tagDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        listConverter = new ListConverter();
        modelMapper = new ModelMapper();
        giftCertificateRequestValidator = new GiftCertificateRequestValidator();
        tagRequestValidator = new TagRequestValidator();
        modelMapper.getConfiguration()
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setSkipNullEnabled(true)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true);
        giftCertificateService = new GiftCertificateServiceImpl(giftCertificateDao,
                tagService, modelMapper, listConverter, giftCertificateRequestValidator, tagRequestValidator);
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
    }

    @Test
    void teatAdd_AllFieldsAreValid_CreatesGiftCertificate() {
        when(giftCertificateDao.add(any())).thenReturn(giftCertificate);
        when(tagService.findByName("name")).thenReturn(Optional.ofNullable(tagDto));
        when(tagService.add(any())).thenReturn(tagDto);
        GiftCertificateDto result = giftCertificateService.add(giftCertificateDto);
        Assertions.assertEquals(giftCertificateDto, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {">name", "<name", "~name", "ab", "Name with space", "NameMoreThen16Char"})
    void testAdd_InvalidName_ExceptionThrown(String name) {
        Assertions.assertThrows(RequestValidationException.class,
                () -> giftCertificateService.add(GiftCertificateDto.builder().name(name).build()));
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
        Assertions.assertThrows(RequestValidationException.class,
                () -> giftCertificateService.add(GiftCertificateDto.builder()
                        .name("name")
                        .description(description)
                        .build()));
    }

    @ParameterizedTest
    @ValueSource(ints = {-500, -1, 1_000_001, 2_000_000})
    void testAdd_InvalidPrice_ExceptionThrown(int price) {
        Assertions.assertThrows(RequestValidationException.class,
                () -> giftCertificateService.add(GiftCertificateDto.builder()
                        .name("name")
                        .description("description")
                        .price(new BigDecimal(price))
                        .build()));
    }

    @ParameterizedTest
    @ValueSource(ints = {-500, -1, 0, 367, 500})
    void testAdd_InvalidDuration_ExceptionThrown(int duration) {
        Assertions.assertThrows(RequestValidationException.class,
                () -> giftCertificateService.add(GiftCertificateDto.builder()
                        .name("name")
                        .price(new BigDecimal(1))
                        .description("description")
                        .duration(duration)
                        .build()));
    }

    @Test
    void testFindById_ValidId_findsGiftCertificate() {
        // Given
        when(giftCertificateDao.findById(1L)).thenReturn(Optional.ofNullable(giftCertificate));
        when(tagService.findByCertificateId(1L)).thenReturn(Collections.singletonList(tagDto));
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
        Assertions.assertThrows(RequestValidationException.class,
                () -> giftCertificateService.findById(-10L));
    }

    @Test
    void testFindAll_GiftCertificatesExist_findsGiftCertificates() {
        // Given
        when(giftCertificateDao.findAll()).thenReturn(Collections.singletonList(giftCertificate));
        when(tagService.findByCertificateId(1L)).thenReturn(Collections.singletonList(tagDto));
        // When
        List<GiftCertificateDto> result = giftCertificateService.findAll();
        // Then
        Assertions.assertEquals(Collections.singletonList(giftCertificateDto), result);
    }


    @Test
    void testUpdate_AllFieldsAreValid_CreatesGiftCertificate() {
        // Given
        when(giftCertificateDao.update(any())).thenReturn(giftCertificate);
        when(giftCertificateDao.findById(1L)).thenReturn(Optional.ofNullable(giftCertificate));
        when(tagService.findByCertificateId(1L)).thenReturn(Collections.singletonList(tagDto));
        when(tagService.findByName("name")).thenReturn(Optional.empty());
        when(tagService.add(tagDto)).thenReturn(tagDto);
        // When
        GiftCertificateDto result = giftCertificateService.update(giftCertificateDto);
        // Then
        Assertions.assertNotEquals(giftCertificateDto.getLastUpdateDate(), result.getLastUpdateDate());
    }

    @ParameterizedTest
    @ValueSource(strings = {">name", "<name", "~name", "ab", "Name with space", "NameMoreThen16Char"})
    void testUpdate_InvalidName_ExceptionThrown(String name) {
        // Given
        // When
        // Then
        Assertions.assertThrows(RequestValidationException.class,
                () -> giftCertificateService.update(GiftCertificateDto.builder().name(name).build()));
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
        Assertions.assertThrows(RequestValidationException.class,
                () -> giftCertificateService.update(GiftCertificateDto.builder().name("name").description(description).build()));
    }

    @ParameterizedTest
    @ValueSource(ints = {-500, -1, 1_000_001, 2_000_000})
    void testUpdate_InvalidPrice_ExceptionThrown(int price) {
        // Given
        // When
        // Then
        Assertions.assertThrows(RequestValidationException.class,
                () -> giftCertificateService.update(GiftCertificateDto.builder()
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
        Assertions.assertThrows(RequestValidationException.class,
                () -> giftCertificateService.update(GiftCertificateDto.builder()
                        .name("name")
                        .price(new BigDecimal(1))
                        .description("description")
                        .duration(duration)
                        .build()));
    }

    @Test
    void testRemoveById_ValidId_True() {
        // Given
        when(giftCertificateDao.removeById(1L)).thenReturn(true);
        // When
        boolean result = giftCertificateService.removeById(1L);
        // Then
        Assertions.assertTrue(result);
    }

    @Test
    void testRemoveById_InvalidId_ExceptionThrown() {
        // Given
        // When
        // Then
        Assertions.assertThrows(RequestValidationException.class,
                () -> giftCertificateService.removeById(-10L));
    }
}
