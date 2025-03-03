package com.fletrax.tracking.commonpackage.client;

import com.fletrax.tracking.commonpackage.utils.dto.responses.DeviceCacheClientResponse;
import com.fletrax.tracking.commonpackage.utils.dto.responses.DeviceClientResponse;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "devices-service", path = "/internal/devices")
public interface DeviceClientCommon {

    @GetMapping("/get-by-user-id/{userId}")
    public List<DeviceClientResponse> getByUserId(@PathVariable UUID userId);

    @GetMapping("/find-by-ident/{ident}")
    public DeviceClientResponse findByIdent(@PathVariable String ident);

    @GetMapping("/find-by-ident-cacheable/{ident}")
    public DeviceCacheClientResponse findByIdentCacheable(@PathVariable String ident);

    @GetMapping("/find-by-id/{id}")
    public DeviceClientResponse findById(@PathVariable UUID id);

    @GetMapping("/get-idents")
    public List<String> getAllIdents();

    @GetMapping("/get-all-devices")
    public List<DeviceClientResponse> getAllDevices();

    @GetMapping("/monitoring")
    public List<DeviceClientResponse> getAllForMonitoring();

    @GetMapping("/monitoring/{userId}")
    public List<DeviceClientResponse> getByUserIdForMonitoring(@PathVariable UUID userId);

    @GetMapping("/commands/update-history-result")
    public void updateHistoryResult(@RequestParam UUID deviceId,
            @RequestParam String result);
}