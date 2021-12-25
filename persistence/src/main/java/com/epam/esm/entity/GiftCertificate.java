package com.epam.esm.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Entity Class GiftCertificate for gift certificate entity
 */
@Getter
@Setter
@EqualsAndHashCode(exclude = {"tags", "orderCertificates"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "gift_certificate")
public class GiftCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "duration")
    private Integer duration;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;
    @Column(name = "removed")
    private boolean isRemoved;
    @ManyToMany
    @JoinTable(name = "gift_certificate_tag_include",
            joinColumns = @JoinColumn(name = "gift_certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;


    @OneToMany(mappedBy = "giftCertificate")
//    @ManyToMany
//    @JoinTable(name = "order_certificates",
//            joinColumns = @JoinColumn(name = "gift_certificate_id"),
//            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private Set<OrderCertificates> orderCertificates;
}
