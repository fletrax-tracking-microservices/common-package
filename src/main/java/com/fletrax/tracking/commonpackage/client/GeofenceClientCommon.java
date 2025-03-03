package com.fletrax.tracking.commonpackage.client;

import com.fletrax.tracking.commonpackage.utils.dto.responses.GeofenceClientResponse;
import com.fletrax.tracking.commonpackage.utils.dto.responses.GeofenceDeviceClientResponse;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "geofences-service", path = "/internal/geofences")
public interface GeofenceClientCommon {

        @GetMapping("/get-geofences-containing-point")
        public List<GeofenceClientResponse> findGeofencesContainingPoint(
                        @RequestParam Double latitude,
                        @RequestParam Double longitude,
                        @RequestParam(required = false) List<UUID> userIds);

        @GetMapping("/devices/find-by-vehicle-id-or-device-id-and-geofence-id")
        public GeofenceDeviceClientResponse findByVehicleIdOrDeviceIdAndGeofenceId(@RequestParam UUID deviceId,
                        @RequestParam UUID vehicleId, @RequestParam UUID geofenceId);

        @PostMapping("/devices/save")
        public GeofenceDeviceClientResponse save(@RequestBody GeofenceDeviceClientResponse geofenceDevice);

        @PostMapping("/devices/update-inside")
        public void updateInside(@RequestParam UUID vehicleId, @RequestParam UUID deviceId,
                        @RequestParam String deviceIdent, @RequestParam UUID geofenceId,
                        @RequestParam Boolean inInside);
}