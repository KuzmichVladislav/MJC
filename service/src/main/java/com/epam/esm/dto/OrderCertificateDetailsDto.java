package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
