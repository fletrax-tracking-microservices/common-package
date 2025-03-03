package com.fletrax.tracking.commonpackage.utils.dto.responses;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleClientResponse extends ClientResponse {
    private UUID id;
    private UUID vehicleId;
    private UUID deviceId;
    private String deviceIdent;
    private String plate;
    private String brand;
    private String model;
    private String image;
    private UUID userId;
    private String currentMileage;
}