package com.fletrax.tracking.commonpackage.utils.components;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PaginationUtils {

    /**
     * Generic pagination function.
     *
     * @param pageable      the pagination information (page number, size)
     * @param entitiesPage  the page of entities fetched from the database
     * @param mapper        function to map entities to DTOs
     * @param <T>           entity type
     * @param <R>           DTO type
     * @return              a Page of DTOs with pagination metadata
     */
    public static <T, R> Page<R> paginate(Pageable pageable, Page<T> entitiesPage, Function<T, R> mapper) {
        List<R> dtos = entitiesPage.getContent().stream()
                .map(mapper)
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, entitiesPage.getTotalElements());
    }
}
