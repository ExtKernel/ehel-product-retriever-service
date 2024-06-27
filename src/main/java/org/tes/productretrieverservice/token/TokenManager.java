package org.tes.productretrieverservice.token;

import org.tes.productretrieverservice.model.AccessToken;
import org.tes.productretrieverservice.model.RefreshToken;

/**
 * @param <T> an object, which is supposed to be used for refresh token retrieval.
 */
public interface TokenManager<T> {

    /**
     * Retrieves a {@link RefreshToken} using the given {@link T} object.
     *
     * @param authModel the {@link T} object
     *                 that will be used for the {@link RefreshToken} retrieval.
     * @return the {@link RefreshToken}.
     */
    RefreshToken getRefreshToken(T authModel);

    /**
     * Retrieves a {@link AccessToken} using the given {@link RefreshToken} object.
     *
     * @param refreshToken the {@link RefreshToken}
     *                    that will be used for the {@link AccessToken} retrieval.
     * @return the {@link AccessToken}.
     */
    AccessToken getAccessToken(RefreshToken refreshToken);
}
