package com.fletrax.tracking.commonpackage.client;

import com.fletrax.tracking.commonpackage.utils.dto.responses.StatisticsResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "statistics-service", path = "/internal/statistics")
public interface StatisticClientCommon {

    @GetMapping("/getLastByIdent/{ident}")
    public StatisticsResponse getLastByIdent(@PathVariable String ident);
}