package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.RequestParamDto;
import com.epam.esm.dto.TagDto;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateService extends BaseService<GiftCertificateDto> {

    GiftCertificateDto update(GiftCertificateDto giftCertificate);

    List<TagDto> findTagsByCertificateId(long giftCertificateId);

    List<GiftCertificateDto> findAll(RequestParamDto requestParams);
}
