package com.fletrax.tracking.commonpackage.utils.services;

import com.fletrax.tracking.commonpackage.client.UserClientCommon;
import com.fletrax.tracking.commonpackage.utils.dto.responses.RegisterDeviceResponse;
import com.fletrax.tracking.commonpackage.utils.dto.responses.UserClientResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceCommon {

    private UserClientCommon userClient;

    public UserClientResponse getCurrentUser() {

        return userClient.findCurrentUser();
    }

    public RegisterDeviceResponse findByUserIdAndFcmTokenAndOs(
            RegisterDeviceResponse registerDeviceReRegisterDeviceResponse) {

        return userClient.findByUserIdAndFcmTokenAndOs(registerDeviceReRegisterDeviceResponse);
    }

    public List<RegisterDeviceResponse> getRegisterdDevicesByUserId(UUID userId) {

        return userClient.getRegisterdDevicesByUserId(userId);
    }

    public UserClientResponse findByKeycloakUserId(String keycloakUserId) {

        return userClient.findByKeycloakUserId(keycloakUserId);
    }

    public List<UserClientResponse> getUserWithChildren(UUID id) {
        return userClient.getUserWithChildren(id);
    }

    public List<UserClientResponse> getUserHierarchy(UUID id) {
        return userClient.getUserHierarchy(id);
    }

    @Cacheable(value = "userHierarchyCache", key = "#id", unless = "#result == null")
    public List<UserClientResponse> getUserHierarchyCacheable(UUID id) {
        return userClient.getUserHierarchyCacheable(id);
    }

    public List<UUID> getCurrentUserWithChildrenIds() {
        Boolean isAdmin = checkIfAdmin();

        if (isAdmin) {
            return List.of();
        }

        List<UserClientResponse> users = userClient.getCurrentUserWithChildren();

        return users.stream()
                .map(UserClientResponse::getId)
                .collect(Collectors.toList());
    }

    public Jwt getLoggedUser() {
        // Get the current authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the authentication is JwtAuthenticationToken and retrieve the Jwt
        // (token)
        if (authentication instanceof JwtAuthenticationToken jwtAuthToken) {
            Jwt jwt = (Jwt) jwtAuthToken.getToken(); // Extract the JWT
            System.out.println(jwt); // For debugging purposes
            return jwt;
        }

        return null; // If not authenticated via JWT
    }

    @SuppressWarnings("unchecked")
    public List<String> getRolesFromJwt(Jwt jwt) {
        // Safely cast the "roles" claim
        Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");

        if (realmAccess != null) {
            Object rolesObj = realmAccess.get("roles");

            // Ensure it's a List of Strings before casting
            if (rolesObj instanceof List<?>) {
                try {
                    return (List<String>) rolesObj;
                } catch (ClassCastException e) {
                    System.err.println("Failed to cast roles to List<String>");
                }
            }
        }

        return null; // Return null or an empty list if roles not found
    }

    public Boolean checkIfAdmin() {
        Jwt jwt = getLoggedUser();
        if (jwt != null) {
            List<String> roles = getRolesFromJwt(jwt);
            if (roles != null) {
                return roles.contains("admin");
            }
        }
        return false;
    }

    public void deleteRegisterdDevice(UUID id) {
        userClient.deleteRegisterdDevice(id);
    }

    public List<UserClientResponse> findAll() {
        return userClient.findAll();
    }

    public UserClientResponse findById(UUID id) {
        return userClient.findById(id);
    }

    @Cacheable(value = "findUsernameById", key = "#id", unless = "#result == null")
    public String findUsernameById(UUID id) {
        return userClient.findUsernameById(id);
    }

    public UserClientResponse findByUsername(String username) {
        return userClient.findByUsername(username);
    }
}