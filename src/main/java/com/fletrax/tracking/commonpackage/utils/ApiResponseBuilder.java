package com.fletrax.tracking.commonpackage.utils;

import com.fletrax.tracking.commonpackage.utils.dto.ApiResponse;

public class ApiResponseBuilder {
    public static <T> ApiResponse<T> success(T result, String message) {
        return new ApiResponse<>(true, message, result);
    }

    public static <T> ApiResponse<T> failure(String message) {
        return new ApiResponse<>(false, message, null);
    }
}
