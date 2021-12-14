package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.RequestSqlParamDto;
import com.epam.esm.dto.TagDto;

import java.util.List;

public interface GiftCertificateService extends BaseService<GiftCertificateDto> {

    GiftCertificateDto update(GiftCertificateDto giftCertificate);

    List<GiftCertificateDto> findByParameters(RequestSqlParamDto requestParams);
}
