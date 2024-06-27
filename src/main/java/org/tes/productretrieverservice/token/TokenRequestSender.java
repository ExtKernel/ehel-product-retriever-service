package org.tes.productretrieverservice.token;

import com.fasterxml.jackson.databind.JsonNode;
import org.tes.productretrieverservice.model.AuthCode;
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
     * Sends a request to get a JSON representation of a refresh token.
     *
     * @param authCode an authorization code.
     * @return a JSON node containing the refresh token.
     */
    JsonNode sendGetRefreshTokenRequest(AuthModelType authCode);

    /**
     * Sends a request to get a JSON representation of an access token.
     *
     * @param refreshToken a refresh token to use for access token retrieval.
     * @return a JSON node containing the access token.
     */
    JsonNode sendGetAccessTokenRequest(RefreshToken refreshToken);
}
