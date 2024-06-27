package org.tes.productretrieverservice.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tes.productretrieverservice.model.AccessToken;
import org.tes.productretrieverservice.model.AuthCode;
import org.tes.productretrieverservice.model.RefreshToken;

@Component
public class EbayTokenManager implements TokenManager<AuthCode> {
    private final TokenRequestSender requestSender;
    private final TokenJsonObjectMapper jsonObjectMapper;

    @Autowired
    public EbayTokenManager(
            TokenRequestSender requestSender,
            TokenJsonObjectMapper jsonObjectMapper
    ) {
        this.requestSender = requestSender;
        this.jsonObjectMapper = jsonObjectMapper;
    }

    @Override
    public RefreshToken getRefreshToken(AuthCode authCode) {
        return jsonObjectMapper.mapUserRefreshTokenJsonNodeToUserRefreshToken(
                requestSender.sendGetRefreshTokenRequest(authCode));
    }

    @Override
    public AccessToken getAccessToken(RefreshToken refreshToken) {
        return jsonObjectMapper.mapUserAccessTokenJsonNodeToUserAccessToken(
                requestSender.sendGetAccessTokenRequest(refreshToken));
    }
}
