package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * The Interface TagService.
 * An interface to define all required methods for tag DTO object.
 */
public interface TagService {

    /**
     * Adds the tag DTO object.
     *
     * @param tagDto the tag DTO object
     * @return the tag DTO object
     */
    TagDto add(TagDto tagDto);

    /**
     * Find by tag DTO object identifier.
     *
     * @param id the tag DTO object identifier
     * @return the tag DTO object
     */
    TagDto findById(long id);

    /**
     * Find tag DTO object by name.
     *
     * @param name the name of tag DTO object
     * @return the optional of tag DTO object
     */
    Optional<TagDto> findByName(String name);

    /**
     * Find most used tag DTO object.
     *
     * @param id the user DTO object identifier
     * @return the tag DTO object
     */
    TagDto findMostUsedUserTag(long id);

    /**
     * Removes the by tag DTO object identifier.
     *
     * @param id the tag DTO object identifier
     */
    void removeById(long id);

    /**
     * Find all tag DTO object.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<TagDto> findAll(Pageable pageable);
}
