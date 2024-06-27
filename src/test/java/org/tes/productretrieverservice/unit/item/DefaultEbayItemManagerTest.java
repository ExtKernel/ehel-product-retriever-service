package org.tes.productretrieverservice.unit.item;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.tes.productretrieverservice.item.DefaultEbayItemManager;
import org.tes.productretrieverservice.item.EbayItemJsonObjectMapper;
import org.tes.productretrieverservice.item.EbayItemRequestSender;
import org.tes.productretrieverservice.model.EbayItem;
import org.tes.productretrieverservice.model.EbayUser;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DefaultEbayItemManagerTest {

    @Mock
    private EbayItemRequestSender requestSender;

    @Mock
    private EbayItemJsonObjectMapper jsonObjectMapper;

    private DefaultEbayItemManager itemManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        itemManager = new DefaultEbayItemManager(requestSender, jsonObjectMapper);
    }

    @Test
    void testGetItem() {
        // Given
        EbayUser user = new EbayUser();
        String keyword = "test";
        JsonNode jsonNode = mock(JsonNode.class);
        EbayItem expectedItem = new EbayItem();

        // When
        when(requestSender.sendGetItemRequest(user, keyword)).thenReturn(jsonNode);
        when(jsonObjectMapper.mapItemJsonToItem(jsonNode)).thenReturn(expectedItem);
        EbayItem result = itemManager.getItem(user, keyword);

        // Then
        assertEquals(expectedItem, result);
        verify(requestSender).sendGetItemRequest(user, keyword);
        verify(jsonObjectMapper).mapItemJsonToItem(jsonNode);
    }

    @Test
    void testGetItemWithSort() {
        // Given
        EbayUser user = new EbayUser();
        String keyword = "test";
        String sort = "price";
        JsonNode jsonNode = mock(JsonNode.class);
        EbayItem expectedItem = new EbayItem();

        // When
        when(requestSender.sendGetItemRequest(user, keyword, sort)).thenReturn(jsonNode);
        when(jsonObjectMapper.mapItemJsonToItem(jsonNode)).thenReturn(expectedItem);
        EbayItem result = itemManager.getItem(user, keyword, sort);

        // Then
        assertEquals(expectedItem, result);
        verify(requestSender).sendGetItemRequest(user, keyword, sort);
        verify(jsonObjectMapper).mapItemJsonToItem(jsonNode);
    }

    @Test
    void testGetItemWithFilters() {
        // Given
        EbayUser user = new EbayUser();
        String keyword = "test";
        List<String> filters = Arrays.asList("condition", "price");
        JsonNode jsonNode = mock(JsonNode.class);
        EbayItem expectedItem = new EbayItem();

        // When
        when(requestSender.sendGetItemRequest(user, keyword, filters)).thenReturn(jsonNode);
        when(jsonObjectMapper.mapItemJsonToItem(jsonNode)).thenReturn(expectedItem);
        EbayItem result = itemManager.getItem(user, keyword, filters);

        // Then
        assertEquals(expectedItem, result);
        verify(requestSender).sendGetItemRequest(user, keyword, filters);
        verify(jsonObjectMapper).mapItemJsonToItem(jsonNode);
    }

    @Test
    void testGetItemWithSortAndFilters() {
        // Given
        EbayUser user = new EbayUser();
        String keyword = "test";
        String sort = "price";
        List<String> filters = Arrays.asList("condition", "brand");
        JsonNode jsonNode = mock(JsonNode.class);
        EbayItem expectedItem = new EbayItem();

        // When
        when(requestSender.sendGetItemRequest(user, keyword, sort, filters)).thenReturn(jsonNode);
        when(jsonObjectMapper.mapItemJsonToItem(jsonNode)).thenReturn(expectedItem);
        EbayItem result = itemManager.getItem(user, keyword, sort, filters);

        // Then
        assertEquals(expectedItem, result);
        verify(requestSender).sendGetItemRequest(user, keyword, sort, filters);
        verify(jsonObjectMapper).mapItemJsonToItem(jsonNode);
    }

    @Test
    void testGetItemById() {
        // Given
        EbayUser user = new EbayUser();
        String itemId = "123456";
        JsonNode jsonNode = mock(JsonNode.class);
        EbayItem expectedItem = new EbayItem();

        // When
        when(requestSender.sendGetItemByIdRequest(user, itemId)).thenReturn(jsonNode);
        when(jsonObjectMapper.mapItemJsonToItem(jsonNode)).thenReturn(expectedItem);
        EbayItem result = itemManager.getItemById(user, itemId);

        // Then
        assertEquals(expectedItem, result);
        verify(requestSender).sendGetItemByIdRequest(user, itemId);
        verify(jsonObjectMapper).mapItemJsonToItem(jsonNode);
    }

    @Test
    void testGetItemByIdWithFieldgroups() {
        // Given
        EbayUser user = new EbayUser();
        String itemId = "123456";
        String fieldgroups = "full";
        JsonNode jsonNode = mock(JsonNode.class);
        EbayItem expectedItem = new EbayItem();

        // When
        when(requestSender.sendGetItemByIdRequest(user, itemId, fieldgroups)).thenReturn(jsonNode);
        when(jsonObjectMapper.mapItemJsonToItem(jsonNode)).thenReturn(expectedItem);
        EbayItem result = itemManager.getItemById(user, itemId, fieldgroups);

        // Then
        assertEquals(expectedItem, result);
        verify(requestSender).sendGetItemByIdRequest(user, itemId, fieldgroups);
        verify(jsonObjectMapper).mapItemJsonToItem(jsonNode);
    }
}
