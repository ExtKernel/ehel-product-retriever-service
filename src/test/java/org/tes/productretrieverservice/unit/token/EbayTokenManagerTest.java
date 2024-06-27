package org.tes.productretrieverservice.unit.token;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.tes.productretrieverservice.model.AccessToken;
import org.tes.productretrieverservice.model.AuthCode;
import org.tes.productretrieverservice.model.RefreshToken;
import org.tes.productretrieverservice.token.EbayTokenManager;
import org.tes.productretrieverservice.token.TokenJsonObjectMapper;
import org.tes.productretrieverservice.token.TokenRequestSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EbayTokenManagerTest {

    @Mock
    private TokenRequestSender requestSender;

    @Mock
    private TokenJsonObjectMapper jsonObjectMapper;

    private EbayTokenManager tokenManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tokenManager = new EbayTokenManager(requestSender, jsonObjectMapper);
    }

    @Test
    void testGetRefreshToken() {
        // Given
        AuthCode authCode = new AuthCode(
                "auth-code-value",
                3600
        );
        JsonNode refreshTokenJsonNode = mock(JsonNode.class);
        RefreshToken expectedRefreshToken = new RefreshToken("refresh-token-value", 3600);

        when(requestSender.sendGetRefreshTokenRequest(authCode)).thenReturn(refreshTokenJsonNode);
        when(jsonObjectMapper.mapUserRefreshTokenJsonNodeToUserRefreshToken(refreshTokenJsonNode)).thenReturn(expectedRefreshToken);

        // When
        RefreshToken result = tokenManager.getRefreshToken(authCode);

        // Then
        assertEquals(expectedRefreshToken, result);
    }

    @Test
    void testGetAccessToken() {
        // Given
        RefreshToken refreshToken = new RefreshToken("refresh-token-value", 3600);
        JsonNode accessTokenJsonNode = mock(JsonNode.class);
        AccessToken expectedAccessToken = new AccessToken("access-token-value", 1800);

        when(requestSender.sendGetAccessTokenRequest(refreshToken)).thenReturn(accessTokenJsonNode);
        when(jsonObjectMapper.mapUserAccessTokenJsonNodeToUserAccessToken(accessTokenJsonNode)).thenReturn(expectedAccessToken);

        // When
        AccessToken result = tokenManager.getAccessToken(refreshToken);

        // Then
        assertEquals(expectedAccessToken, result);
    }
}
