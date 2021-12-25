package com.epam.esm.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"order", "giftCertificate"})
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "order_certificates")
public class OrderCertificates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
//    @Column(name = "order_id")
//    private long orderId;
//    @Column(name = "gift_certificate_id")
//    private long giftCertificateId;
    @Column(name = "gift_certificate_cost")
    private BigDecimal giftCertificateCost;
    @Column(name = "number_of_certificates")
    private int numberOfCertificates;

    @ManyToOne
    private Order order;

    @ManyToOne
    private GiftCertificate giftCertificate;
}
