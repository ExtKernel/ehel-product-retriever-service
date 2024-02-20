package org.tes.productretrieverservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import org.tes.productretrieverservice.exception.NullTokenException;

/**
 * Helper methods for unit and integration testing.
 */
public class TestUtils {

    /**
     * This method is designed to get a JWT access token from a Keycloak realm.
     *
     * @param username A username of the user registered in the Keycloak realm or client.
     * @param password A password of the user registered in the Keycloak realm or client.
     * @param tokenEndpointUrl An endpoint for obtaining the token.
     * @return A JWT access token obtained from the Keycloak realm.
     */
    public static String obtainKeycloakAccessToken(
            String username,
            String password,
            String tokenEndpointUrl
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("client_id", "product-service");
        map.add("username", username);
        map.add("password", password);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(
                tokenEndpointUrl,
                request,
                Map.class
        );

        if (response.getBody() != null) {
            return response.getBody().get("access_token").toString();
        } else {
            throw new NullTokenException("Token is null");
        }
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
