package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto extends RepresentationModel<OrderDto> {

    private long id;
    private long userId;
    private LocalDateTime purchaseTime;
    private List<OrderCertificateDetailsDto> orderCertificateDetails;
    private BigDecimal totalCost;
}
