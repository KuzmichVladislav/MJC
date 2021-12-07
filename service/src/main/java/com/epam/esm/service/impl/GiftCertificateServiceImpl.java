package com.epam.esm.service.impl;

import com.epam.esm.configuration.MapperUtil;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;
    @Autowired
    TagService tagService;
    @Autowired
    TagDao tagDao;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    public GiftCertificateDto add(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = convertToGiftCertificateEntity(giftCertificateDto);
        long giftCertificateId = giftCertificateDao.add(giftCertificate).getId();
        addGiftCertificateTags(giftCertificateDto, giftCertificateId);
        return convertToGiftCertificateDto(giftCertificateDao
                .findById(giftCertificateId).orElse(null));// TODO: 12/7/2021
    }

    private GiftCertificate convertToGiftCertificateEntity(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
        giftCertificate.setTagList(MapperUtil.convertList(giftCertificateDto.getTagDtoList(), this::convertToTagEntity));
        return giftCertificate;
    }

    private Tag convertToTagEntity(TagDto tagDto) {
        return modelMapper.map(tagDto, Tag.class);
    }

    private GiftCertificateDto convertToGiftCertificateDto(GiftCertificate giftCertificate) {
        GiftCertificateDto giftCertificateDto = modelMapper.map(giftCertificate, GiftCertificateDto.class);
        //  giftCertificateDto.setTagDtoList(MapperUtil.convertList(giftCertificate.getTagList(), this::convertToTagDto));
        return giftCertificateDto;
    }

    private TagDto convertToTagDto(Tag tag) {
        return modelMapper.map(tag, TagDto.class);
    }

    @Override
    public GiftCertificateDto findById(long id) {
        GiftCertificate giftCertificate = giftCertificateDao
                .findById(id).orElse(null);// TODO: 12/7/2021
        GiftCertificateDto giftCertificateDto = convertToGiftCertificateDto(giftCertificate);
        giftCertificateDto.setTagDtoList(readAllTagsByCertificateId(id));
        return giftCertificateDto;
    }

    @Override
    public List<GiftCertificateDto> findAll() {
        List<GiftCertificate> all = giftCertificateDao.findAll();
        return MapperUtil.convertList(all, this::convertToGiftCertificateDto).stream()
                .peek(t -> t.setTagDtoList(readAllTagsByCertificateId(t.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public GiftCertificateDto update(GiftCertificateDto giftCertificateDto) {
        long giftCertificateId = giftCertificateDto.getId();
        removeTagByCertificateId(giftCertificateId);
        addGiftCertificateTags(giftCertificateDto, giftCertificateId);
        GiftCertificate giftCertificate = convertToGiftCertificateEntity(giftCertificateDto);
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        return convertToGiftCertificateDto(giftCertificateDao.update(giftCertificate));
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
    public List<TagDto> readAllTagsByCertificateId(long giftCertificateId) {
        return MapperUtil.convertList(giftCertificateDao.readAllTagsByCertificateId(giftCertificateId), this::convertToTagDto);
    }

    @Override
    public boolean removeById(long id) {
        return giftCertificateDao.removeById(id);
    }

    private void addGiftCertificateTags(GiftCertificateDto giftCertificateDto, long giftCertificateId) {
        List<Tag> tagList = MapperUtil.convertList(giftCertificateDto.getTagDtoList(), this::convertToTagEntity);
        List<Tag> existingTags = tagDao.findAll();
        tagList.stream()
                .distinct()
                .filter(e -> !existingTags.contains(e))
                .map(tag -> tagService.findByName(tag.getName()).orElseGet(() -> tagService.add(tag)).getId())
                .forEach(id -> addTagToCertificate(giftCertificateId, id));
    }
}
