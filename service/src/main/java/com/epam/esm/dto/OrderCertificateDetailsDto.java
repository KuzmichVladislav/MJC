package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO Class OrderCertificateDetailsDto for order certificate details DTO object
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCertificateDetailsDto {

    GiftCertificateDto giftCertificate;
    @JsonIgnore
    BigDecimal price;
    int numberOfCertificates;
}
