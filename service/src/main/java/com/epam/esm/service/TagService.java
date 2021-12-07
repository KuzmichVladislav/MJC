package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;

import java.util.Optional;

public interface TagService extends BaseService<TagDto> {

    Optional<Tag> findByName(String name);
}
