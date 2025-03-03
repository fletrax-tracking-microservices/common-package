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
public class GeofenceDeviceClientResponse extends ClientResponse {

    private UUID id;
    private UUID geofenceId;
    private UUID deviceId;
    private UUID vehicleId;
    private String deviceIdent;
    private Boolean isInside;

}