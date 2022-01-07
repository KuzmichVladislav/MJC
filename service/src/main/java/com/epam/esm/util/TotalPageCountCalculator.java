package com.epam.esm.util;

import com.epam.esm.dto.QueryParameterDto;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.RequestValidationException;
import org.springframework.stereotype.Component;

/**
 * Utility Class TotalPageCountCalculator to calculate the number of pages possible to display.
 */
@Component
public class TotalPageCountCalculator {

    /**
     * Сalculate the number of pages possible to display.
     *
     * @param queryParameterDto  the query parameter DTO
     * @param totalNumberOfItems the total number of items
     * @return the total number of pages
     */
    public int getTotalPage(QueryParameterDto queryParameterDto, long totalNumberOfItems) {
        int page = queryParameterDto.getPage();
        int size = queryParameterDto.getSize();
        int totalPage = (int) Math.ceil(totalNumberOfItems / (double) size);
        if (page > totalPage) {
            throw new RequestValidationException(ExceptionKey.SPECIFIED_PAGE_DOES_NOT_EXIST, String.valueOf(page));
        }
        queryParameterDto.setFirstValue((page - 1) * size);
        return totalPage;
    }
}
