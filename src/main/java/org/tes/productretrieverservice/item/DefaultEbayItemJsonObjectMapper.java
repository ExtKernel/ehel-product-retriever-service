package org.tes.productretrieverservice.item;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;
import org.tes.productretrieverservice.model.EbayItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class DefaultEbayItemJsonObjectMapper implements EbayItemJsonObjectMapper {

    @Override
    public List<EbayItem> mapItemsJsonToItems(JsonNode ebayItemJsonNode) {
        JsonNode itemSummariesEbayJsonNode = ebayItemJsonNode.get("itemSummaries");

        List<EbayItem> ebayItems = new ArrayList<>();
        itemSummariesEbayJsonNode.forEach(ebayItemJson -> ebayItems.add(mapItemJsonToItem(ebayItemJson)));

        return ebayItems;
    }

    @Override
    public EbayItem mapItemJsonToItem(JsonNode ebayItemJsonNode) {
        EbayItem ebayItem = new EbayItem();

        ebayItem.setItemId(ebayItemJsonNode.path("itemId").asText(null));
        ebayItem.setLegacyItemId(ebayItemJsonNode.path("legacyItemId").asText(null));
        ebayItem.setTitle(ebayItemJsonNode.path("title").asText(null));
        ebayItem.setShortDescription(ebayItemJsonNode.path("shortDescription").asText(null));
        ebayItem.setDescription(ebayItemJsonNode.path("description").asText(null));
        ebayItem.setCategory(ebayItemJsonNode.path("categoryPath").asText(null));
        ebayItem.setAspects(getAspects(ebayItemJsonNode.path("localizedAspects")));
        ebayItem.setPrimaryImgUrl(ebayItemJsonNode.path("image").path("imageUrl").asText(null));
        ebayItem.setPrice(ebayItemJsonNode.path("price").path("value").asDouble());
        ebayItem.setPriceCurrency(ebayItemJsonNode.path("price").path("currency").asText(null));
        ebayItem.setBuyingOptions(getStringList(ebayItemJsonNode.path("buyingOptions")));
        ebayItem.setUrl(ebayItemJsonNode.path("itemWebUrl").asText(null));
        ebayItem.setCondition(ebayItemJsonNode.path("condition").asText(null));

        JsonNode shipping = ebayItemJsonNode.path("shippingOptions").path(0);
        ebayItem.setShippingType(shipping.path("type").asText(null));
        ebayItem.setShippingCost(shipping.path("shippingCost").path("value").asDouble());
        ebayItem.setShippingCostCurrency(shipping.path("shippingCost").path("currency").asText(null));
        ebayItem.setShippingCostType(shipping.path("shippingCostType").asText(null));

        JsonNode location = ebayItemJsonNode.path("itemLocation");
        ebayItem.setItemLocationCity(location.path("city").asText(null));
        ebayItem.setItemLocationCountry(location.path("country").asText(null));
        ebayItem.setShippingRegions(getStringList(ebayItemJsonNode.path("shipToLocations").path("regionIncluded"), "regionName"));
        ebayItem.setAdditionalImgUrls(getStringList(ebayItemJsonNode.path("additionalImages"), "imageUrl"));
        ebayItem.setCategories(getStringList(ebayItemJsonNode.path("categories"), "categoryName"));

        return ebayItem;
    }

    private List<Map<String, String>> getAspects(JsonNode aspects) {
        return StreamSupport.stream(aspects.spliterator(), false)
                .map(aspect -> Map.of(
                        aspect.path("name").asText(""),
                        aspect.path("value").asText("")
                ))
                .collect(Collectors.toList());
    }

    private List<String> getStringList(JsonNode node) {
        return getStringList(node, null);
    }

    private List<String> getStringList(JsonNode node, String field) {
        return StreamSupport.stream(node.spliterator(), false)
                .map(n -> field == null ? n.asText() : n.path(field).asText())
                .collect(Collectors.toList());
    }
}
