package org.tes.productretrieverservice.unit.token;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.tes.productretrieverservice.TestFactory;
import org.tes.productretrieverservice.model.AccessToken;
import org.tes.productretrieverservice.model.RefreshToken;
import org.tes.productretrieverservice.token.EbayTokenJsonObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EbayTokenJsonObjectMapperTest extends TestFactory {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final EbayTokenJsonObjectMapper ebayTokenJsonObjectMapper = new EbayTokenJsonObjectMapper();

    @Test
    public void givenValidRefreshToken_whenMapUserRefreshTokenJsonNodeToRefreshToken_thenReturnRefreshToken() {
        RefreshToken refreshToken = buildValidRefreshToken();

        ObjectNode refreshTokenObjectNode = objectMapper.createObjectNode();
        refreshTokenObjectNode.put("refresh_token", refreshToken.getToken());
        refreshTokenObjectNode.put("refresh_token_expires_in", refreshToken.getExpiresIn());

        RefreshToken result = ebayTokenJsonObjectMapper.mapRefreshTokenJsonNodeToUserRefreshToken(refreshTokenObjectNode);

        assertEquals(
                refreshToken.getToken(),
                result.getToken()
        );
        assertEquals(
                refreshToken.getExpiresIn(),
                result.getExpiresIn()
        );
    }

    @Test
    public void givenValidAccessToken_whenMapUserAccessTokenJsonNodeToAccessToken_thenReturnAccessToken() {
        AccessToken accessToken = buildValidAccessToken();

        ObjectNode accessTokenObjectNode = objectMapper.createObjectNode();
        accessTokenObjectNode.put("access_token", accessToken.getToken());
        accessTokenObjectNode.put("expires_in", accessToken.getExpiresIn());
        JsonNode accessTokenJsonNode = accessTokenObjectNode;

        AccessToken result = ebayTokenJsonObjectMapper.mapAccessTokenJsonNodeToUserAccessToken(accessTokenJsonNode);

        assertEquals(
                accessToken.getToken(),
                result.getToken()
        );
        assertEquals(
                accessToken.getExpiresIn(),
                result.getExpiresIn()
        );
    }
}
