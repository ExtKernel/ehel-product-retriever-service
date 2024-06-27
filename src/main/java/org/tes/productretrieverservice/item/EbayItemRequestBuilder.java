package org.tes.productretrieverservice.item;

import org.springframework.http.HttpEntity;

import java.util.List;

public interface EbayItemRequestBuilder extends ItemRequestBuilder<Long, String> {

    @Override
    HttpEntity buildHttpRequestEntity(
            String requestBody,
            Long userId
    );

    @Override
    HttpEntity buildAuthOnlyHttpRequestEntity(
            Long userId
    );

    @Override
    String buildRequestUrl(
            String url,
            String keyword
    );

    String buildRequestUrl(
            String url,
            String keyword,
            String sort
    );

    String buildRequestUrl(
            String url,
            String keyword,
            List<String> filters
    );

    String buildRequestUrl(
            String url,
            String keyword,
            String sort,
            List<String> filters
    );

    String buildItemIdRequestUrl(
            String url,
            String itemId
    );

    String buildItemIdRequestUrl(
            String url,
            String itemId,
            String fieldgroups
    );
}
