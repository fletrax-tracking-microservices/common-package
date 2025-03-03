package com.fletrax.tracking.commonpackage.client;

import com.fletrax.tracking.commonpackage.utils.dto.responses.RegisterDeviceResponse;
import com.fletrax.tracking.commonpackage.utils.dto.responses.UserClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "users-service", path = "/internal/users")
public interface UserClientCommon {

    @GetMapping("/find-all")
    public List<UserClientResponse> findAll();

    @GetMapping("/get-current-user-with-children")
    public List<UserClientResponse> getCurrentUserWithChildren();

    @GetMapping("/find-current-user")
    public UserClientResponse findCurrentUser();

    @GetMapping("/get-user-with-children/{id}")
    public List<UserClientResponse> getUserWithChildren(@PathVariable UUID id);

    @GetMapping("/get-user-hierarchy/{id}")
    public List<UserClientResponse> getUserHierarchy(@PathVariable UUID id);

    @GetMapping("/get-user-hierarchy-cacheable/{id}")
    public List<UserClientResponse> getUserHierarchyCacheable(@PathVariable UUID id);

    @GetMapping("/find-by-keycloak-user-id/{keycloakUserId}")
    public UserClientResponse findByKeycloakUserId(@PathVariable String keycloakUserId);

    @GetMapping("/register-device/get-by-user-id/{userId}")
    public List<RegisterDeviceResponse> getRegisterdDevicesByUserId(@PathVariable UUID userId);

    @PostMapping("/register-device/find-by-user-id-and-fcm-token-and-os")
    public RegisterDeviceResponse findByUserIdAndFcmTokenAndOs(@RequestBody RegisterDeviceResponse registerDevice);

    @PostMapping("/register-device/delete/{id}")
    public void deleteRegisterdDevice(@PathVariable UUID id);

    @GetMapping("/find-by-id/{id}")
    public UserClientResponse findById(@PathVariable UUID id);

    @GetMapping("/find-by-username/{username}")
    public UserClientResponse findByUsername(@PathVariable String username);

    @GetMapping("/find-username-by-id/{id}")
    public String findUsernameById(@PathVariable UUID id);
}
