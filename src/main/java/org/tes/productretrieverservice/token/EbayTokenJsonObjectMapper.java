package org.tes.productretrieverservice.token;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;
import org.tes.productretrieverservice.model.AccessToken;
import org.tes.productretrieverservice.model.RefreshToken;

@Component
public class EbayTokenJsonObjectMapper implements TokenJsonObjectMapper {

    @Override
    public RefreshToken mapRefreshTokenJsonNodeToUserRefreshToken(JsonNode refreshTokenJsonNode) {
        return new RefreshToken(
                refreshTokenJsonNode.path("refresh_token").asText(),
                refreshTokenJsonNode.path("refresh_token_expires_in").asInt()
        );
    }

    @Override
    public AccessToken mapAccessTokenJsonNodeToUserAccessToken(JsonNode accessTokenJsonNode) {
        return new AccessToken(
                accessTokenJsonNode.path("access_token").asText(),
                accessTokenJsonNode.path("expires_in").asInt()
        );
    }
}
