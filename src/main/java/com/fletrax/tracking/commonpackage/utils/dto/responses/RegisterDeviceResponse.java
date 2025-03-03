package com.fletrax.tracking.commonpackage.utils.dto.responses;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDeviceResponse {

    private UUID id;
    private String os;
    private String fcmToken;
    private UUID userId;
}
