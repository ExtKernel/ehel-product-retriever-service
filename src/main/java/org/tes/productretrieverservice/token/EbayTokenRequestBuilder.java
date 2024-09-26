package org.tes.productretrieverservice.token;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.tes.productretrieverservice.exception.WritingAuthCodeRequestBodyToJsonStringException;
import org.tes.productretrieverservice.exception.WritingRefreshTokenRequestBodyToJsonStringException;
import org.tes.productretrieverservice.model.AuthCode;
import org.tes.productretrieverservice.model.EbayUser;
import org.tes.productretrieverservice.model.RefreshToken;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class EbayTokenRequestBuilder implements TokenRequestBuilder<EbayUser, AuthCode> {
    private final ObjectMapper objectMapper;

    @Value("${EBAY_CLIENT_REDIRECT_URI}")
    private String redirectUri;

    @Autowired
    public EbayTokenRequestBuilder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * @param user a user which holds the eBay client data.
     */
    @Override
    public HttpEntity<String> buildHttpRequestEntity(
            EbayUser user,
            String requestBody
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
    public String buildAuthModelRequestBody(AuthCode authCode) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("grant_type", "authorization_code");
        requestBody.put("code", authCode.getAuthCode());
        requestBody.put("redirect_uri", redirectUri);

        try {
            return objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException exception) {
            throw new WritingAuthCodeRequestBodyToJsonStringException(
                    "An exception occurred, while writing a request body,"
                            + " containing an auth code to a JSON string",
                    exception
            );
        }
    }

    @Override
    public String buildRefreshTokenRequestBody(RefreshToken refreshToken) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("grant_type", "refresh_token");
        requestBody.put("refresh_token", refreshToken.getToken());

        try {
            return objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException exception) {
            throw new WritingRefreshTokenRequestBodyToJsonStringException(
                    "An exception occurred, while writing a request body,"
                            + " containing a refresh token to a JSON string",
                    exception
            );
        }
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
