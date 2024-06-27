package org.tes.productretrieverservice.unit.token;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.tes.productretrieverservice.model.AccessToken;
import org.tes.productretrieverservice.model.RefreshToken;
import org.tes.productretrieverservice.token.EbayTokenJsonObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EbayTokenJsonObjectMapperTest {

    private EbayTokenJsonObjectMapper tokenJsonObjectMapper;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        tokenJsonObjectMapper = new EbayTokenJsonObjectMapper();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testMapUserRefreshTokenJsonNodeToUserRefreshToken() throws Exception {
        // Given
        String jsonString = "{\"refresh_token\": \"refresh-token-value\", \"refresh_token_expires_in\": 3600}";
        JsonNode jsonNode = objectMapper.readTree(jsonString);

        // When
        RefreshToken result = tokenJsonObjectMapper.mapUserRefreshTokenJsonNodeToUserRefreshToken(jsonNode);

        // Then
        assertEquals("refresh-token-value", result.getToken());
        assertEquals(3600, result.getExpiresIn());
    }

    @Test
    void testMapUserAccessTokenJsonNodeToUserAccessToken() throws Exception {
        // Given
        String jsonString = "{\"access_token\": \"access-token-value\", \"expires_in\": 1800}";
        JsonNode jsonNode = objectMapper.readTree(jsonString);

        // When
        AccessToken result = tokenJsonObjectMapper.mapUserAccessTokenJsonNodeToUserAccessToken(jsonNode);

        // Then
        assertEquals("access-token-value", result.getToken());
        assertEquals(1800, result.getExpiresIn());
    }
}
