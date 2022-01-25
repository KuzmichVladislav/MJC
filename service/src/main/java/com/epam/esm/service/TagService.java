package com.epam.esm.service;

import com.epam.esm.dto.QueryParameterDto;
import com.epam.esm.dto.TagDto;

import java.util.Optional;

/**
 * The Interface TagService.
 * A interface to define all required methods for tag DTO object.
 */
public interface TagService extends BaseService<TagDto, QueryParameterDto> {

    /**
     * Find tag DTO object by name.
     *
     * @param name the name of tag DTO object
     * @return the optional of tag DTO object
     */
    Optional<TagDto> findByName(String name);

    /**
     * Find most used tag DTO.
     *
     * @param id the user identifier
     * @return the tag DTO
     */
    TagDto findMostUsedTag(long id);

    /**
     * Removes the by tag DTO object identifier.
     *
     * @param id the the tag DTO object identifier
     */
    void removeById(long id);
}
