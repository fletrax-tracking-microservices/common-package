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
public class GeofenceClientResponse extends ClientResponse {
    private UUID id;
    private String name;
    private String type;
    private UUID userId;
}