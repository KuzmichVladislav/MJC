package com.epam.esm.entity;

import com.epam.esm.entity.audit.GiftCertificateListener;
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
@EntityListeners(GiftCertificateListener.class)
public class GiftCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
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
    private Set<OrderCertificateDetails> orderCertificates;
}
