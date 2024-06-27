package org.tes.productretrieverservice.unit.item;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.tes.productretrieverservice.item.DefaultEbayItemRequestSender;
import org.tes.productretrieverservice.item.EbayItemRequestBuilder;
import org.tes.productretrieverservice.model.EbayUser;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class DefaultEbayItemRequestSenderTest {

    @Mock
    private EbayItemRequestBuilder requestBuilder;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private RestTemplate restTemplate;

    private DefaultEbayItemRequestSender defaultEbayItemRequestSender;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        defaultEbayItemRequestSender = new DefaultEbayItemRequestSender(requestBuilder, objectMapper);
        ReflectionTestUtils.setField(defaultEbayItemRequestSender, "itemSummaryApiUrl", "http://example.com/itemsummary");
        ReflectionTestUtils.setField(defaultEbayItemRequestSender, "getItemApiUrl", "http://example.com/getitem");
        ReflectionTestUtils.setField(defaultEbayItemRequestSender, "restTemplate", restTemplate);
    }

    @Test
    void testSendGetItemRequest() throws Exception {
        // Given
        EbayUser user = new EbayUser();
        user.setId(123L);
        String keyword = "test";

        String url = "http://example.com/itemsummary?q=test";
        HttpEntity<String> httpEntity = new HttpEntity<>("headers");
        ResponseEntity<String> responseEntity = ResponseEntity.ok("response");
        JsonNode jsonNode = objectMapper.createObjectNode();

        // When
        when(requestBuilder.buildRequestUrl(anyString(), eq(keyword))).thenReturn(url);
        when(requestBuilder.buildAuthOnlyHttpRequestEntity(user.getId())).thenReturn(httpEntity);
        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), eq(httpEntity), eq(String.class))).thenReturn(responseEntity);
        when(objectMapper.readTree(responseEntity.getBody())).thenReturn(jsonNode);

        JsonNode result = defaultEbayItemRequestSender.sendGetItemRequest(user, keyword);

        // Then
        assertEquals(jsonNode, result);
    }

    @Test
    void testSendGetItemRequestWithSort() throws Exception {
        // Given
        EbayUser user = new EbayUser();
        user.setId(123L);
        String keyword = "test";
        String sort = "price";

        String url = "http://example.com/itemsummary?q=test&sort=price";
        HttpEntity<String> httpEntity = new HttpEntity<>("headers");
        ResponseEntity<String> responseEntity = ResponseEntity.ok("response");
        JsonNode jsonNode = objectMapper.createObjectNode();

        // When
        when(requestBuilder.buildRequestUrl(anyString(), eq(keyword), eq(sort))).thenReturn(url);
        when(requestBuilder.buildAuthOnlyHttpRequestEntity(user.getId())).thenReturn(httpEntity);
        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), eq(httpEntity), eq(String.class))).thenReturn(responseEntity);
        when(objectMapper.readTree(responseEntity.getBody())).thenReturn(jsonNode);

        JsonNode result = defaultEbayItemRequestSender.sendGetItemRequest(user, keyword, sort);

        // Then
        assertEquals(jsonNode, result);
    }

    @Test
    void testSendGetItemRequestWithFilters() throws Exception {
        // Given
        EbayUser user = new EbayUser();
        user.setId(123L);
        String keyword = "test";
        List<String> filters = Arrays.asList("condition", "price");

        String url = "http://example.com/itemsummary?q=test&filter=condition,price";
        HttpEntity<String> httpEntity = new HttpEntity<>("headers");
        ResponseEntity<String> responseEntity = ResponseEntity.ok("response");
        JsonNode jsonNode = objectMapper.createObjectNode();

        // When
        when(requestBuilder.buildRequestUrl(anyString(), eq(keyword), eq(filters))).thenReturn(url);
        when(requestBuilder.buildAuthOnlyHttpRequestEntity(user.getId())).thenReturn(httpEntity);
        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), eq(httpEntity), eq(String.class))).thenReturn(responseEntity);
        when(objectMapper.readTree(responseEntity.getBody())).thenReturn(jsonNode);

        JsonNode result = defaultEbayItemRequestSender.sendGetItemRequest(user, keyword, filters);

        // Then
        assertEquals(jsonNode, result);
    }

    @Test
    void testSendGetItemByIdRequest() throws Exception {
        // Given
        EbayUser user = new EbayUser();
        user.setId(123L);
        String itemId = "item123";

        String url = "http://example.com/getitem/item123";
        HttpEntity<String> httpEntity = new HttpEntity<>("headers");
        ResponseEntity<String> responseEntity = ResponseEntity.ok("response");
        JsonNode jsonNode = objectMapper.createObjectNode();

        // When
        when(requestBuilder.buildItemIdRequestUrl(anyString(), eq(itemId))).thenReturn(url);
        when(requestBuilder.buildAuthOnlyHttpRequestEntity(user.getId())).thenReturn(httpEntity);
        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), eq(httpEntity), eq(String.class))).thenReturn(responseEntity);
        when(objectMapper.readTree(responseEntity.getBody())).thenReturn(jsonNode);

        JsonNode result = defaultEbayItemRequestSender.sendGetItemByIdRequest(user, itemId);

        // Then
        assertEquals(jsonNode, result);
    }
}
