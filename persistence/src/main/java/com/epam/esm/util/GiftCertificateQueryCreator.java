package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificateQueryParameter;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Collections;

/**
 * The Class GiftCertificateQueryCreator for creating a query based on parameters obtained from request parameters.
 */
@Component
public class GiftCertificateQueryCreator {

    private static final String WHERE_GIFT_CERTIFICATE_NAME_LIKE = "WHERE gift_certificate.name LIKE ''%{0}%''";
    private static final String GIFT_CERTIFICATE_DESCRIPTION_LIKE = "gift_certificate.description LIKE ''%{0}%''";
    private static final String TAG_NAME = "t.name = ''{0}''";
    private static final String ASC = "ASC";
    private static final String DESC = "DESC";
    private static final String ORDER_BY = " ORDER BY %s %s";
    private static final String DELIMITER = ", ";
    private static final String ID = "id";
    private static final String WHERE = "WHERE ";
    private static final String AND = " AND ";

    public String mapRequestParam(GiftCertificateQueryParameter requestParams) {
        StringBuilder builder = new StringBuilder();
        if (requestParams.getName().isPresent()) {
            builder.append(MessageFormat.format(WHERE_GIFT_CERTIFICATE_NAME_LIKE, requestParams.getName().get()));
        }
        if (requestParams.getDescription().isPresent()) {
            addCondition(builder);
            builder.append(MessageFormat.format(GIFT_CERTIFICATE_DESCRIPTION_LIKE, requestParams.getDescription().get()));
        }
        if (requestParams.getTagName().isPresent()) {
            addCondition(builder);
            builder.append(MessageFormat.format(TAG_NAME, requestParams.getTagName().get()));
        }
        String sortOrderParameter = addSortOrderParameter(requestParams);
        String orderParameterPostfix = addSortTypeParameters(requestParams, sortOrderParameter);
        return builder.append(orderParameterPostfix).toString();
    }

    private void addCondition(StringBuilder builder) {
        if (builder.toString().isEmpty()) {
            builder.append(WHERE);
        } else {
            builder.append(AND);
        }
    }

    private String addSortTypeParameters(GiftCertificateQueryParameter requestParams, String sortOrderParam) {
        return String.format(ORDER_BY, String.join(DELIMITER,
                requestParams.getSortType().orElse(Collections.singletonList(ID))), sortOrderParam);
    }

    private String addSortOrderParameter(GiftCertificateQueryParameter requestParams) {
        String sortOrderParam = requestParams.getSortOrder().orElse(ASC);
        if (!sortOrderParam.equals(DESC) && !sortOrderParam.equals(ASC)) {
            sortOrderParam = ASC;
        }
        return sortOrderParam;
    }
}
