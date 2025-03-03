package com.fletrax.tracking.commonpackage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fletrax.tracking.commonpackage.utils.ApiResponseBuilder;
import com.fletrax.tracking.commonpackage.utils.dto.ApiResponse;
import com.fletrax.tracking.commonpackage.utils.services.ListService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${api.external-prefix}/lists")
public class ListController {

    @Autowired
    private ListService listService;

    @GetMapping("/{key}")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getList(@PathVariable String key) {
        return ResponseEntity
                .ok(ApiResponseBuilder.success(listService.getListByKey(key), key + " fetched successfully"));
    }
}
