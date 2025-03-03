package com.fletrax.tracking.commonpackage.utils.services;

import com.fletrax.tracking.commonpackage.client.VehicleClientCommon;
import com.fletrax.tracking.commonpackage.utils.dto.responses.VehicleClientResponse;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class VehicleServiceCommon {

    private VehicleClientCommon vehicleClient;

    public VehicleClientResponse findByDeviceId(UUID deviceId) {

        return vehicleClient.findByDeviceId(deviceId);
    }

    public VehicleClientResponse findCarByVehicleDeviceIdent(String deviceIdent) {

        return vehicleClient.findCarByVehicleDeviceIdent(deviceIdent);
    }

    public VehicleClientResponse findCarByVehicleId(UUID vehicleId) {

        return vehicleClient.findCarByVehicleId(vehicleId);
    }

    public List<VehicleClientResponse> findAll() {
        return vehicleClient.findAll();
    }
}