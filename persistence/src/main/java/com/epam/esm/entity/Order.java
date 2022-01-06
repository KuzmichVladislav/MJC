package com.epam.esm.entity;

import com.epam.esm.entity.audit.GiftCertificateListener;
import com.epam.esm.entity.audit.OrderListener;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"orderCertificateDetails"})
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
    @Column(name = "user_id")
    private long userId;
    @Column(name = "purchase_time")
    private LocalDateTime purchaseTime;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderCertificateDetails> orderCertificateDetails;
}
