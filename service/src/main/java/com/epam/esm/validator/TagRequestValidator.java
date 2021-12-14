package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.RequestValidationException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class TagRequestValidator {
    private static final String NAME_REGEX = "^[\\w_]{3,16}$";

    public void checkId(Long id) {
        if (id < 1) {
            throw new RequestValidationException(ExceptionKey.ID_IS_NOT_VALID.getKey(), String.valueOf(id));
        }
    }

    public void checkName(String name) {
        if (!name.trim().matches(NAME_REGEX)) {
            throw new RequestValidationException(ExceptionKey.NAME_IS_NOT_VALID.getKey(), name);
        }
    }

    public void validateTags(GiftCertificateDto giftCertificateDto) {
        List<TagDto> tagDtoList = giftCertificateDto.getTags();
        if (tagDtoList != null) {
            tagDtoList.stream()
                    .map(TagDto::getName)
                    .forEach(this::checkName);
        }
    }
}