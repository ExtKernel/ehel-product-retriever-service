package org.tes.productretrieverservice.unit.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import org.tes.productretrieverservice.TestFactory;
import org.tes.productretrieverservice.model.AuthCode;
import org.tes.productretrieverservice.model.EbayUser;
import org.tes.productretrieverservice.model.RefreshToken;
import org.tes.productretrieverservice.token.EbayTokenRequestBuilder;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EbayTokenRequestBuilderTest extends TestFactory {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private EbayTokenRequestBuilder ebayTokenRequestBuilder;

    @Test
    public void givenUserAndRequestBody_whenBuildHttpRequestEntity_thenReturnHttpEntity() {
        EbayUser user = buildEbayUser();
        String requestBody = "test-request-body";

        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, buildBasicAuthHttpHeaders(
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
        Map<String, String> requestBody = buildHashMapAuthModelRequestBody(
                user,
                authCode
        );
        String stringRequestBody = buildStringAuthModelRequestBody(
                user,
                authCode
        );

        when(objectMapper.writeValueAsString(requestBody)).thenReturn(stringRequestBody);

        assertEquals(stringRequestBody, ebayTokenRequestBuilder.buildAuthModelRequestBody(
                user,
                authCode
        ));
    }

    @Test
    public void givenRefreshToken_whenBuildRefreshTokenRequestBody_thenReturnRequestBody()
            throws Exception {
        RefreshToken refreshToken = buildValidRefreshToken();
        Map<String, String> requestBody = buildHashMapRefreshTokenRequestBody(refreshToken);
        String stringRequestBody = buildStringRefreshTokenRequestBody(refreshToken);

        when(objectMapper.writeValueAsString(requestBody)).thenReturn(stringRequestBody);

        assertEquals(stringRequestBody, ebayTokenRequestBuilder.buildRefreshTokenRequestBody(refreshToken));
    }

    @Test
    public void givenNothing_whenGetRestTemplate_thenReturnRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        assertEquals(restTemplate.getClass(), ebayTokenRequestBuilder.getRestTemplate().getClass());
    }
}
