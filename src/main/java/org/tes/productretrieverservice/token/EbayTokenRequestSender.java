package org.tes.productretrieverservice.token;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.tes.productretrieverservice.exception.AccessTokenJsonReadingException;
import org.tes.productretrieverservice.exception.RefreshTokenJsonReadingException;
import org.tes.productretrieverservice.model.AuthCode;
import org.tes.productretrieverservice.model.EbayUser;
import org.tes.productretrieverservice.model.RefreshToken;

/**
 * A component class, implementing {@link TokenRequestSender} interface.
 * Sends specific to the eBay API token related requests.
 */
@Component
public class EbayTokenRequestSender implements TokenRequestSender<EbayUser, AuthCode> {
    private final ObjectMapper objectMapper;
    private final TokenRequestBuilder<EbayUser, AuthCode> requestBuilder;
    private final RestTemplate restTemplate;

    @Value("${ebayTokenUrl}")
    private String ebayTokenUrl;

    @Autowired
    public EbayTokenRequestSender(
            ObjectMapper objectMapper,
            TokenRequestBuilder<EbayUser, AuthCode> requestBuilder,
            RestTemplate restTemplate
    ) {
        this.objectMapper = objectMapper;
        this.requestBuilder = requestBuilder;
        this.restTemplate = restTemplate;
    }

    /**
     * @return a JSON node containing the refresh token, but also an access token.
     *         The access token is supposed to be ignored.
     */
    @Override
    public JsonNode sendGetRefreshTokenRequest(
            EbayUser user,
            AuthCode authCode
    ) {
        try {
            return objectMapper.readTree(restTemplate.exchange(
                    ebayTokenUrl,
                    HttpMethod.POST,
                    requestBuilder.buildHttpRequestEntity(
                            user,
                            requestBuilder.buildAuthModelRequestBody(
                                    user,
                                    authCode
                            )
                    ),
                    String.class
            ).getBody());
        } catch (JsonProcessingException exception) {
            throw new RefreshTokenJsonReadingException(
                    "An exception occurred while reading"
                            + " an eBay refresh token JSON received from the request"
                            + " to retrieve a refresh token",
                    exception
            );
        }
    }

    @Override
    public JsonNode sendGetAccessTokenRequest(
            EbayUser user,
            RefreshToken refreshToken
    ) {
        try {
            return objectMapper.readTree(restTemplate.exchange(
                    ebayTokenUrl,
                    HttpMethod.POST,
                    requestBuilder.buildHttpRequestEntity(
                            user,
                            requestBuilder.buildRefreshTokenRequestBody(refreshToken)
                    ),
                    String.class
            ).getBody());
        } catch (JsonProcessingException exception) {
            throw new AccessTokenJsonReadingException(
                    "An exception occurred while reading"
                            + " an eBay access token JSON received from the request"
                            + " to retrieve a access token",
                    exception
            );
        }
    }
}
