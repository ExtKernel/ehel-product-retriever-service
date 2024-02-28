package org.tes.productretrieverservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Map;

@Component
public class ProductionEbayTokenRefresher implements EbayTokenRefresher {
    @Value("${ebayClientId}")
    private String cliendId;

    @Value("${ebayClientSecret}")
    private String clientSecret;

    @Value("${ebayTokenUrl}")
    private String ebayTokenUrl;

    @Value("${ebayUserAuthCodeUrl}")
    private String userAuthCodeUrl;

    private String getUserAuthCode() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.exchange(userAuthCodeUrl, HttpMethod.GET, null, Map.class);

        return (String) response.getBody().get("authCode");
    }

    private String exchangeUserCodeForRefreshToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(Base64.getEncoder().encodeToString((cliendId + ":" + clientSecret).getBytes()));

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("code", getUserAuthCode());
        requestBody.add("redirect_uri", "Alexander_Gamja-Alexande-auctio-lzjzbqai");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.exchange(ebayTokenUrl, HttpMethod.POST, requestEntity, Map.class);

        return (String) response.getBody().get("refresh_token");
    }

    @Override
    public String getAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(Base64.getEncoder().encodeToString((cliendId + ":" + clientSecret).getBytes()));

        String refreshToken = exchangeUserCodeForRefreshToken();
        String requestBody = "grant_type=refresh_token&refresh_token=" + refreshToken;
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> responseEntity = restTemplate.exchange(ebayTokenUrl, HttpMethod.POST, requestEntity, Map.class);

        return (String) responseEntity.getBody().get("access_token");
    }
}
