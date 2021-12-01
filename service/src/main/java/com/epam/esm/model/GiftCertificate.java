package com.epam.esm.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class GiftCertificate {
    int id;
    String name;
    String description;
    BigDecimal price;
    int duration;
    Date createDate;
    Date lastUpdateDate;
    List<Tag> tagList;
}
