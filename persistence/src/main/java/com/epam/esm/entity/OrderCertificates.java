package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "order_certificates")
public class OrderCertificates {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @Column(name = "gift_certificate_cost")
    private BigDecimal giftCertificateCost;
    @Column(name = "number_of_certificates")
    private int numberOfCertificates;

    @ManyToOne
    private Order order;

    @ManyToOne
    private GiftCertificate giftCertificate;
}
