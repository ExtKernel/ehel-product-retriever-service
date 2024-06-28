package org.tes.productretrieverservice.service;

import org.tes.productretrieverservice.exception.NoRecordOfRefreshTokenException;
import org.tes.productretrieverservice.model.AuthModel;
import org.tes.productretrieverservice.model.Token;

/**
 * An interface for a service that performs operations on {@link AuthModel} tokens.
 *
 * @param <TokenType> the type of the token.
 * @param <AuthModelType> the type of the {@link AuthModel} object
 *                      that is needed for the token generation.
 */
public interface TokenService<TokenType extends Token, AuthModelType extends AuthModel>
        extends CrudService<TokenType, Long> {

    /**
     * Generate a {@link TokenType} object.
     *
     * @param authModel the {@link AuthModelType} that will be used while generating.
     * @return the {@link TokenType} object.
     */
    TokenType generate(AuthModelType authModel);

    /**
     * Retrieves the latest {@link TokenType} object.
     *
     * @return the latest {@link TokenType} object.
     * @throws NoRecordOfRefreshTokenException if there are no {@link TokenType}
     *                                        objects in the database.
     */
    TokenType findLatest() throws NoRecordOfRefreshTokenException;
}
