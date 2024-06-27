package org.tes.productretrieverservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tes.productretrieverservice.model.AuthCode;
import org.tes.productretrieverservice.repository.AccessTokenRepository;
import org.tes.productretrieverservice.token.TokenManager;

@Service
public class EbayAccessTokenService extends GenericAccessTokenService {

    @Autowired
    public EbayAccessTokenService(
            AccessTokenRepository repository,
            AccessTokenRepository tokenRepository,
            TokenManager<AuthCode> tokenManager
    ) {
        super(
                repository,
                tokenRepository,
                tokenManager
        );
    }
}
