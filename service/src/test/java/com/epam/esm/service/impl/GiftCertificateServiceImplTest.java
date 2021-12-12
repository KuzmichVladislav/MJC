package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ListConvertor;
import com.epam.esm.validator.RequestValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class GiftCertificateServiceImplTest {

    @Mock
    GiftCertificateDao giftCertificateDao;
    @Mock
    TagService tagService;
    @Mock
    ModelMapper modelMapper = new ModelMapper();
    @Mock
    ListConvertor mapperUtilInstance;
    @Mock
    RequestValidator requestValidator;
    @Mock
    PlatformTransactionManager transactionManager;
    @Mock
    TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
    @InjectMocks
    GiftCertificateServiceImpl giftCertificateServiceImpl;
    GiftCertificateDto giftCertificateDto;
    GiftCertificate giftCertificate;
    TagDto tagDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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
        when(modelMapper.map(any(GiftCertificate.class), any())).thenReturn(giftCertificateDto);
        when(modelMapper.map(any(GiftCertificateDto.class), any())).thenReturn(giftCertificate);

    }

    @Test
    void testAdd() {
        when(giftCertificateDao.add(any())).thenReturn(giftCertificate);
        when(tagService.findByName("name")).thenReturn(Optional.ofNullable(tagDto));
        when(tagService.add(any())).thenReturn(tagDto);
        when(mapperUtilInstance.convertList(any(), any()))
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
        when(mapperUtilInstance.convertList(any(), any())).thenReturn(Collections.singletonList(giftCertificateDto));
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
        Assertions.assertEquals(giftCertificateDto, result);
    }

    @Test
    void testRemoveById() {
        when(giftCertificateDao.removeById(1L)).thenReturn(true);
        boolean result = giftCertificateServiceImpl.removeById(1L);
        Assertions.assertTrue(result);
    }
}