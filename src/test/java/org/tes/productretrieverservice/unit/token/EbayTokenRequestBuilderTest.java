package org.tes.productretrieverservice.unit.token;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.tes.productretrieverservice.exception.WritingAuthCodeRequestBodyToJsonStringException;
import org.tes.productretrieverservice.exception.WritingRefreshTokenRequestBodyToJsonStringException;
import org.tes.productretrieverservice.model.AuthCode;
import org.tes.productretrieverservice.model.RefreshToken;
import org.tes.productretrieverservice.token.EbayTokenRequestBuilder;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class EbayTokenRequestBuilderTest {

    @Mock
    private ObjectMapper objectMapper;

    private EbayTokenRequestBuilder requestBuilder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        requestBuilder = new EbayTokenRequestBuilder(objectMapper);
        ReflectionTestUtils.setField(requestBuilder, "clientId", "client-id-value");
        ReflectionTestUtils.setField(requestBuilder, "clientSecret", "client-secret-value");
        ReflectionTestUtils.setField(requestBuilder, "redirectUri", "redirect-uri-value");
    }

    @Test
    void testBuildHttpRequestEntity() {
        // Given
        String requestBody = "request-body-value";

        // When
        HttpEntity<String> result = requestBuilder.buildHttpRequestEntity(requestBody);

        // Then
        assertNotNull(result);
        assertEquals(requestBody, result.getBody());
        HttpHeaders headers = result.getHeaders();
        assertEquals(MediaType.APPLICATION_FORM_URLENCODED, headers.getContentType());
        assertEquals("Basic " + Base64.getEncoder().encodeToString("client-id-value:client-secret-value".getBytes()), headers.getFirst(HttpHeaders.AUTHORIZATION));
    }

    @Test
    void testBuildAuthModelRequestBody() throws JsonProcessingException {
        // Given
        AuthCode authCode = new AuthCode(
                "auth-code-value",
                3600
        );
        Map<String, String> expectedRequestBody = new HashMap<>();
        expectedRequestBody.put("grant_type", "authorization_code");
        expectedRequestBody.put("code", "auth-code-value");
        expectedRequestBody.put("redirect_uri", "redirect-uri-value");

        when(objectMapper.writeValueAsString(expectedRequestBody)).thenReturn("json-string-value");

        // When
        String result = requestBuilder.buildAuthModelRequestBody(authCode);

        // Then
        assertEquals("json-string-value", result);
    }

    @Test
    void testBuildAuthModelRequestBodyThrowsException() throws JsonProcessingException {
        // Given
        AuthCode authCode = new AuthCode(
                "auth-code-value",
                3600
        );
        Map<String, String> expectedRequestBody = new HashMap<>();
        expectedRequestBody.put("grant_type", "authorization_code");
        expectedRequestBody.put("code", "auth-code-value");
        expectedRequestBody.put("redirect_uri", "redirect-uri-value");

        when(objectMapper.writeValueAsString(expectedRequestBody)).thenThrow(new JsonProcessingException("Error") {});

        // When & Then
        assertThrows(WritingAuthCodeRequestBodyToJsonStringException.class, () -> requestBuilder.buildAuthModelRequestBody(authCode));
    }

    @Test
    void testBuildRefreshTokenRequestBody() throws JsonProcessingException {
        // Given
        RefreshToken refreshToken = new RefreshToken("refresh-token-value", 3600);
        Map<String, String> expectedRequestBody = new HashMap<>();
        expectedRequestBody.put("grant_type", "refresh_token");
        expectedRequestBody.put("refresh_token", "refresh-token-value");

        when(objectMapper.writeValueAsString(expectedRequestBody)).thenReturn("json-string-value");

        // When
        String result = requestBuilder.buildRefreshTokenRequestBody(refreshToken);

        // Then
        assertEquals("json-string-value", result);
    }

    @Test
    void testBuildRefreshTokenRequestBodyThrowsException() throws JsonProcessingException {
        // Given
        RefreshToken refreshToken = new RefreshToken("refresh-token-value", 3600);
        Map<String, String> expectedRequestBody = new HashMap<>();
        expectedRequestBody.put("grant_type", "refresh_token");
        expectedRequestBody.put("refresh_token", "refresh-token-value");

        when(objectMapper.writeValueAsString(expectedRequestBody)).thenThrow(new JsonProcessingException("Error") {});

        // When & Then
        assertThrows(WritingRefreshTokenRequestBodyToJsonStringException.class, () -> requestBuilder.buildRefreshTokenRequestBody(refreshToken));
    }

    @Test
    void testGetRestTemplate() {
        // When
        RestTemplate result = requestBuilder.getRestTemplate();

        // Then
        assertNotNull(result);
    }
}
