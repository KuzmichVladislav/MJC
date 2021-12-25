package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

    private long id;
    private long userId;
    private LocalDateTime purchaseTime;
    //    private Map<Map<GiftCertificateDto, BigDecimal>, Integer> giftCertificateDetails;
    private List<OrderCertificateDetails> orderCertificateDetails;
    private BigDecimal totalCost;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OrderCertificateDetails {
        long giftCertificateId;
        BigDecimal price;
        int numberOfCertificates;
    }
}
