package com.fletrax.tracking.commonpackage.utils.services;

import com.fletrax.tracking.commonpackage.client.StatisticClientCommon;
import com.fletrax.tracking.commonpackage.utils.dto.responses.StatisticsResponse;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class StatisticServiceCommon {

    private StatisticClientCommon statisticClient;

    public StatisticsResponse getLastByIdent(String ident) {
        return statisticClient.getLastByIdent(ident);
    }

}