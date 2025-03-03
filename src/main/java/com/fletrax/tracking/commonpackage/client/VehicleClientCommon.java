package com.fletrax.tracking.commonpackage.client;

import com.fletrax.tracking.commonpackage.utils.dto.responses.VehicleClientResponse;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "vehicles-service", path = "/internal/vehicles")
public interface VehicleClientCommon {

    @GetMapping("/find-all")
    public List<VehicleClientResponse> findAll();

    @GetMapping("/find-by-device-id/{deviceId}")
    public VehicleClientResponse findByDeviceId(@PathVariable UUID deviceId);

    @GetMapping("/cars/find-by-device-ident/{deviceIdent}")
    public VehicleClientResponse findCarByVehicleDeviceIdent(@PathVariable String deviceIdent);

    @GetMapping("/cars/find-by-vehicle-id/{vehicleId}")
    public VehicleClientResponse findCarByVehicleId(@PathVariable UUID vehicleId);

}
