package com.epam.esm.entity;

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
public class RequestSqlParam {
    private Optional<String> name;
    private Optional<String> description;
    private Optional<String> tagName;
    private Optional<List<String>> sort;
    private Optional<String> orderBy;
}
