package com.epam.esm.entity;

import com.epam.esm.entity.audit.OrderListener;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Entity Class Order for order entity
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
@EntityListeners(OrderListener.class)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long userId;
    private LocalDateTime purchaseTime;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderCertificateDetails> orderCertificateDetails;
}
