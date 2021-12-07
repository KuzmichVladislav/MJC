package com.epam.esm.dto.mapper;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;

public class GiftCertificateDtoMapper {
    public GiftCertificateDto toDto(GiftCertificate giftCertificate) {
        new GiftCertificateDto();
        return GiftCertificateDto.builder()
                .id(giftCertificate.getId())
                .name(giftCertificate.getName())
                .description(giftCertificate.getDescription())
                .price(giftCertificate.getPrice())
                .duration(giftCertificate.getDuration())
                .createDate(giftCertificate.getCreateDate())
                .lastUpdateDate(giftCertificate.getLastUpdateDate())
                .tagList(giftCertificate.getTagList())
                .build();
    }

    public GiftCertificate toGiftCertificate(GiftCertificateDto giftCertificateDto) {
        new GiftCertificate();
        return GiftCertificate.builder()
                .id(giftCertificateDto.getId())
                .name(giftCertificateDto.getName())
                .description(giftCertificateDto.getDescription())
                .price(giftCertificateDto.getPrice())
                .duration(giftCertificateDto.getDuration())
                .createDate(giftCertificateDto.getCreateDate())
                .lastUpdateDate(giftCertificateDto.getLastUpdateDate())
                .tagList(giftCertificateDto.getTagList())
                .build();
    }
}
