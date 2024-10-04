package org.tes.productretrieverservice.unit.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tes.productretrieverservice.TestFactory;
import org.tes.productretrieverservice.model.AuthCode;
import org.tes.productretrieverservice.model.EbayUser;
import org.tes.productretrieverservice.model.RefreshToken;
import org.tes.productretrieverservice.repository.RefreshTokenRepository;
import org.tes.productretrieverservice.service.EbayRefreshTokenService;
import org.tes.productretrieverservice.token.TokenManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EbayRefreshTokenServiceTest extends TestFactory {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    TokenManager<EbayUser, AuthCode> tokenManager;

    @InjectMocks
    private EbayRefreshTokenService ebayRefreshTokenService;

    @Test
    public void givenValidUserAndAuthCode_whenGenerate_thenReturnValidRefreshToken() {
        EbayUser user = buildEbayUser();
        AuthCode authCode = buildValidAuthCode();
        RefreshToken refreshToken = buildValidRefreshToken();

        when(tokenManager.getRefreshToken(
                user,
                authCode
        )).thenReturn(refreshToken);

        assertEquals(refreshToken, ebayRefreshTokenService.generate(
                user,
                authCode
        ));
    }

    @Test
    public void givenLatestRefreshToken_whenFindLatest_thenReturnLatestRefreshToken() {
        RefreshToken refreshToken = buildValidRefreshToken();

        when(refreshTokenRepository.findFirstByOrderByCreationDateDesc()).thenReturn(Optional.ofNullable(refreshToken));

        assertEquals(refreshToken, ebayRefreshTokenService.findLatest());
    }
}
