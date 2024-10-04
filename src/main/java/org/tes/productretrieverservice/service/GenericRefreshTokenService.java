package org.tes.productretrieverservice.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tes.productretrieverservice.exception.NoRecordOfRefreshTokenException;
import org.tes.productretrieverservice.exception.RefreshTokenIsNullException;
import org.tes.productretrieverservice.model.AuthModel;
import org.tes.productretrieverservice.model.RefreshToken;
import org.tes.productretrieverservice.model.User;
import org.tes.productretrieverservice.repository.RefreshTokenRepository;
import org.tes.productretrieverservice.token.TokenManager;

/**
 * A generic class that implements the generic behaviour
 * of services that manage {@link RefreshToken} objects.
 *
 * @param <T> the object, which is supposed to be used for retrieval of refresh tokens.
 */
public abstract class GenericRefreshTokenService<UserType extends User, AuthModelType extends AuthModel>
        extends GenericCrudService<RefreshToken, Long>
        implements TokenService<UserType, RefreshToken, AuthModelType> {
    RefreshTokenRepository tokenRepository;
    TokenManager<UserType, AuthModelType> tokenManager;

    public GenericRefreshTokenService(
            JpaRepository<RefreshToken, Long> repository,
            RefreshTokenRepository tokenRepository,
            TokenManager<UserType, AuthModelType> tokenManager
    ) {
        super(repository);
        this.tokenRepository = tokenRepository;
        this.tokenManager = tokenManager;
    }

    @Override
    public RefreshToken generate(
            UserType user,
            AuthModelType authModel
    ) {
        return tokenManager.getRefreshToken(
                user,
                authModel
        );
    }

    @Override
    public RefreshToken findLatest() throws NoRecordOfRefreshTokenException {
        try {
            return tokenRepository.findFirstByOrderByCreationDateDesc()
                    .orElseThrow(() -> new RefreshTokenIsNullException("The refresh token is null"));
        } catch (RefreshTokenIsNullException exception) {
            throw new NoRecordOfRefreshTokenException(
                    "There is no record of refresh tokens in the database: " + exception.getMessage(),
                    exception
            );
        }
    }
}
