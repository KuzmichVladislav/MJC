package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ListConvertor;
import com.epam.esm.validator.GiftCertificateRequestValidator;
import com.epam.esm.validator.TagRequestValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
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
    GiftCertificateDao giftCertificateDao;
    @Mock
    TagService tagService;
    @Mock
    ListConvertor listConvertor;
    @Spy
    ModelMapper modelMapper = new ModelMapper();
    @Spy
    GiftCertificateRequestValidator giftCertificateRequestValidator = new GiftCertificateRequestValidator();
    @Spy
    TagRequestValidator tagRequestValidator = new TagRequestValidator();
    @InjectMocks
    GiftCertificateServiceImpl giftCertificateServiceImpl;
    GiftCertificateDto giftCertificateDto;
    GiftCertificate giftCertificate;
    TagDto tagDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        modelMapper.getConfiguration()
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setSkipNullEnabled(true)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true);

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
    void testAdd() {
        when(giftCertificateDao.add(any())).thenReturn(giftCertificate);
        when(tagService.findByName("name")).thenReturn(Optional.ofNullable(tagDto));
        when(tagService.add(any())).thenReturn(tagDto);
        when(listConvertor.convertList(any(), any()))
                .thenReturn(Collections.singletonList(Collections.singletonList(tagDto)));
        GiftCertificateDto result = giftCertificateServiceImpl.add(giftCertificateDto);
        Assertions.assertEquals(giftCertificateDto, result);
    }

    @Test
    void testFindById() {
        when(giftCertificateDao.findById(1L)).thenReturn(Optional.ofNullable(giftCertificate));
        when(tagService.findByCertificateId(1L)).thenReturn(Collections.singletonList(tagDto));
        GiftCertificateDto result = giftCertificateServiceImpl.findById(1L);
        Assertions.assertEquals(giftCertificateDto, result);
    }

    @Test
    void testFindAll() {
        when(giftCertificateDao.findAll()).thenReturn(Collections.singletonList(giftCertificate));
        when(listConvertor.convertList(any(), any())).thenReturn(Collections.singletonList(giftCertificateDto));
        List<GiftCertificateDto> result = giftCertificateServiceImpl.findAll();
        Assertions.assertEquals(Collections.singletonList(giftCertificateDto), result);
    }


    @Test
    void testUpdate() {
        when(giftCertificateDao.update(any())).thenReturn(giftCertificate);
        when(giftCertificateDao.findById(1L)).thenReturn(Optional.ofNullable(giftCertificate));
        when(tagService.findByCertificateId(1L)).thenReturn(Collections.singletonList(tagDto));
        when(tagService.findByName("name")).thenReturn(Optional.empty());
        when(tagService.add(tagDto)).thenReturn(tagDto);
        GiftCertificateDto result = giftCertificateServiceImpl.update(giftCertificateDto);
        Assertions.assertNotEquals(giftCertificateDto.getLastUpdateDate(), result.getLastUpdateDate());
    }

    @Test
    void testRemoveById() {
        when(giftCertificateDao.removeById(1L)).thenReturn(true);
        boolean result = giftCertificateServiceImpl.removeById(1L);
        Assertions.assertTrue(result);
    }
}