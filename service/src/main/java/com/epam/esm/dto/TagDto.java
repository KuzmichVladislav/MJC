package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO Class GiftCertificate for tag DTO object
 *
 * @author Vladislav Kuzmich
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagDto {
    private long id;
    private String name;
}
