package org.tes.productretrieverservice.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.tes.productretrieverservice.model.AuthCode;
import org.tes.productretrieverservice.model.EbayUser;
import org.tes.productretrieverservice.model.RefreshToken;

import java.util.Base64;

@Component
public class EbayTokenRequestBuilder implements TokenRequestBuilder<EbayUser, AuthCode> {
    private final ObjectMapper objectMapper;

    @Autowired
    public EbayTokenRequestBuilder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * @param user a user which holds the eBay client data.
     */
    @Override
    public HttpEntity<MultiValueMap<String, String>> buildHttpRequestEntity(
            EbayUser user,
            MultiValueMap<String, String> requestBody
    ) {
        return new HttpEntity<>(
                requestBody,
                buildHeaders(
                        user.getClientId(),
                        user.getClientSecret()
                )
        );
    }

    @Override
    public MultiValueMap<String, String> buildAuthModelRequestBody(
            EbayUser user,
            AuthCode authCode
    ) {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("code", authCode.getAuthCode());
        requestBody.add("redirect_uri", user.getRedirectUrl());

        return requestBody;
    }

    @Override
    public MultiValueMap<String, String> buildRefreshTokenRequestBody(RefreshToken refreshToken) {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "refresh_token");
        requestBody.add("refresh_token", refreshToken.getToken());

        return requestBody;
    }

    @Override
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    private HttpHeaders buildHeaders(
            String clientId,
            String clientSecret
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(
                Base64.getEncoder()
                        .encodeToString((clientId + ":" + clientSecret).getBytes()));

        return headers;
    }
}
