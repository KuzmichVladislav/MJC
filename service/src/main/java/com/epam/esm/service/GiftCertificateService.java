package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;

import java.util.List;

public interface GiftCertificateService extends BaseService<GiftCertificateDto> {

    GiftCertificateDto update(GiftCertificateDto giftCertificate);

    List<TagDto> findByCertificateId(long giftCertificateId);

    List<GiftCertificateDto> findAllSorted(List<String> params);
}
