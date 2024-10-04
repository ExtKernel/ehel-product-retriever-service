package org.tes.productretrieverservice.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tes.productretrieverservice.model.AccessToken;
import org.tes.productretrieverservice.model.AuthCode;
import org.tes.productretrieverservice.model.EbayUser;
import org.tes.productretrieverservice.model.RefreshToken;

@Component
public class EbayTokenManager implements TokenManager<EbayUser, AuthCode> {
    private final TokenRequestSender<EbayUser, AuthCode> requestSender;
    private final TokenJsonObjectMapper jsonObjectMapper;

    @Autowired
    public EbayTokenManager(
            TokenRequestSender<EbayUser, AuthCode> requestSender,
            TokenJsonObjectMapper jsonObjectMapper
    ) {
        this.requestSender = requestSender;
        this.jsonObjectMapper = jsonObjectMapper;
    }

    @Override
    public RefreshToken getRefreshToken(
            EbayUser user,
            AuthCode authCode
    ) {
        return jsonObjectMapper.mapRefreshTokenJsonNodeToUserRefreshToken(
                requestSender.sendGetRefreshTokenRequest(
                        user,
                        authCode
                )
        );
    }

    @Override
    public AccessToken getAccessToken(
            EbayUser user,
            RefreshToken refreshToken
    ) {
        return jsonObjectMapper.mapAccessTokenJsonNodeToUserAccessToken(
                requestSender.sendGetAccessTokenRequest(
                        user,
                        refreshToken
                )
        );
    }
}
