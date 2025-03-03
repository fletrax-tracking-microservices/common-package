package com.fletrax.tracking.commonpackage.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "lists")
public class ListsConfig {
    
    // vehicles service
    private Map<String, Map<String, Object>> vehicleFuelTypes;
    private Map<String, Map<String, Object>> vehicleTransmissionTypes;
    private Map<String, Map<String, Object>> vehicleTypes;
    private Map<String, Map<String, Object>> vehicleStatuses;
    private Map<String, Map<String, Object>> vehicleColors;
    private Map<String, Map<String, Object>> vehicleSeats;
    private Map<String, Map<String, Object>> vehicleCarTypes;
    private Map<String, Map<String, Object>> vehicleCarBrands;

    // violations service
    private Map<String, Map<String, Object>> violationStatuses;

    // maintenances service
    private Map<String, Map<String, Object>> maintenanceStatuses;
}
