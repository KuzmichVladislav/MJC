package com.epam.esm.dto;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCertificatesDto {

    private long id;
    private BigDecimal giftCertificateCost;
    private int numberOfCertificates;
    private GiftCertificate giftCertificate;
}
