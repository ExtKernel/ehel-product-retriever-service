package org.tes.productretrieverservice.token;

import org.tes.productretrieverservice.model.AccessToken;
import org.tes.productretrieverservice.model.AuthModel;
import org.tes.productretrieverservice.model.RefreshToken;
import org.tes.productretrieverservice.model.User;

/**
 * @param <AuthModelType> an object, which is supposed to be used for refresh token retrieval.
 */
public interface TokenManager<UserType extends User, AuthModelType extends AuthModel> {

    /**
     * Retrieves a {@link RefreshToken} using the given {@link AuthModelType} object.
     *
     * @param authModel the {@link AuthModelType} object
     *                 that will be used for the {@link RefreshToken} retrieval.
     * @return the {@link RefreshToken}.
     */
    RefreshToken getRefreshToken(
            UserType user,
            AuthModelType authModel
    );

    /**
     * Retrieves a {@link AccessToken} using the given {@link RefreshToken} object.
     *
     * @param user a user which holds the necessary data for request building.
     * @param refreshToken the {@link RefreshToken}
     *                    that will be used for the {@link AccessToken} retrieval.
     * @return the {@link AccessToken}.
     */
    AccessToken getAccessToken(
            UserType user,
            RefreshToken refreshToken
    );
}
