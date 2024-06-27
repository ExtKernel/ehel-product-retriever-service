package org.tes.productretrieverservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tes.productretrieverservice.model.AuthCode;
import org.tes.productretrieverservice.repository.RefreshTokenRepository;
import org.tes.productretrieverservice.token.TokenManager;

@Service
public class EbayRefreshTokenService extends GenericRefreshTokenService<AuthCode> {

    @Autowired
    public EbayRefreshTokenService(
            RefreshTokenRepository repository,
            RefreshTokenRepository tokenRepository,
            TokenManager<AuthCode> tokenManager
    ) {
        super(
                repository,
                tokenRepository,
                tokenManager
        );
    }
}
