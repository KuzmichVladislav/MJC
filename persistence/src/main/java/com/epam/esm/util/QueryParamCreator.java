package com.epam.esm.util;

import com.epam.esm.entity.RequestSqlParam;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class QueryParamCreator {

    private static final String EMPTY_STRING = "";
    private static final String WHERE_GIFT_CERTIFICATE_NAME_LIKE = "WHERE gift_certificate.name LIKE '%";
    private static final String LIKE_POSTFIX = "%'";
    private static final String AND_GIFT_CERTIFICATE_DESCRIPTION_LIKE = " AND gift_certificate.description LIKE '%";
    private static final String AND_NAME = " AND t.name = '";
    private static final String WHERE_GIFT_CERTIFICATE_DESCRIPTION_LIKE = "WHERE gift_certificate.description LIKE '%";
    private static final String AND_POSTFIX = "'";
    private static final String WHERE_NAME = "WHERE t.name = '";
    private static final String ASC = "ASC";
    private static final String DESC = "DESC";
    private static final String ORDER_BY = " ORDER BY %s %s";
    private static final String DELIMITER = ", ";
    private static final String ID = "id";

    public String mapRequestParam(RequestSqlParam requestParams){
        String partOfNamePostfix = EMPTY_STRING;
        String partOfDescriptionPostfix = EMPTY_STRING;
        String tagNamesPostfix = EMPTY_STRING;
        String orderParamPostfix;
        if (requestParams.getName().isPresent()) {
            partOfNamePostfix = WHERE_GIFT_CERTIFICATE_NAME_LIKE + requestParams.getName().get() + LIKE_POSTFIX;
            if (requestParams.getDescription().isPresent()) {
                partOfDescriptionPostfix = AND_GIFT_CERTIFICATE_DESCRIPTION_LIKE + requestParams.getDescription().get() + LIKE_POSTFIX;
            }
            if (requestParams.getTagName().isPresent()) {
                tagNamesPostfix = AND_NAME + requestParams.getTagName().get() + AND_POSTFIX;
            }
        } else {
            if (requestParams.getDescription().isPresent()) {
                partOfDescriptionPostfix = WHERE_GIFT_CERTIFICATE_DESCRIPTION_LIKE + requestParams.getDescription().get() + LIKE_POSTFIX;
                if (requestParams.getTagName().isPresent()) {
                    tagNamesPostfix = AND_NAME + requestParams.getTagName().get() + AND_POSTFIX;
                }
            } else {
                if (requestParams.getTagName().isPresent()) {
                    tagNamesPostfix = WHERE_NAME + requestParams.getTagName().get() + AND_POSTFIX;
                }
            }
        }
        String sortOrderParam = requestParams.getOrderBy().orElse(ASC);
        if (!sortOrderParam.equals(DESC) && !sortOrderParam.equals(ASC)) {
            sortOrderParam = ASC;
        }
        orderParamPostfix = String.format(ORDER_BY, String.join(DELIMITER,
                requestParams.getSort().orElse(Collections.singletonList(ID))), sortOrderParam);
        return partOfNamePostfix + partOfDescriptionPostfix + tagNamesPostfix + orderParamPostfix;
    }
}
