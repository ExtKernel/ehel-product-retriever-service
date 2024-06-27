package org.tes.productretrieverservice.token;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import org.tes.productretrieverservice.model.AuthModel;
import org.tes.productretrieverservice.model.RefreshToken;

/**
 * An interface to send token related requests.
 *
 * @param <AuthModelType> the type of the {@link AuthModel} object
 *                       that will be used for {@link RefreshToken} retrieval.
 */
public interface TokenRequestBuilder<AuthModelType extends AuthModel> {

    /**
     * Builds an {@link HttpEntity}
     * for {@link org.tes.productretrieverservice.model.Token}-related requests.
     *
     * @param requestBody a request body to include in the {@link HttpEntity}.
     * @return the {@link HttpEntity}.
     */
    HttpEntity buildHttpRequestEntity(String requestBody);

    /**
     * Builds a request body, containing a {@link AuthModelType}
     * to perform a {@link RefreshToken} retrieval request.
     *
     * @param authModel the {@link AuthModelType}.
     * @return the request body.
     */
    String buildAuthModelRequestBody(AuthModelType authModel);

    /**
     * Builds a request body, containing a {@link RefreshToken}
     * to perform a {@link org.tes.productretrieverservice.model.AccessToken} retrieval request.
     *
     * @param refreshToken the {@link RefreshToken}.
     * @return the request body.
     */
    String buildRefreshTokenRequestBody(RefreshToken refreshToken);

    /**
     * Get a custom {@link RestTemplate} object, tailored for the specific needs.
     *
     * @return the custom {@link RestTemplate}.
     */
    RestTemplate getRestTemplate();
}
