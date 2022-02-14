package com.epam.esm.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Entity Class OrderCertificateDetails contains parameters for generation query for order certificate
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "order_certificates")
public class OrderCertificateDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private BigDecimal giftCertificateCost;
    private int numberOfCertificates;
    @ManyToOne
    private Order order;
    @ManyToOne
    private GiftCertificate giftCertificate;
}
