package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The Class PageWrapper for Page object
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageWrapper<T> {

    List<T> itemsPerPage;
    int totalPages;
}
