package org.tes.productretrieverservice.token;

import com.fasterxml.jackson.databind.JsonNode;
import org.tes.productretrieverservice.model.AccessToken;
import org.tes.productretrieverservice.model.RefreshToken;

public interface TokenJsonObjectMapper {

    /**
     * Maps a {@link JsonNode} representation of a refresh token to a {@link RefreshToken} object.
     *
     * @param userRefreshTokenJsonNode the JSON node representation of the {@link RefreshToken}.
     * @return the {@link RefreshToken}.
     */
    RefreshToken mapUserRefreshTokenJsonNodeToUserRefreshToken(JsonNode userRefreshTokenJsonNode);

    /**
     * Maps a {@link JsonNode} representation of an access token to a {@link AccessToken} object.
     *
     * @param userAccessTokenJsonNode the JSON node representation of the {@link AccessToken}.
     * @return the {@link AccessToken}.
     */
    AccessToken mapUserAccessTokenJsonNodeToUserAccessToken(JsonNode userAccessTokenJsonNode);
}
