package com.epam.esm.util;

import com.epam.esm.dto.QueryParameterDto;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.RequestValidationException;
import org.springframework.stereotype.Component;

@Component
public class TotalPageCountCalculator {

    public int getTotalPage(QueryParameterDto queryParameterDto, long totalNumberOfItems) {
        int page = queryParameterDto.getPage();
        if (page < 1) {
            throw new RequestValidationException(ExceptionKey.PAGE_MIGHT_NOT_BE_NEGATIVE, String.valueOf(page));
        }
        int size = queryParameterDto.getSize();
        if (size < 1) {
            throw new RequestValidationException(ExceptionKey.SIZE_MIGHT_NOT_BE_NEGATIVE, String.valueOf(size));
        }
        int totalPage = (int) Math.ceil(totalNumberOfItems / (double) size);
        if (page > totalPage) {
            throw new RequestValidationException(ExceptionKey.SPECIFIED_PAGE_DOES_NOT_EXIST, String.valueOf(page));
        }
        queryParameterDto.setFirstValue(page * size - size);
        return totalPage;
    }
}
