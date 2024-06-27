package org.tes.productretrieverservice.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tes.productretrieverservice.exception.NoRecordOfRefreshTokenException;
import org.tes.productretrieverservice.exception.RefreshTokenIsNullException;
import org.tes.productretrieverservice.model.AccessToken;
import org.tes.productretrieverservice.model.AuthCode;
import org.tes.productretrieverservice.model.RefreshToken;
import org.tes.productretrieverservice.repository.AccessTokenRepository;
import org.tes.productretrieverservice.token.TokenManager;

/**
 * A generic class that implements the generic behaviour
 * of services that manage {@link AccessToken} objects.
 */
public abstract class GenericAccessTokenService
        extends GenericCrudService<AccessToken, Long>
        implements TokenService<AccessToken, RefreshToken> {
    AccessTokenRepository tokenRepository;
    TokenManager<AuthCode> tokenManager;

    public GenericAccessTokenService(
            JpaRepository<AccessToken, Long> repository,
            AccessTokenRepository tokenRepository,
            TokenManager<AuthCode> tokenManager
    ) {
        super(repository);
        this.tokenRepository = tokenRepository;
        this.tokenManager = tokenManager;
    }

    @Override
    public AccessToken generate(RefreshToken refreshToken) {
        return tokenManager.getAccessToken(refreshToken);
    }

    @Override
    public AccessToken findLatest() throws NoRecordOfRefreshTokenException {
        try {
            return tokenRepository.findFirstByOrderByCreationDateDesc()
                    .orElseThrow(() -> new RefreshTokenIsNullException("The refresh token is null"));
        } catch (RefreshTokenIsNullException exception) {
            throw new NoRecordOfRefreshTokenException(
                    "There is no record of access tokens in the database: " + exception.getMessage(),
                    exception
            );
        }
    }
}
