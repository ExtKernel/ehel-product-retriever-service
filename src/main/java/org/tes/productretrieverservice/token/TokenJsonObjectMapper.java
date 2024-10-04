package org.tes.productretrieverservice.token;

import com.fasterxml.jackson.databind.JsonNode;
import org.tes.productretrieverservice.model.AccessToken;
import org.tes.productretrieverservice.model.RefreshToken;

public interface TokenJsonObjectMapper {

    /**
     * Maps a {@link JsonNode} representation of a refresh token to a {@link RefreshToken} object.
     *
     * @param refreshTokenJsonNode the JSON node representation of the {@link RefreshToken}.
     * @return the {@link RefreshToken}.
     */
    RefreshToken mapRefreshTokenJsonNodeToUserRefreshToken(JsonNode refreshTokenJsonNode);

    /**
     * Maps a {@link JsonNode} representation of an access token to a {@link AccessToken} object.
     *
     * @param accessTokenJsonNode the JSON node representation of the {@link AccessToken}.
     * @return the {@link AccessToken}.
     */
    AccessToken mapAccessTokenJsonNodeToUserAccessToken(JsonNode accessTokenJsonNode);
}
