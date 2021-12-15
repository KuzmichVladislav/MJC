package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

/**
 * DTO Class GiftCertificateQueryParameterDto contains parameters for generation query
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiftCertificateQueryParameterDto {
    private Optional<String> name;
    private Optional<String> description;
    private Optional<String> tagName;
    private Optional<List<String>> sortType;
    private Optional<String> sortOrder;
}
