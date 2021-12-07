package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;

public interface GiftCertificateService extends BaseService<GiftCertificateDto> {

    GiftCertificateDto update(GiftCertificateDto giftCertificate);

    void addTagToCertificate(long giftCertificateId, long tagId);

    void removeTagByCertificateId(long giftCertificateId);
}
