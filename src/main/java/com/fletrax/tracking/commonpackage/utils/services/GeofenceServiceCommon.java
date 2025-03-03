package com.fletrax.tracking.commonpackage.utils.services;

import com.fletrax.tracking.commonpackage.client.GeofenceClientCommon;
import com.fletrax.tracking.commonpackage.utils.dto.responses.GeofenceClientResponse;
import com.fletrax.tracking.commonpackage.utils.dto.responses.GeofenceDeviceClientResponse;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class GeofenceServiceCommon {

    private GeofenceClientCommon geofenceClient;

    public List<GeofenceClientResponse> findGeofencesContainingPoint(Double latitude, Double longitude,
            List<UUID> userIds) {
        return geofenceClient.findGeofencesContainingPoint(latitude, longitude, userIds);
    }

    @Cacheable(value = "vehicleInGeofence", key = "{#deviceId, #vehicleId, #geofenceId}", unless = "#result == null")
    public GeofenceDeviceClientResponse findByVehicleIdOrDeviceIdAndGeofenceId(UUID deviceId, UUID vehicleId,
            UUID geofenceId) {
        return geofenceClient.findByVehicleIdOrDeviceIdAndGeofenceId(deviceId, vehicleId, geofenceId);
    }

    public GeofenceDeviceClientResponse save(GeofenceDeviceClientResponse geofenceDevice) {
        return geofenceClient.save(geofenceDevice);
    }

    public void updateInside(UUID vehicleId, UUID deviceId, String deviceIdent, UUID geofenceId,
            Boolean inInside) {
        geofenceClient.updateInside(vehicleId, deviceId, deviceIdent, geofenceId, inInside);
    }
}