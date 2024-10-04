package org.tes.productretrieverservice.item;

import com.fasterxml.jackson.databind.JsonNode;
import org.tes.productretrieverservice.model.EbayUser;

import java.util.List;

public interface EbayItemRequestSender extends ItemRequestSender<String, EbayUser> {

    @Override
    JsonNode sendGetItemsRequest(
            EbayUser user,
            String keyword
    );

    JsonNode sendGetItemsRequest(
            EbayUser user,
            String keyword,
            String sort
    );

    JsonNode sendGetItemsRequest(
            EbayUser user,
            String keyword,
            List<String> filters
    );

    JsonNode sendGetItemsRequest(
            EbayUser user,
            String keyword,
            String sort,
            List<String> filters
    );

    JsonNode sendGetItemByIdRequest(
            EbayUser user,
            String itemId
    );

    JsonNode sendGetItemByIdRequest(
            EbayUser user,
            String itemId,
            String fieldgroups
    );
}
