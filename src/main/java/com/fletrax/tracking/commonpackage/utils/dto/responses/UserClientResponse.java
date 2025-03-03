package com.fletrax.tracking.commonpackage.utils.dto.responses;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserClientResponse {
    private UUID id;
    private String username;
    private String role;
    private String locale;
    private String timezone;
}