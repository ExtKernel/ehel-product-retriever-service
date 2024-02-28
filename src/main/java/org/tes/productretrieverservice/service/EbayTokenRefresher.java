package org.tes.productretrieverservice.service;

import org.springframework.stereotype.Component;

@Component
public interface EbayTokenRefresher {
    public String getAccessToken();
}
