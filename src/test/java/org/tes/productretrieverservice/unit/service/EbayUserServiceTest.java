package org.tes.productretrieverservice.unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.tes.productretrieverservice.exception.NoRecordOfRefreshTokenException;
import org.tes.productretrieverservice.exception.RefreshTokenIsNullException;
import org.tes.productretrieverservice.model.AccessToken;
import org.tes.productretrieverservice.model.AuthCode;
import org.tes.productretrieverservice.model.EbayUser;
import org.tes.productretrieverservice.model.RefreshToken;
import org.tes.productretrieverservice.repository.EbayUserRepository;
import org.tes.productretrieverservice.service.AuthCodeService;
import org.tes.productretrieverservice.service.EbayUserService;
import org.tes.productretrieverservice.service.TokenService;
import org.tes.productretrieverservice.token.TokenManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EbayUserServiceTest {

    @Mock
    private EbayUserRepository repository;
    @Mock
    private AuthCodeService authCodeService;
    @Mock
    private TokenManager<AuthCode> tokenManager;
    @Mock
    private TokenService<RefreshToken, AuthCode> refreshTokenService;
    @Mock
    private TokenService<AccessToken, RefreshToken> accessTokenService;

    private EbayUserService ebayUserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ebayUserService = new EbayUserService(repository, authCodeService, tokenManager, refreshTokenService, accessTokenService);
    }

    @Test
    void testGenerateAccessToken() {
        // Given
        Long userId = 1L;
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setCreationDate(new Date());
        refreshToken.setExpiresIn(3600000); // 1 hour
        AccessToken expectedAccessToken = new AccessToken();

        when(refreshTokenService.findLatest()).thenReturn(refreshToken);
        when(accessTokenService.generate(refreshToken)).thenReturn(expectedAccessToken);

        // When
        AccessToken result = ebayUserService.generateAccessToken(userId);

        // Then
        assertEquals(expectedAccessToken, result);
        verify(refreshTokenService).findLatest();
        verify(accessTokenService).generate(refreshToken);
    }

    @Test
    void testGenerateRefreshToken() {
        // Given
        Long userId = 1L;
        AuthCode authCode = new AuthCode();
        RefreshToken expectedRefreshToken = new RefreshToken();

        when(authCodeService.getValid()).thenReturn(authCode);
        when(refreshTokenService.generate(authCode)).thenReturn(expectedRefreshToken);

        // When
        RefreshToken result = ebayUserService.generateRefreshToken(userId);

        // Then
        assertEquals(expectedRefreshToken, result);
        verify(authCodeService).getValid();
        verify(refreshTokenService).generate(authCode);
    }

    @Test
    void testSaveRefreshToken() {
        // Given
        Long userId = 1L;
        RefreshToken refreshToken = new RefreshToken();
        EbayUser user = new EbayUser("test-username");
        user.setRefreshTokens(new ArrayList<>());

        when(repository.findById(userId)).thenReturn(Optional.of(user));
        when(repository.save(user)).thenReturn(user);

        // When
        RefreshToken result = ebayUserService.saveRefreshToken(userId, Optional.of(refreshToken));

        // Then
        assertEquals(refreshToken, result);
        assertEquals(1, user.getRefreshTokens().size());
        assertEquals(refreshToken, user.getRefreshTokens().get(0));
        verify(refreshTokenService).save(Optional.of(refreshToken));
        verify(repository).save(user);
    }

    @Test
    void testSaveRefreshTokenNullToken() {
        // Given
        Long userId = 1L;

        // When & Then
        assertThrows(RefreshTokenIsNullException.class, () ->
                ebayUserService.saveRefreshToken(userId, Optional.empty())
        );
    }

    @Test
    void testGetValidRefreshTokenValid() {
        // Given
        Long userId = 1L;
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setCreationDate(new Date());
        refreshToken.setExpiresIn(3600000); // 1 hour

        when(refreshTokenService.findLatest()).thenReturn(refreshToken);

        // When
        RefreshToken result = ebayUserService.getValidRefreshToken(userId);

        // Then
        assertEquals(refreshToken, result);
        verify(refreshTokenService).findLatest();
    }

    @Test
    void testGetValidRefreshTokenExpired() {
        // Given
        Long userId = 1L;
        RefreshToken oldToken = new RefreshToken();
        oldToken.setCreationDate(new Date(0)); // Set to epoch time
        oldToken.setExpiresIn(3600000); // 1 hour

        RefreshToken newToken = new RefreshToken();
        EbayUser user = new EbayUser("test-username");
        user.setRefreshTokens(new ArrayList<>());

        when(refreshTokenService.findLatest()).thenReturn(oldToken);
        when(authCodeService.getValid()).thenReturn(new AuthCode());
        when(refreshTokenService.generate(any())).thenReturn(newToken);
        when(repository.findById(userId)).thenReturn(Optional.of(user));
        when(repository.save(user)).thenReturn(user);

        // When
        RefreshToken result = ebayUserService.getValidRefreshToken(userId);

        // Then
        assertEquals(newToken, result);
        verify(refreshTokenService).findLatest();
        verify(refreshTokenService).generate(any());
        verify(refreshTokenService).save(Optional.of(newToken));
    }

    @Test
    void testGetValidRefreshTokenNoRecord() {
        // Given
        Long userId = 1L;
        RefreshToken newToken = new RefreshToken();
        EbayUser user = new EbayUser("test-username");
        user.setRefreshTokens(new ArrayList<>());

        when(refreshTokenService.findLatest()).thenThrow(new NoRecordOfRefreshTokenException("no-record"));
        when(authCodeService.getValid()).thenReturn(new AuthCode());
        when(refreshTokenService.generate(any())).thenReturn(newToken);
        when(repository.findById(userId)).thenReturn(Optional.of(user));
        when(repository.save(user)).thenReturn(user);

        // When
        RefreshToken result = ebayUserService.getValidRefreshToken(userId);

        // Then
        assertEquals(newToken, result);
        verify(refreshTokenService).findLatest();
        verify(refreshTokenService).generate(any());
        verify(refreshTokenService).save(Optional.of(newToken));
    }

    @Test
    void testGetRefreshTokens() {
        // Given
        Long userId = 1L;
        EbayUser user = new EbayUser("test-username");
        List<RefreshToken> refreshTokens = new ArrayList<>();
        refreshTokens.add(new RefreshToken());
        user.setRefreshTokens(refreshTokens);

        when(repository.findById(userId)).thenReturn(Optional.of(user));

        // When
        List<RefreshToken> result = ebayUserService.getRefreshTokens(userId);

        // Then
        assertEquals(refreshTokens, result);
        verify(repository).findById(userId);
    }

    @Test
    void testGetRefreshTokensNullList() {
        // Given
        Long userId = 1L;
        EbayUser user = new EbayUser();
        user.setRefreshTokens(null);

        when(repository.findById(userId)).thenReturn(Optional.of(user));

        // When
        List<RefreshToken> result = ebayUserService.getRefreshTokens(userId);

        // Then
        assertTrue(result.isEmpty());
        verify(repository).findById(userId);
    }
}
