package com.epam.esm.util;

import com.epam.esm.entity.RequestSqlParam;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Collections;

@Component
public class QueryParamCreator {

    private static final String WHERE_GIFT_CERTIFICATE_NAME_LIKE = "WHERE gift_certificate.name LIKE ''%{0}%''";
    private static final String AND_GIFT_CERTIFICATE_DESCRIPTION_LIKE = " AND gift_certificate.description LIKE ''%{0}%''";
    private static final String AND_NAME = " AND t.name = ''{0}''";
    private static final String WHERE_GIFT_CERTIFICATE_DESCRIPTION_LIKE = "WHERE gift_certificate.description LIKE ''%{0}%''";
    private static final String WHERE_NAME = "WHERE t.name = ''{0}''";
    private static final String ASC = "ASC";
    private static final String DESC = "DESC";
    private static final String ORDER_BY = " ORDER BY %s %s";
    private static final String DELIMITER = ", ";
    private static final String ID = "id";

    public String mapRequestParam(RequestSqlParam requestParams) {

        StringBuilder builder = new StringBuilder();
        String orderParamPostfix;
        if (requestParams.getName().isPresent()) {
            builder.append(MessageFormat.format(WHERE_GIFT_CERTIFICATE_NAME_LIKE, requestParams.getName().get()));
            if (requestParams.getDescription().isPresent()) {
                builder.append(MessageFormat.format(AND_GIFT_CERTIFICATE_DESCRIPTION_LIKE, requestParams.getDescription().get()));
            }
            if (requestParams.getTagName().isPresent()) {
                builder.append(MessageFormat.format(AND_NAME, requestParams.getTagName().get()));
            }
        } else {
            if (requestParams.getDescription().isPresent()) {
                builder.append(MessageFormat.format(WHERE_GIFT_CERTIFICATE_DESCRIPTION_LIKE, requestParams.getDescription().get()));
                if (requestParams.getTagName().isPresent()) {
                    builder.append(MessageFormat.format(AND_NAME, requestParams.getTagName().get()));
                }
            } else {
                if (requestParams.getTagName().isPresent()) {
                    builder.append(MessageFormat.format(WHERE_NAME, requestParams.getTagName().get()));
                }
            }
        }
        String sortOrderParam = requestParams.getOrderBy().orElse(ASC);
        if (!sortOrderParam.equals(DESC) && !sortOrderParam.equals(ASC)) {
            sortOrderParam = ASC;
        }
        orderParamPostfix = String.format(ORDER_BY, String.join(DELIMITER,
                requestParams.getSort().orElse(Collections.singletonList(ID))), sortOrderParam);
        return builder.append(orderParamPostfix).toString();
    }
}
