package org.tes.productretrieverservice.unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.tes.productretrieverservice.TestFactory;
import org.tes.productretrieverservice.model.AccessToken;
import org.tes.productretrieverservice.model.AuthCode;
import org.tes.productretrieverservice.model.EbayUser;
import org.tes.productretrieverservice.model.RefreshToken;
import org.tes.productretrieverservice.repository.EbayUserRepository;
import org.tes.productretrieverservice.service.AuthCodeService;
import org.tes.productretrieverservice.service.EbayAccessTokenService;
import org.tes.productretrieverservice.service.EbayRefreshTokenService;
import org.tes.productretrieverservice.service.EbayUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EbayUserServiceTest extends TestFactory {

    @Mock
    EbayUserRepository ebayUserRepository;

    @Mock
    private AuthCodeService authCodeService;

    @Mock
    private EbayRefreshTokenService refreshTokenService;

    @Mock
    private EbayAccessTokenService accessTokenService;

    @InjectMocks
    private EbayUserService ebayUserService;

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(ebayUserService, "refreshTokenService", refreshTokenService);
        ReflectionTestUtils.setField(ebayUserService, "accessTokenService", accessTokenService);
    }

    @Test
    public void givenUserId_whenGenerateAccessToken_thenReturnAccessToken() {
        EbayUser user = new EbayUser();
        long userId = 1L;
        user.setId(userId);

        RefreshToken refreshToken = buildValidRefreshToken();
        AccessToken accessToken = buildValidAccessToken();

        when(ebayUserRepository.findById(userId)).thenReturn(Optional.of(user));
        when(refreshTokenService.findLatest()).thenReturn(refreshToken);
        when(accessTokenService.generate(
                user,
                refreshToken
        )).thenReturn(accessToken);

        AccessToken result = ebayUserService.generateAccessToken(userId);

        assertEquals(accessToken.getToken(), result.getToken());
        assertEquals(accessToken.getExpiresIn(), result.getExpiresIn());
    }

    @Test
    public void givenUserId_whenGenerateRefreshToken_thenReturnRefreshToken() {
        EbayUser user = new EbayUser();
        long userId = 1L;
        user.setId(userId);

        AuthCode authCode = buildValidAuthCode();
        RefreshToken refreshToken = buildValidRefreshToken();

        when(ebayUserRepository.findById(userId)).thenReturn(Optional.of(user));
        when(authCodeService.getValid()).thenReturn(authCode);
        when(refreshTokenService.generate(
                user,
                authCode
        )).thenReturn(refreshToken);

        RefreshToken result = ebayUserService.generateRefreshToken(userId);

        assertEquals(refreshToken.getToken(), result.getToken());
        assertEquals(refreshToken.getExpiresIn(), result.getExpiresIn());
    }

    @Test
    public void givenUserId_whenGetValidRefreshToken_thenReturnValidRefreshToken() {
        EbayUser user = new EbayUser();
        long userId = 1L;
        user.setId(userId);

        RefreshToken refreshToken = buildValidRefreshToken();
        when(refreshTokenService.findLatest()).thenReturn(refreshToken);

        RefreshToken result = ebayUserService.getValidRefreshToken(userId);

        assertEquals(refreshToken.getToken(), result.getToken());
        assertEquals(refreshToken.getExpiresIn(), result.getExpiresIn());
    }

    @Test
    public void givenUserId_whenGetRefreshTokens_thenReturnRefreshTokens() {
        RefreshToken refreshToken1 = buildValidRefreshToken();
        refreshToken1.setId(1L);
        RefreshToken refreshToken2 = buildValidRefreshToken();
        refreshToken2.setId(2L);
        RefreshToken refreshToken3 = buildValidRefreshToken();
        refreshToken3.setId(3L);

        List<RefreshToken> refreshTokens = new ArrayList<>();
        refreshTokens.add(refreshToken1);
        refreshTokens.add(refreshToken2);
        refreshTokens.add(refreshToken3);

        EbayUser user = new EbayUser();
        long userId = 1L;
        user.setId(userId);
        user.setRefreshTokens(refreshTokens);

        when(ebayUserRepository.findById(userId)).thenReturn(Optional.of(user));

        assertEquals(refreshTokens, ebayUserService.getRefreshTokens(userId));
    }
}
