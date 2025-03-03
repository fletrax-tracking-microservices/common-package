package com.fletrax.tracking.commonpackage.utils.components;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import jakarta.validation.constraints.Min;

public class Pagination {
    
    @Min(0)
    private int page = 0;

    @Min(0)
    private int offset = 0;

    @Min(1)
    private int size = 10;

    private String sort = "createdAt,desc"; // Default value

    // Getters and Setters
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    // Convenience method to create Pageable
    public Pageable toPageable() {
        // Parse sort
        String[] sortParts = sort.split(",");
        String sortBy = sortParts[0];
        String sortDir = sortParts.length > 1 ? sortParts[1].toLowerCase() : "desc";

        // Create Sort object
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // Handle Pageable creation
        if (offset != 0) {
            return OffsetBasedPageRequest.of(offset, size, sort);
        } else {
            return PageRequest.of(page, size, sort);
        }
    }
}
