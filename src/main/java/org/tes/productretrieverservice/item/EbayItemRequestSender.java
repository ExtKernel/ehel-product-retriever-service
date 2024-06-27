package org.tes.productretrieverservice.item;

import com.fasterxml.jackson.databind.JsonNode;
import org.tes.productretrieverservice.model.EbayUser;

import java.util.List;

public interface EbayItemRequestSender extends ItemRequestSender<String, EbayUser> {

    @Override
    JsonNode sendGetItemRequest(
            EbayUser user,
            String keyword
    );

    JsonNode sendGetItemRequest(
            EbayUser user,
            String keyword,
            String sort
    );

    JsonNode sendGetItemRequest(
            EbayUser user,
            String keyword,
            List<String> filters
    );

    JsonNode sendGetItemRequest(
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
