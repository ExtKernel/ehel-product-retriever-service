package org.tes.productretrieverservice.unit.token;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.tes.productretrieverservice.exception.AccessTokenJsonReadingException;
import org.tes.productretrieverservice.exception.RefreshTokenJsonReadingException;
import org.tes.productretrieverservice.model.AuthCode;
import org.tes.productretrieverservice.model.RefreshToken;
import org.tes.productretrieverservice.token.EbayTokenRequestSender;
import org.tes.productretrieverservice.token.TokenRequestBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class EbayTokenRequestSenderTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TokenRequestBuilder<AuthCode> requestBuilder;

    private EbayTokenRequestSender tokenRequestSender;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tokenRequestSender = new EbayTokenRequestSender(objectMapper, requestBuilder);
        ReflectionTestUtils.setField(tokenRequestSender, "ebayTokenUrl", "http://example.com/token");
        ReflectionTestUtils.setField(tokenRequestSender, "restTemplate", restTemplate);
    }

    @Test
    void testSendGetRefreshTokenRequest() throws JsonProcessingException {
        // Given
        AuthCode authCode = new AuthCode(
                "auth-code-value",
                3600
        );
        HttpEntity<String> httpEntity = new HttpEntity<>("body");
        ResponseEntity<String> responseEntity = ResponseEntity.ok("response");
        JsonNode refreshTokenJsonNode = objectMapper.createObjectNode();

        when(requestBuilder.buildAuthModelRequestBody(authCode)).thenReturn("body");
        when(requestBuilder.buildHttpRequestEntity("body")).thenReturn(httpEntity);
        when(restTemplate.exchange(eq("http://example.com/token"), eq(HttpMethod.POST), eq(httpEntity), eq(String.class))).thenReturn(responseEntity);
        when(objectMapper.readTree(responseEntity.getBody())).thenReturn(refreshTokenJsonNode);

        // When
        JsonNode result = tokenRequestSender.sendGetRefreshTokenRequest(authCode);

        // Then
        assertEquals(refreshTokenJsonNode, result);
    }

    @Test
    void testSendGetRefreshTokenRequestThrowsException() throws JsonProcessingException {
        // Given
        AuthCode authCode = new AuthCode(
                "auth-code-value",
                3600
        );
        HttpEntity<String> httpEntity = new HttpEntity<>("body");
        ResponseEntity<String> responseEntity = ResponseEntity.ok("response");

        when(requestBuilder.buildAuthModelRequestBody(authCode)).thenReturn("body");
        when(requestBuilder.buildHttpRequestEntity("body")).thenReturn(httpEntity);
        when(restTemplate.exchange(eq("http://example.com/token"), eq(HttpMethod.POST), eq(httpEntity), eq(String.class))).thenReturn(responseEntity);
        when(objectMapper.readTree(responseEntity.getBody())).thenThrow(new JsonProcessingException("Error") {});

        // When & Then
        assertThrows(RefreshTokenJsonReadingException.class, () -> tokenRequestSender.sendGetRefreshTokenRequest(authCode));
    }

    @Test
    void testSendGetAccessTokenRequest() throws JsonProcessingException {
        // Given
        RefreshToken refreshToken = new RefreshToken("refresh-token-value", 3600);
        HttpEntity<String> httpEntity = new HttpEntity<>("body");
        ResponseEntity<String> responseEntity = ResponseEntity.ok("response");
        JsonNode accessTokenJsonNode = objectMapper.createObjectNode();

        when(requestBuilder.buildRefreshTokenRequestBody(refreshToken)).thenReturn("body");
        when(requestBuilder.buildHttpRequestEntity("body")).thenReturn(httpEntity);
        when(restTemplate.exchange(eq("http://example.com/token"), eq(HttpMethod.POST), eq(httpEntity), eq(String.class))).thenReturn(responseEntity);
        when(objectMapper.readTree(responseEntity.getBody())).thenReturn(accessTokenJsonNode);

        // When
        JsonNode result = tokenRequestSender.sendGetAccessTokenRequest(refreshToken);

        // Then
        assertEquals(accessTokenJsonNode, result);
    }

    @Test
    void testSendGetAccessTokenRequestThrowsException() throws JsonProcessingException {
        // Given
        RefreshToken refreshToken = new RefreshToken("refresh-token-value", 3600);
        HttpEntity<String> httpEntity = new HttpEntity<>("body");
        ResponseEntity<String> responseEntity = ResponseEntity.ok("response");

        when(requestBuilder.buildRefreshTokenRequestBody(refreshToken)).thenReturn("body");
        when(requestBuilder.buildHttpRequestEntity("body")).thenReturn(httpEntity);
        when(restTemplate.exchange(eq("http://example.com/token"), eq(HttpMethod.POST), eq(httpEntity), eq(String.class))).thenReturn(responseEntity);
        when(objectMapper.readTree(responseEntity.getBody())).thenThrow(new JsonProcessingException("Error") {});

        // When & Then
        assertThrows(AccessTokenJsonReadingException.class, () -> tokenRequestSender.sendGetAccessTokenRequest(refreshToken));
    }
}
