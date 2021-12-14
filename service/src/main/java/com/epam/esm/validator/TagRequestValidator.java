package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.RequestValidationException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Utility Class TagRequestValidator for validating the values received
 * from the request for the tag DTO object.
 *
 * @author Vladislav Kuzmich
 */
@Component
public class TagRequestValidator {
    private static final String NAME_REGEX = "^[\\w_]{3,16}$";

    /**
     * Check id request.
     *
     * @param id the id request
     */
    public void checkId(Long id) {
        if (id < 1) {
            throw new RequestValidationException(ExceptionKey.TAG_ID_IS_NOT_VALID.getKey(), String.valueOf(id));
        }
    }

    /**
     * Check name request.
     *
     * @param name the name request
     */
    public void checkName(String name) {
        if (!name.trim().matches(NAME_REGEX)) {
            throw new RequestValidationException(ExceptionKey.TAG_NAME_IS_NOT_VALID.getKey(), name);
        }
    }

    /**
     * Validate tags request.
     *
     * @param giftCertificateDto the gift certificate DTO object
     */
    public void validateTags(GiftCertificateDto giftCertificateDto) {
        List<TagDto> tagDtoList = giftCertificateDto.getTags();
        if (tagDtoList != null) {
            tagDtoList.stream()
                    .map(TagDto::getName)
                    .forEach(this::checkName);
        }
    }
}