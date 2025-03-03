package com.fletrax.tracking.commonpackage.utils.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class SkynasaService {

    private RestTemplate restTemplate;
    private String baseUrl;
    private String loginUrl;
    private String username;
    private String password;
    private String token;

    /**
     * Logs in and retrieves a new token.
     *
     * @return the new token
     */
    public String login() {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(loginUrl).toUriString();
            log.info("Attempting to log in with username: {}", username);

            HttpHeaders headers = createJsonHeaders();
            String requestBody = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";
            HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);

            log.info("Logging in to retrieve a new token...");
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return extractToken(response.getBody());
            } else {
                log.error("Login failed. Status: {}, Body: {}", response.getStatusCode(), response.getBody());
                throw new RuntimeException("Failed to retrieve token from login API.");
            }
        } catch (Exception e) {
            log.error("Error during login: {}", e.getMessage(), e);
            throw new RuntimeException("Error during login: " + e.getMessage(), e);
        }
    }

    /**
     * Makes an API call to the specified path with the given parameters and body.
     * Automatically refreshes the token on expiry.
     *
     * @param path   the API path
     * @param method the HTTP method
     * @param params the query parameters
     * @param body   the request body
     * @return the response entity
     */
    public <T> ResponseEntity<String> callApi(String path, HttpMethod method, Map<String, String> params,
            Map<String, T> body) {
        String url = buildUrl(path, params);
        HttpHeaders headers = createAuthHeaders();

        HttpEntity<Object> requestEntity = new HttpEntity<>(body, headers);
        log.info("Calling URL: {}", url);

        try {
            return restTemplate.exchange(url, method, requestEntity, String.class);
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().is4xxClientError() && e.getStatusCode().value() == 401) {
                log.warn("Token expired. Refreshing token...");
                token = login(); // Refresh the token
                headers.set("Authorization", "Bearer " + token);
                requestEntity = new HttpEntity<>(body, headers);
                return retryApiCall(url, method, requestEntity);
            } else {
                log.error("HTTP error during API call: {}", e.getMessage(), e);
                throw new RuntimeException("HTTP error during API call: " + e.getMessage(), e);
            }
        } catch (Exception e) {
            log.error("Unexpected error during API call: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error during API call: " + e.getMessage(), e);
        }
    }

    // Helper Methods

    /**
     * Builds the full URL with the base path and query parameters.
     */
    private String buildUrl(String path, Map<String, String> params) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl + path);
        if (params != null) {
            params.forEach(uriBuilder::queryParam);
        }
        return uriBuilder.toUriString();
    }

    /**
     * Creates headers with JSON content type.
     */
    private HttpHeaders createJsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /**
     * Creates headers with authorization token and JSON content type.
     */
    private HttpHeaders createAuthHeaders() {
        HttpHeaders headers = createJsonHeaders();
        headers.set("Authorization", "Bearer " + token);
        return headers;
    }

    /**
     * Extracts the token from the login response body.
     */
    private String extractToken(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            String newToken = jsonNode.path("result").path("access_token").asText();
            log.info("Successfully retrieved new token.");
            return newToken;
        } catch (Exception e) {
            log.error("Error extracting token: {}", e.getMessage(), e);
            throw new RuntimeException("Error extracting token: " + e.getMessage(), e);
        }
    }

    /**
     * Retries the API call with refreshed token.
     */
    private ResponseEntity<String> retryApiCall(String url, HttpMethod method, HttpEntity<Object> requestEntity) {
        try {
            return restTemplate.exchange(url, method, requestEntity, String.class);
        } catch (Exception e) {
            log.error("Retry failed for API call: {}", e.getMessage(), e);
            throw new RuntimeException("Retry failed for API call: " + e.getMessage(), e);
        }
    }
}
