package org.tes.productretrieverservice.token;

import com.fasterxml.jackson.databind.JsonNode;
import org.tes.productretrieverservice.model.AuthModel;
import org.tes.productretrieverservice.model.RefreshToken;

/**
 * An interface to send token related requests.
 *
 * @param <AuthModelType> the type of the {@link AuthModel} object
 *                       that will be used for {@link RefreshToken} retrieval.
 */
public interface TokenRequestSender<AuthModelType extends AuthModel> {

    /**
     * Sends a request to get a {@link JsonNode} representation of a refresh token.
     *
     * @param authModel an object that will be used for the refresh token retrieval.
     * @return a JSON node containing the refresh token.
     */
    JsonNode sendGetRefreshTokenRequest(AuthModelType authModel);

    /**
     * Sends a request to get a {@link JsonNode} representation of an access token.
     *
     * @param refreshToken a refresh token to use for access token retrieval.
     * @return a JSON node containing the access token.
     */
    JsonNode sendGetAccessTokenRequest(RefreshToken refreshToken);
}
