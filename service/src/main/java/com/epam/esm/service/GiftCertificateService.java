package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateService extends BaseService<GiftCertificateDto> {

    GiftCertificateDto update(GiftCertificateDto giftCertificate);

    List<TagDto> findByCertificateId(long giftCertificateId);

    List<GiftCertificateDto> findAll(Optional<String> partOfName, Optional<List<String>> tagNames, Optional<List<String>> sortParams, Optional<String> sortOrder);
}
