package org.tes.productretrieverservice.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tes.productretrieverservice.exception.AccessTokenIsNullException;
import org.tes.productretrieverservice.exception.NoRecordOfAccessTokenException;
import org.tes.productretrieverservice.model.AccessToken;
import org.tes.productretrieverservice.model.AuthCode;
import org.tes.productretrieverservice.model.RefreshToken;
import org.tes.productretrieverservice.model.User;
import org.tes.productretrieverservice.repository.AccessTokenRepository;
import org.tes.productretrieverservice.token.TokenManager;

/**
 * A generic class that implements the generic behaviour
 * of services that manage {@link AccessToken} objects.
 */
public abstract class GenericAccessTokenService<UserType extends User>
        extends GenericCrudService<AccessToken, Long>
        implements TokenService<UserType, AccessToken, RefreshToken> {
    AccessTokenRepository tokenRepository;
    TokenManager<UserType, AuthCode> tokenManager;

    public GenericAccessTokenService(
            JpaRepository<AccessToken, Long> repository,
            AccessTokenRepository tokenRepository,
            TokenManager<UserType, AuthCode> tokenManager
    ) {
        super(repository);
        this.tokenRepository = tokenRepository;
        this.tokenManager = tokenManager;
    }

    @Override
    public AccessToken generate(
            UserType user,
            RefreshToken refreshToken
    ) {
        return tokenManager.getAccessToken(
                user,
                refreshToken
        );
    }

    @Override
    public AccessToken findLatest() throws AccessTokenIsNullException {
        try {
            return tokenRepository.findFirstByOrderByCreationDateDesc()
                    .orElseThrow(() -> new AccessTokenIsNullException("The access token is null"));
        } catch (AccessTokenIsNullException exception) {
            throw new NoRecordOfAccessTokenException(
                    "There is no record of access tokens in the database: " + exception.getMessage(),
                    exception
            );
        }
    }
}
