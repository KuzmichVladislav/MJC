package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

/**
 * Entity Class GiftCertificateQueryParameter contains parameters for generation query
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiftCertificateQueryParameter {

    private Optional<String> name;
    private Optional<String> description;
    private Optional<String> tagName;
    private Optional<List<String>> sortType;
    private Optional<String> sortOrder;
}
