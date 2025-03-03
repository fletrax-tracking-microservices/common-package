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
public class DeviceClientResponse extends ClientResponse {
    private UUID id;
    private String ident;
    private String name;
    private UUID vehicleId;
    private String vehiclePlate;
    private String vehicleImage;
    private String vehicleBrnad;
    private String vehicleModel;
    private UUID userId;
}