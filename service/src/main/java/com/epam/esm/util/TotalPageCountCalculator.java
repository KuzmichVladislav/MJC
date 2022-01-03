package com.epam.esm.util;

import com.epam.esm.dto.QueryParameterDto;
import org.springframework.stereotype.Component;

@Component
public class TotalPageCountCalculator {

    public int getTotalPage(QueryParameterDto queryParameterDto, long totalNumberOfItems) {
        int page = queryParameterDto.getPage();
        int size = queryParameterDto.getSize();
        int totalPage = (int) Math.ceil(totalNumberOfItems / (double) size);
        if (page > totalPage) {
            // TODO: 12/27/2021 throw new exception
        }
        queryParameterDto.setFirstValue(page * size - size);
        // TODO: 12/29/2021 add exception
        return totalPage;
    }
}
