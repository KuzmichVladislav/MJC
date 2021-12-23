package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue
    private long id;
    @Column(name = "user_id")
    private long userId;
    @Column(name = "purchase_time")
    private LocalDateTime purchaseTime;
    @Transient
    private BigDecimal totalCost;
    @Transient
    private List<GiftCertificate> giftCertificates;
}
