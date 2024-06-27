package org.tes.productretrieverservice.unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.tes.productretrieverservice.exception.NoRecordOfRefreshTokenException;
import org.tes.productretrieverservice.model.AccessToken;
import org.tes.productretrieverservice.model.AuthCode;
import org.tes.productretrieverservice.model.RefreshToken;
import org.tes.productretrieverservice.repository.AccessTokenRepository;
import org.tes.productretrieverservice.service.GenericAccessTokenService;
import org.tes.productretrieverservice.token.TokenManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GenericAccessTokenServiceTest {

    @Mock
    private AccessTokenRepository repository;

    @Mock
    private AccessTokenRepository tokenRepository;

    @Mock
    private TokenManager<AuthCode> tokenManager;

    private GenericAccessTokenService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new TestGenericAccessTokenService(repository, tokenRepository, tokenManager);
    }

    @Test
    void testGenerate() {
        // Given
        RefreshToken refreshToken = new RefreshToken();
        AccessToken expectedAccessToken = new AccessToken();
        when(tokenManager.getAccessToken(refreshToken)).thenReturn(expectedAccessToken);

        // When
        AccessToken result = service.generate(refreshToken);

        // Then
        assertEquals(expectedAccessToken, result);
        verify(tokenManager).getAccessToken(refreshToken);
    }

    @Test
    void testFindLatest() throws NoRecordOfRefreshTokenException {
        // Given
        AccessToken expectedAccessToken = new AccessToken();
        when(tokenRepository.findFirstByOrderByCreationDateDesc()).thenReturn(Optional.of(expectedAccessToken));

        // When
        AccessToken result = service.findLatest();

        // Then
        assertEquals(expectedAccessToken, result);
        verify(tokenRepository).findFirstByOrderByCreationDateDesc();
    }

    @Test
    void testFindLatestNoRecord() {
        // Given
        when(tokenRepository.findFirstByOrderByCreationDateDesc()).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NoRecordOfRefreshTokenException.class, () -> service.findLatest());
        verify(tokenRepository).findFirstByOrderByCreationDateDesc();
    }

    // Concrete implementation of GenericAccessTokenService for testing
    private static class TestGenericAccessTokenService extends GenericAccessTokenService {
        public TestGenericAccessTokenService(
                JpaRepository<AccessToken, Long> repository,
                AccessTokenRepository tokenRepository,
                TokenManager<AuthCode> tokenManager
        ) {
            super(repository, tokenRepository, tokenManager);
        }
    }
}
