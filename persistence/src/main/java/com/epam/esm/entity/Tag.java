package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity Class Tag for tag entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tag {

    private long id;
    private String name;
}
