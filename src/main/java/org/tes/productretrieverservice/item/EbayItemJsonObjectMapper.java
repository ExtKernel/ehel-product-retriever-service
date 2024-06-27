package org.tes.productretrieverservice.item;

import com.fasterxml.jackson.databind.JsonNode;
import org.tes.productretrieverservice.model.EbayItem;

public interface EbayItemJsonObjectMapper extends ItemJsonObjectMapper<EbayItem> {

    @Override
    EbayItem mapItemJsonToItem(JsonNode ebayItemJsonNode);
}
