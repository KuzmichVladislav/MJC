package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.RequestValidationException;
import org.springframework.stereotype.Component;

/**
 * Utility Class GiftCertificateValidator for validating the values received
 * from the request for the gift certificate object.
 */
@Component
public class GiftCertificateValidator {

    /**
     * Check gift certificate fields for null.
     *
     * @param giftCertificateDto the gift certificate DTO
     */
    public void checkGiftCertificateFields(GiftCertificateDto giftCertificateDto) {
        if (giftCertificateDto.getName() == null) {
            throw new RequestValidationException(ExceptionKey.TAG_NAME_MIGHT_NOT_BE_NULL);
        }
        if (giftCertificateDto.getDescription() == null) {
            throw new RequestValidationException(ExceptionKey.CERTIFICATE_DESCRIPTION_MIGHT_NOT_BE_NULL);
        }
        if (giftCertificateDto.getPrice() == null) {
            throw new RequestValidationException(ExceptionKey.CERTIFICATE_PRICE_MIGHT_NOT_BE_NULL);
        }
        if (giftCertificateDto.getDuration() == null) {
            throw new RequestValidationException(ExceptionKey.CERTIFICATE_DURATION_MIGHT_NOT_BE_NULL);
        }
    }
}
