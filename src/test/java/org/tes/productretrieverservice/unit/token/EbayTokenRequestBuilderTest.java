package org.tes.productretrieverservice.unit.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.tes.productretrieverservice.TestFactory;
import org.tes.productretrieverservice.model.AuthCode;
import org.tes.productretrieverservice.model.EbayUser;
import org.tes.productretrieverservice.model.RefreshToken;
import org.tes.productretrieverservice.token.EbayTokenRequestBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class EbayTokenRequestBuilderTest extends TestFactory {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private EbayTokenRequestBuilder ebayTokenRequestBuilder;

    @Test
    public void givenUserAndRequestBody_whenBuildHttpRequestEntity_thenReturnHttpEntity() {
        EbayUser user = buildEbayUser();
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(requestBody, buildBasicAuthHttpHeaders(
                user.getClientId(),
                user.getClientSecret()
        ));

        assertEquals(httpEntity, ebayTokenRequestBuilder.buildHttpRequestEntity(
                user,
                requestBody
        ));
    }

    @Test
    public void givenUserAndAuthCode_whenBuildAuthModelRequestBody_thenReturnRequestBody()
            throws Exception {
        EbayUser user = buildEbayUser();

        AuthCode authCode = buildValidAuthCode();
        MultiValueMap<String, String> requestBody = buildHashMapAuthModelRequestBody(
                user,
                authCode
        );

        assertEquals(requestBody, ebayTokenRequestBuilder.buildAuthModelRequestBody(
                user,
                authCode
        ));
    }

    @Test
    public void givenRefreshToken_whenBuildRefreshTokenRequestBody_thenReturnRequestBody()
            throws Exception {
        RefreshToken refreshToken = buildValidRefreshToken();
        MultiValueMap<String, String> requestBody = buildHashMapRefreshTokenRequestBody(refreshToken);

        assertEquals(requestBody, ebayTokenRequestBuilder.buildRefreshTokenRequestBody(refreshToken));
    }

    @Test
    public void givenNothing_whenGetRestTemplate_thenReturnRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        assertEquals(restTemplate.getClass(), ebayTokenRequestBuilder.getRestTemplate().getClass());
    }
}
