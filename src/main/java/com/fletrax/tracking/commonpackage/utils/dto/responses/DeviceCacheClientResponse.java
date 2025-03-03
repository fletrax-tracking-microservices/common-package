package com.fletrax.tracking.commonpackage.utils.dto.responses;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceCacheClientResponse {

    private UUID id;
    private String ident;
    private String vehiclePlate;
    private UUID vehicleId;
    private UUID userId;

}
