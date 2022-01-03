package com.epam.esm.service;

import com.epam.esm.dto.TagDto;

import java.util.Optional;

/**
 * The Interface TagService.
 * A interface to define all required methods for tag DTO object.
 */
public interface TagService extends BaseService<TagDto> {

    /**
     * Find tag DTO object by name.
     *
     * @param name the name of tag DTO object
     * @return the optional of tag DTO object
     */
    Optional<TagDto> findByName(String name);

    TagDto findMostUsedTag(long id);
}
