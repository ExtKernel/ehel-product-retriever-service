package org.tes.productretrieverservice.unit.token;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.tes.productretrieverservice.TestFactory;
import org.tes.productretrieverservice.model.AuthCode;
import org.tes.productretrieverservice.model.EbayUser;
import org.tes.productretrieverservice.model.RefreshToken;
import org.tes.productretrieverservice.token.EbayTokenRequestSender;
import org.tes.productretrieverservice.token.TokenRequestBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EbayTokenRequestSenderTest extends TestFactory {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TokenRequestBuilder<EbayUser, AuthCode> requestBuilder;

    @InjectMocks
    private EbayTokenRequestSender ebayTokenRequestSender;

    private final String EBAY_TOKEN_URL = "https://api.ebay.com/identity/v1/oauth2/token";

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(ebayTokenRequestSender, "restTemplate", restTemplate);
        ReflectionTestUtils.setField(ebayTokenRequestSender, "ebayTokenUrl", EBAY_TOKEN_URL);
    }

    @Test
    public void givenUserAndAuthCode_whenSendGetRefreshTokenRequest_thenReturnRefreshTokenJsonNode()
            throws Exception {
        EbayUser user = buildEbayUser();
        AuthCode authCode = buildValidAuthCode();
        String ebayTokensJsonString = getEbayTokensJsonString();
        JsonNode ebayTokensJsonNode = getEbayTokensJsonNode();
        HttpEntity<String> httpEntity = new HttpEntity<>("mockRequestBody");

        when(requestBuilder.buildHttpRequestEntity(eq(user), any())).thenReturn(httpEntity);
        when(restTemplate.exchange(
                eq(EBAY_TOKEN_URL),
                eq(HttpMethod.POST),
                eq(httpEntity),
                eq(String.class)
        )).thenReturn(ResponseEntity.ok(ebayTokensJsonString));
        when(objectMapper.readTree(ebayTokensJsonString)).thenReturn(ebayTokensJsonNode);

        assertEquals(ebayTokensJsonNode, ebayTokenRequestSender.sendGetRefreshTokenRequest(
                user,
                authCode
        ));
    }

    @Test
    public void givenUserAndRefreshToken_whenSendGetAccessTokenRequest_thenReturnAccessTokenJsonNode()
            throws Exception {
        EbayUser user = buildEbayUser();
        RefreshToken refreshToken = buildValidRefreshToken();
        String refreshTokenRequestBody = buildStringRefreshTokenRequestBody(refreshToken);
        String ebayTokensJsonString = getEbayTokensJsonString();
        JsonNode ebayTokensJsonNode = getEbayTokensJsonNode();
        HttpEntity<String> httpEntity = new HttpEntity<>(refreshTokenRequestBody, buildBasicAuthHttpHeaders(
                user.getClientId(),
                user.getClientSecret()
        ));

        when(requestBuilder.buildHttpRequestEntity(eq(user), any())).thenReturn(httpEntity);
        when(restTemplate.exchange(
                eq(EBAY_TOKEN_URL),
                eq(HttpMethod.POST),
                eq(httpEntity),
                eq(String.class)
        )).thenReturn(ResponseEntity.ok(ebayTokensJsonString));
        when(objectMapper.readTree(ebayTokensJsonString)).thenReturn(ebayTokensJsonNode);

        assertEquals(ebayTokensJsonNode, ebayTokenRequestSender.sendGetAccessTokenRequest(
                user,
                refreshToken
        ));
    }
}
