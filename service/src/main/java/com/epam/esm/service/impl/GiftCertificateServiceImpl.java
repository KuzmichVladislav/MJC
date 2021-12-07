package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.mapper.GiftCertificateDtoMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;

    @Autowired
    GiftCertificateDtoMapper giftCertificateMapper;

    @Autowired
    TagService tagService;

    @Autowired
    TagDao tagDao;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    public GiftCertificateDto add(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = giftCertificateMapper.toGiftCertificate(giftCertificateDto);
        long giftCertificateId = giftCertificateDao.add(giftCertificate).getId();
        addTags(giftCertificateDto, giftCertificateId);
        return giftCertificateMapper.toDto(giftCertificateDao
                .findById(giftCertificateId).orElse(null));// TODO: 12/7/2021
    }

    @Override
    public GiftCertificateDto findById(long id) {
        GiftCertificate giftCertificate = giftCertificateDao
                .findById(id).orElse(null);// TODO: 12/7/2021
        giftCertificate.setTagList(tagService.readAllTagsByCertificateId(id));
        return giftCertificateMapper.toDto(giftCertificate);
    }

    @Override
    public List<GiftCertificateDto> findAll() {
        return giftCertificateDao.findAll().stream()
                .map(t -> giftCertificateMapper.toDto(t))
                .collect(Collectors.toList());
    }

    @Override
    public GiftCertificateDto update(GiftCertificateDto giftCertificateDto) {
        long giftCertificateId = giftCertificateDto.getId();
        removeTagByCertificateId(giftCertificateId);
        addTags(giftCertificateDto, giftCertificateId);
        GiftCertificate giftCertificate = giftCertificateMapper.toGiftCertificate(giftCertificateDto);
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        return giftCertificateMapper.toDto(giftCertificateDao.update(giftCertificate));
    }

    @Override
    public void addTagToCertificate(long giftCertificateId, long tagId) {
        giftCertificateDao.addTagToCertificate(giftCertificateId, tagId);
    }

    @Override
    public void removeTagByCertificateId(long giftCertificateId) {
        giftCertificateDao.removeTagByCertificateId(giftCertificateId);
    }

    @Override
    public boolean removeById(long id) {
        return giftCertificateDao.removeById(id);
    }

    public List<GiftCertificate> findAllCertificateByTagId(long giftCertificateId) {
        return giftCertificateDao.findAllCertificateByTagId(giftCertificateId);
    }

    private void addTags(GiftCertificateDto giftCertificateDto, long giftCertificateId) {
        List<Tag> tagList = giftCertificateDto.getTagList();
        List<Tag> existingTags = tagDao.findAll();
        tagList.stream()
                .distinct()
                .filter(e -> !existingTags.contains(e))
                .map(tag -> tagService.findByName(tag.getName()).orElseGet(() -> tagService.add(tag)).getId())
                .forEach(id -> addTagToCertificate(giftCertificateId, id));
    }
}
