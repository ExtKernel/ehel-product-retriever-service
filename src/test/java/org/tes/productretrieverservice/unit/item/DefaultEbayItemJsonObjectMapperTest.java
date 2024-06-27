package org.tes.productretrieverservice.unit.item;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.tes.productretrieverservice.item.DefaultEbayItemJsonObjectMapper;
import org.tes.productretrieverservice.model.EbayItem;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultEbayItemJsonObjectMapperTest {

    private DefaultEbayItemJsonObjectMapper mapper;
    private ObjectMapper jsonMapper;

    @BeforeEach
    void setUp() {
        mapper = new DefaultEbayItemJsonObjectMapper();
        jsonMapper = new ObjectMapper();
    }

    @Test
    void testMapItemJsonToItem() throws IOException {
        // Given
        String json = """
            {
              "itemId": "v1|123456789|0",
              "legacyItemId": "123456789",
              "title": "Test Item",
              "shortDescription": "Short desc",
              "description": "Long description",
              "categoryPath": "Electronics > Computers",
              "localizedAspects": [
                {"name": "Brand", "value": "TestBrand"},
                {"name": "Model", "value": "TestModel"}
              ],
              "image": {"imageUrl": "http://example.com/image.jpg"},
              "price": {"value": 99.99, "currency": "USD"},
              "buyingOptions": ["FIXED_PRICE", "AUCTION"],
              "itemWebUrl": "http://example.com/item",
              "condition": "New",
              "shippingOptions": [
                {
                  "type": "STANDARD",
                  "shippingCost": {"value": 5.99, "currency": "USD"},
                  "shippingCostType": "FIXED"
                }
              ],
              "itemLocation": {"city": "New York", "country": "US"},
              "shipToLocations": {"regionIncluded": [{"regionName": "Worldwide"}]},
              "additionalImages": [{"imageUrl": "http://example.com/image2.jpg"}],
              "categories": [{"categoryName": "Electronics"}, {"categoryName": "Computers"}]
            }
            """;

        JsonNode jsonNode = jsonMapper.readTree(json);

        // When
        EbayItem item = mapper.mapItemJsonToItem(jsonNode);

        // Then
        assertEquals("v1|123456789|0", item.getItemId());
        assertEquals("123456789", item.getLegacyItemId());
        assertEquals("Test Item", item.getTitle());
        assertEquals("Short desc", item.getShortDescription());
        assertEquals("Long description", item.getDescription());
        assertEquals("Electronics > Computers", item.getCategory());
        assertEquals(List.of(Map.of("Brand", "TestBrand"), Map.of("Model", "TestModel")), item.getAspects());
        assertEquals("http://example.com/image.jpg", item.getPrimaryImgUrl());
        assertEquals(99.99, item.getPrice());
        assertEquals("USD", item.getPriceCurrency());
        assertEquals(List.of("FIXED_PRICE", "AUCTION"), item.getBuyingOptions());
        assertEquals("http://example.com/item", item.getUrl());
        assertEquals("New", item.getCondition());
        assertEquals("STANDARD", item.getShippingType());
        assertEquals(5.99, item.getShippingCost());
        assertEquals("USD", item.getShippingCostCurrency());
        assertEquals("FIXED", item.getShippingCostType());
        assertEquals("New York", item.getItemLocationCity());
        assertEquals("US", item.getItemLocationCountry());
        assertEquals(List.of("Worldwide"), item.getShippingRegions());
        assertEquals(List.of("http://example.com/image2.jpg"), item.getAdditionalImgUrls());
        assertEquals(List.of("Electronics", "Computers"), item.getCategories());
    }

    @Test
    void testMapItemJsonToItemWithMissingFields() throws IOException {
        // Given
        String json = """
            {
              "itemId": "v1|123456789|0",
              "title": "Test Item",
              "price": {"value": 99.99, "currency": "USD"}
            }
            """;

        JsonNode jsonNode = jsonMapper.readTree(json);

        // When
        EbayItem item = mapper.mapItemJsonToItem(jsonNode);

        // Then
        assertEquals("v1|123456789|0", item.getItemId());
        assertEquals("Test Item", item.getTitle());
        assertEquals(99.99, item.getPrice());
        assertEquals("USD", item.getPriceCurrency());

        assertNull(item.getLegacyItemId());
        assertNull(item.getShortDescription());
        assertNull(item.getDescription());
        assertNull(item.getCategory());
        assertTrue(item.getAspects().isEmpty());
        assertNull(item.getPrimaryImgUrl());
        assertTrue(item.getBuyingOptions().isEmpty());
        assertNull(item.getUrl());
        assertNull(item.getCondition());
        assertNull(item.getShippingType());
        assertEquals(0.0, item.getShippingCost());
        assertNull(item.getShippingCostCurrency());
        assertNull(item.getShippingCostType());
        assertNull(item.getItemLocationCity());
        assertNull(item.getItemLocationCountry());
        assertTrue(item.getShippingRegions().isEmpty());
        assertTrue(item.getAdditionalImgUrls().isEmpty());
        assertTrue(item.getCategories().isEmpty());
    }
}
