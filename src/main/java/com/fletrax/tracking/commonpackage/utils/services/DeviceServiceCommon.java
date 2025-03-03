package com.fletrax.tracking.commonpackage.utils.services;

import com.fletrax.tracking.commonpackage.client.DeviceClientCommon;
import com.fletrax.tracking.commonpackage.utils.dto.responses.DeviceCacheClientResponse;
import com.fletrax.tracking.commonpackage.utils.dto.responses.DeviceClientResponse;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class DeviceServiceCommon {

    private DeviceClientCommon deviceClient;

    public List<DeviceClientResponse> getByUserId(UUID userId) {
        return deviceClient.getByUserId(userId);
    }

    @Cacheable(value = "deviceByIdent", key = "#ident", unless = "#result == null")
    public DeviceClientResponse findByIdent(String ident) {
        return deviceClient.findByIdent(ident);
    }

    @Cacheable(value = "deviceCache", key = "#ident", unless = "#result == null")
    public DeviceCacheClientResponse findByIdentCacheable(String ident) {
        return deviceClient.findByIdentCacheable(ident);
    }

    public DeviceClientResponse findById(UUID id) {
        return deviceClient.findById(id);
    }

    public List<String> getAllIdents() {
        return deviceClient.getAllIdents();
    }

    public List<DeviceClientResponse> getAllDevices() {
        return deviceClient.getAllDevices();
    }

    public List<DeviceClientResponse> getAllForMonitoring() {
        return deviceClient.getAllForMonitoring();

    }

    public List<DeviceClientResponse> getByUserIdForMonitoring(@PathVariable UUID userId) {
        return deviceClient.getByUserIdForMonitoring(userId);
    }

    public void updateHistoryResult(UUID deviceId, String result) {
        deviceClient.updateHistoryResult(deviceId, result);
    }
}