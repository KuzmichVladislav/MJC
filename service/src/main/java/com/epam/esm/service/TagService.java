package com.epam.esm.service;

import com.epam.esm.dto.TagDto;

import java.util.List;
import java.util.Optional;

/**
 * The Interface TagService.
 * A interface to define all required methods for tag DTO object.
 *
 * @author Vladislav Kuzmich
 */
public interface TagService extends BaseService<TagDto> {

    /**
     * Find tag DTO object by certificate id.
     *
     * @param giftCertificateId the gift certificate id
     * @return the list of tag DTO objects
     */
    List<TagDto> findByCertificateId(long giftCertificateId);

    /**
     * Find tag DTO object by name.
     *
     * @param name the name of tag DTO object
     * @return the optional of tag DTO object
     */
    Optional<TagDto> findByName(String name);
}
