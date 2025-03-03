package com.fletrax.tracking.commonpackage.utils.security;


import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    private final static String ROLE_PREFIX = "ROLE_";

    @SuppressWarnings("null")
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        return extractRoles(jwt);
    }

    @SuppressWarnings("unchecked")
    private Collection<GrantedAuthority> extractRoles(Jwt jwt) {
        var claims = jwt.getClaims();
        var resourceAccess = (Map<String, Object>) claims.getOrDefault("resource_access", Collections.emptyMap());
        var clientRolesMap = (Map<String, Object>) resourceAccess.getOrDefault("fletrax-tracking", Collections.emptyMap());
        var clientRoles = (List<String>) clientRolesMap.getOrDefault("roles", Collections.emptyList());

        return clientRoles.stream()
                .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role))
                .collect(Collectors.toList());
    }
}