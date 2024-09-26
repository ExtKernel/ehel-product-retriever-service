package org.tes.productretrieverservice.token;

import com.fasterxml.jackson.databind.JsonNode;
import org.tes.productretrieverservice.model.AuthModel;
import org.tes.productretrieverservice.model.RefreshToken;
import org.tes.productretrieverservice.model.User;

/**
 * An interface to send token related requests.
 *
 * @param <AuthModelType> the type of the {@link AuthModel} object
 *                       that will be used for {@link RefreshToken} retrieval.
 * @param <UserType> the type of user to be used while building and sending a request.
 */
public interface TokenRequestSender<UserType extends User, AuthModelType extends AuthModel> {

    /**
     * Sends a request to get a {@link JsonNode} representation of a refresh token.
     *
     * @param user a user that will be used to build the request.
     * @param authModel an object that will be used for the refresh token retrieval.
     * @return a JSON node containing the refresh token.
     */
    JsonNode sendGetRefreshTokenRequest(
            UserType user,
            AuthModelType authModel
    );

    /**
     * Sends a request to get a {@link JsonNode} representation of an access token.
     *
     * @param user a user that will be used to build the request.
     * @param refreshToken a refresh token to use for access token retrieval.
     * @return a JSON node containing the access token.
     */
    JsonNode sendGetAccessTokenRequest(
            UserType user,
            RefreshToken refreshToken
    );
}
