package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

/**
 * DTO Class GiftCertificate for request parameters DTO object
 *
 * @author Vladislav Kuzmich
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestSqlParamDto {
    private Optional<String> name;
    private Optional<String> description;
    private Optional<String> tagName;
    private Optional<List<String>> sort;
    private Optional<String> orderBy;
}
