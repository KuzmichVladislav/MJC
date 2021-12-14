package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.RequestValidationException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Utility Class GiftCertificateRequestValidator for validating the values received
 * from the request for the gift certificate DTO object.
 *
 * @author Vladislav Kuzmich
 */
@Component
public class GiftCertificateRequestValidator {
    private static final String NAME_REGEX = "^[\\w_]{3,16}$";
    private static final String DESCRIPTION_REGEX = "^[A-Za-z0-9\\s!@.?,&%'-]{0,250}$";

    /**
     * Check id request.
     *
     * @param id the id request
     */
    public void checkId(Long id) {
        if (id < 1) {
            throw new RequestValidationException(ExceptionKey.CERTIFICATE_ID_IS_NOT_VALID.getKey(), String.valueOf(id));
        }
    }

    /**
     * Check name request.
     *
     * @param name the name request
     */
    public void checkName(String name) {
        if (!name.trim().matches(NAME_REGEX)) {
            throw new RequestValidationException(ExceptionKey.CERTIFICATE_NAME_IS_NOT_VALID.getKey(), name);
        }
    }

    /**
     * Check description request.
     *
     * @param description the description request
     */
    public void checkDescription(String description) {
        if (!description.matches(DESCRIPTION_REGEX)) {
            throw new RequestValidationException(ExceptionKey.CERTIFICATE_DESCRIPTION_IS_NOT_VALID.getKey(), description);
        }
    }

    /**
     * Check duration request.
     *
     * @param duration the duration request
     */
    public void checkDuration(int duration) {
        if (duration < 1 || duration > 366) {
            throw new RequestValidationException(ExceptionKey.CERTIFICATE_DURATION_IS_NOT_VALID.getKey(),
                    String.valueOf(duration));
        }
    }

    /**
     * Check price request.
     *
     * @param price the price request
     */
    public void checkPrice(BigDecimal price) {
        if (price.compareTo(new BigDecimal(0)) < 0 || price.compareTo(new BigDecimal(1_000_000)) > 0) {
            throw new RequestValidationException(ExceptionKey.CERTIFICATE_PRICE_IS_NOT_VALID.getKey(),
                    String.valueOf(price));
        }
    }


    /**
     * Validate all request parameters for a gift certificate.
     *
     * @param giftCertificateDto the gift certificate DTO object
     */
    public void validateGiftCertificate(GiftCertificateDto giftCertificateDto) {
        String name = giftCertificateDto.getName();
        if (name != null) {
            checkName(name);
        }
        String description = giftCertificateDto.getDescription();
        if (description != null) {
            checkDescription(description);
        }
        Integer duration = giftCertificateDto.getDuration();
        if (duration != null) {
            checkDuration(duration);
        }
        BigDecimal price = giftCertificateDto.getPrice();
        if (price != null) {
            checkPrice(price);
        }
    }
}