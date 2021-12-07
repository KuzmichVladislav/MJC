package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;

import java.util.List;

public interface GiftCertificateService extends BaseService<GiftCertificateDto> {

    GiftCertificateDto update(GiftCertificateDto giftCertificate);

    void addTagToCertificate(long giftCertificateId, long tagId);

    void removeTagByCertificateId(long giftCertificateId);

    List<TagDto> readAllTagsByCertificateId(long giftCertificateId);
}
