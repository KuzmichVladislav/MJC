package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificateQueryParameter;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * The Class GiftCertificateQueryCreator for creating a query based on parameters obtained from request parameters.
 */
@Component
public class GiftCertificateQueryCreator {

    private static final String WHERE_GIFT_CERTIFICATE_NAME_LIKE = "gc.name LIKE ''%{0}%''";
    private static final String GIFT_CERTIFICATE_DESCRIPTION_LIKE = "gc.description LIKE ''%{0}%''";
    private static final String TAG_NAME = "t.name = ''{0}''";
    private static final String ORDER_BY = " ORDER BY gc.{0} {1}";
    private static final String WHERE = " WHERE ";
    private static final String AND = " AND ";
    private static final String IS_ACTIVE = "gc.isRemoved = false ";

    public String mapRequestParameters(GiftCertificateQueryParameter requestParameter) {
        StringBuilder builder = new StringBuilder();
        if (requestParameter.getName().isPresent()) {
            addCondition(builder);
            builder.append(MessageFormat.format(WHERE_GIFT_CERTIFICATE_NAME_LIKE, requestParameter.getName().get()));
        }
        if (requestParameter.getDescription().isPresent()) {
            addCondition(builder);
            builder.append(MessageFormat.format(GIFT_CERTIFICATE_DESCRIPTION_LIKE,
                    requestParameter.getDescription().get()));
        }
        if (requestParameter.getTagNames().isPresent()) {
            requestParameter.getTagNames().get().forEach(t -> {
                addCondition(builder);
                builder.append(MessageFormat.format(TAG_NAME, t));
            });
        }
        addCondition(builder);
        builder.append(IS_ACTIVE);
        String sortOrderParameter = requestParameter.getSortParameter().getParameter();
        String orderParameterPostfix = requestParameter.getSortingDirection().name();
        builder.append(MessageFormat.format(ORDER_BY, sortOrderParameter, orderParameterPostfix));
        return builder.toString();
    }

    private void addCondition(StringBuilder builder) {
        if (builder.toString().isEmpty()) {
            builder.append(WHERE);
        } else {
            builder.append(AND);
        }
    }
}
