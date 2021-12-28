package com.epam.esm.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"orderCertificates"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "user_id")
    private long userId;
    @Column(name = "purchase_time")
    private LocalDateTime purchaseTime;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderCertificateDetails> orderCertificates;
}
