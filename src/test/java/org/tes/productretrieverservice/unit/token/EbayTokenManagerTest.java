package org.tes.productretrieverservice.unit.token;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tes.productretrieverservice.TestFactory;
import org.tes.productretrieverservice.model.AccessToken;
import org.tes.productretrieverservice.model.AuthCode;
import org.tes.productretrieverservice.model.EbayUser;
import org.tes.productretrieverservice.model.RefreshToken;
import org.tes.productretrieverservice.token.EbayTokenManager;
import org.tes.productretrieverservice.token.TokenJsonObjectMapper;
import org.tes.productretrieverservice.token.TokenRequestSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EbayTokenManagerTest extends TestFactory {

    @Mock
    private TokenRequestSender<EbayUser, AuthCode> requestSender;

    @Mock
    private TokenJsonObjectMapper jsonObjectMapper;

    @InjectMocks
    private EbayTokenManager ebayTokenManager;

    @Test
    public void givenUserAndAuthCode_whenGetRefreshToken_thenReturnRefreshToken()
            throws Exception {
        EbayUser user = buildEbayUser();
        AuthCode authCode = buildValidAuthCode();
        JsonNode refreshTokenJsonNode = getEbayTokensJsonNode();
        RefreshToken refreshToken = buildValidRefreshToken();

        when(requestSender.sendGetRefreshTokenRequest(
                user,
                authCode
        )).thenReturn(refreshTokenJsonNode);
        when(jsonObjectMapper.mapRefreshTokenJsonNodeToUserRefreshToken(refreshTokenJsonNode)).thenReturn(refreshToken);

        RefreshToken result = ebayTokenManager.getRefreshToken(
                user,
                authCode
        );

        assertEquals(refreshToken.getToken(), result.getToken());
        assertEquals(refreshToken.getExpiresIn(), result.getExpiresIn());
    }

    @Test
    public void givenUserAndRefreshToken_whenGetAccessToken_thenReturnAccessToken()
            throws Exception {
        EbayUser user = buildEbayUser();
        JsonNode accessTokenJsonNode = getEbayTokensJsonNode();
        AccessToken accessToken = buildValidAccessToken();
        RefreshToken refreshToken = buildValidRefreshToken();

        when(requestSender.sendGetAccessTokenRequest(
                user,
                refreshToken
        )).thenReturn(accessTokenJsonNode);
        when(jsonObjectMapper.mapAccessTokenJsonNodeToUserAccessToken(accessTokenJsonNode)).thenReturn(accessToken);

        AccessToken result = ebayTokenManager.getAccessToken(
                user,
                refreshToken
        );

        assertEquals(accessToken.getToken(), result.getToken());
        assertEquals(accessToken.getExpiresIn(), result.getExpiresIn());
    }
}
