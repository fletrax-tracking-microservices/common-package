package com.fletrax.tracking.commonpackage.utils.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponse
{
    private boolean isSuccess;
    private String message;
}