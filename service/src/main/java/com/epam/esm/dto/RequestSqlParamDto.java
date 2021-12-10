package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestSqlParamDto {
    Optional<String> name;
    Optional<String> description;
    Optional<String> tagName;
    Optional<List<String>> sort;
    Optional<String> orderBy;
}
