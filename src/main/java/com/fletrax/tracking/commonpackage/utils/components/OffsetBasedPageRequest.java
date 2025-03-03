package com.fletrax.tracking.commonpackage.utils.components;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("null")
public class OffsetBasedPageRequest implements Pageable {
    private final int offset;
    private final int pageSize;
    private final Sort sort;

    public OffsetBasedPageRequest(int offset, int pageSize, Sort sort) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset index must not be less than zero!");
        }
        if (pageSize < 1) {
            throw new IllegalArgumentException("Page size must not be less than one!");
        }
        this.offset = offset;
        this.pageSize = pageSize;
        this.sort = sort;
    }

    public static OffsetBasedPageRequest of(int offset, int pageSize, Sort sort) {
        return new OffsetBasedPageRequest(offset, pageSize, sort);
    }

    @Override
    public int getPageNumber() {
        return offset / pageSize;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return new OffsetBasedPageRequest((int) (getOffset() + getPageSize()), getPageSize(), getSort());
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? new OffsetBasedPageRequest((int) (getOffset() - getPageSize()), getPageSize(), getSort())
                : this;
    }

    @Override
    public Pageable first() {
        return new OffsetBasedPageRequest(0, getPageSize(), getSort());
    }

    @Override
    public boolean hasPrevious() {
        return offset > 0;
    }

    @Override
    public Pageable withPage(int pageNumber) {
        if (pageNumber < 0) {
            throw new IllegalArgumentException("Page index must not be less than zero!");
        }
        return new OffsetBasedPageRequest(pageNumber * pageSize, pageSize, sort);
    }
}
