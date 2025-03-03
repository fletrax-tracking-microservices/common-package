package com.fletrax.tracking.commonpackage.utils.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsResponse {
    
    private String ident;
    private String date;
    private String formatedTotalExistingKilometers;
    private Double totalExistingKilometers;
    private String formatedTotalEngineHours;
    private Double totalEngineHours;
    private String formatedTotalParkingTime;
    private Double totalParkingTime;
}
