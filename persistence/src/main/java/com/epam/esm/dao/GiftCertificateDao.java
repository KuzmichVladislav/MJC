package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.List;

public interface GiftCertificateDao extends BaseDao<GiftCertificate>{

    GiftCertificate update(GiftCertificate giftCertificate);

    List<GiftCertificate> findAllCertificateByTagId(long tagId); // TODO: 12/8/2021  

    void addTagToCertificate(long giftCertificateId, long tagId);

    void removeFromTableGiftCertificateIncludeTag(long giftCertificateId);
}
