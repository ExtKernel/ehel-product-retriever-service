package org.tes.productretrieverservice.token;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;
import org.tes.productretrieverservice.model.AccessToken;
import org.tes.productretrieverservice.model.RefreshToken;

@Component
public class EbayTokenJsonObjectMapper implements TokenJsonObjectMapper {

    @Override
    public RefreshToken mapUserRefreshTokenJsonNodeToUserRefreshToken(JsonNode userRefreshTokenJsonNode) {
        return new RefreshToken(
                userRefreshTokenJsonNode.path("refresh_token").asText(),
                userRefreshTokenJsonNode.path("refresh_token_expires_in").asInt()
        );
    }

    @Override
    public AccessToken mapUserAccessTokenJsonNodeToUserAccessToken(JsonNode userAccessTokenJsonNode) {
        return new AccessToken(
                userAccessTokenJsonNode.path("access_token").asText(),
                userAccessTokenJsonNode.path("expires_in").asInt()
        );
    }
}
