package org.tes.productretrieverservice.item;

import com.fasterxml.jackson.databind.JsonNode;
import org.tes.productretrieverservice.model.EbayItem;

import java.util.List;

public interface EbayItemJsonObjectMapper extends ItemJsonObjectMapper<EbayItem> {

    @Override
    List<EbayItem> mapItemsJsonToItems(JsonNode ebayItemJsonNode);

    @Override
    EbayItem mapItemJsonToItem(JsonNode ebayItemJsonNode);
}
