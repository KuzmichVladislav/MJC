package com.epam.esm.util;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Utility Class ListConverter for converting list of entities to list of DTO objects and vice versa.
 */
@Component
public class ListConverter {

    /**
     * Convert list of entities to list of DTO objects and vice versa.
     *
     * @param <R>       the generic type
     * @param <E>       the element type
     * @param list      the list
     * @param converter the converter
     * @return the list
     */
    public <R, E> List<R> convertList(List<E> list, Function<E, R> converter) {
        return list.stream().map(converter).collect(Collectors.toList());
    }
}