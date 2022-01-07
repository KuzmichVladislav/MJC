package com.epam.esm.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Entity Class OrderCertificateDetails contains parameters for generation query for order certificate
 */
@Getter
@Setter
@EqualsAndHashCode(exclude = {"order", "giftCertificate"})
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
    @Column(name = "gift_certificate_cost")
    private BigDecimal giftCertificateCost;
    @Column(name = "number_of_certificates")
    private int numberOfCertificates;
    @ManyToOne
    private Order order;
    @ManyToOne
    private GiftCertificate giftCertificate;
}