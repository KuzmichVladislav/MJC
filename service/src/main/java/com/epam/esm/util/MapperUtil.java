package com.epam.esm.util;

import com.epam.esm.dto.RequestParamDto;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class MapperUtil {
    public <R, E> List<R> convertList(List<E> list, Function<E, R> converter) {
        return list.stream().map(converter).collect(Collectors.toList());
    }

    public String mapRequestParam(RequestParamDto requestParams){
        String partOfNamePostfix = "";
        String partOfDescriptionPostfix = "";
        String tagNamesPostfix = "";
        String orderParamPostfix = "";
        if (requestParams.getName().isPresent()) {
            partOfNamePostfix = "WHERE gift_certificate.name LIKE '%" + requestParams.getName().get() + "%'";
            if (requestParams.getDescription().isPresent()) {
                partOfDescriptionPostfix = " AND gift_certificate.description LIKE '%" + requestParams.getDescription().get() + "%'";
            }
            if (requestParams.getTagName().isPresent()) {
                tagNamesPostfix = " AND t.name = '" + requestParams.getTagName().get() + ("'");
            }
        } else {
            if (requestParams.getDescription().isPresent()) {
                partOfDescriptionPostfix = "WHERE gift_certificate.description LIKE '%" + requestParams.getDescription().get() + "%'";
                if (requestParams.getTagName().isPresent()) {
                    tagNamesPostfix = " AND t.name = '" + requestParams.getTagName().get() + "'";
                }
            } else {
                if (requestParams.getTagName().isPresent()) {
                    tagNamesPostfix = "WHERE t.name = '" + requestParams.getTagName().get() + "'";
                }
            }
        }
        String sortOrderParam = requestParams.getOrderBy().orElse("ASC");
        if (!sortOrderParam.equals("DESC") && !sortOrderParam.equals("ASC")) {
            sortOrderParam = "ASC";
        }
        orderParamPostfix = String.format(" ORDER BY %s %s", String.join(", ",
                requestParams.getSort().orElse(Arrays.asList("id"))), sortOrderParam);
        String sqlQueryPostfix = partOfNamePostfix + partOfDescriptionPostfix + tagNamesPostfix + orderParamPostfix;
        return sqlQueryPostfix;
    }
}