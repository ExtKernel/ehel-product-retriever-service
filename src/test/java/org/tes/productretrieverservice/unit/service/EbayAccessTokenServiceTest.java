package org.tes.productretrieverservice.unit.service;

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
import org.tes.productretrieverservice.repository.AccessTokenRepository;
import org.tes.productretrieverservice.service.EbayAccessTokenService;
import org.tes.productretrieverservice.token.TokenManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EbayAccessTokenServiceTest extends TestFactory {

    @Mock
    private AccessTokenRepository accessTokenRepository;

    @Mock
    private TokenManager<EbayUser, AuthCode> tokenManager;

    @InjectMocks
    private EbayAccessTokenService ebayAccessTokenService;

    @Test
    public void givenValidUserAndRefreshToken_whenGenerate_thenReturnValidAccessToken() {
        EbayUser user = buildEbayUser();
        RefreshToken refreshToken = buildValidRefreshToken();
        AccessToken accessToken = buildValidAccessToken();

        when(tokenManager.getAccessToken(
                user,
                refreshToken
        )).thenReturn(accessToken);

        assertEquals(accessToken, ebayAccessTokenService.generate(
                user,
                refreshToken
        ));
    }

    @Test
    public void givenLatestAccessToken_whenFindLatest_thenReturnLatestAccessToken() {
        AccessToken accessToken = buildValidAccessToken();

        when(accessTokenRepository.findFirstByOrderByCreationDateDesc()).thenReturn(Optional.ofNullable(accessToken));

        assertEquals(accessToken, ebayAccessTokenService.findLatest());
    }
}
