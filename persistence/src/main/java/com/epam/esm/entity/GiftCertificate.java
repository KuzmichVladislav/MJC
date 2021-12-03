package com.epam.esm.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
    Timestamp createDate;
    Timestamp lastUpdateDate;
    List<Tag> tagList;
}
