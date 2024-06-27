package org.tes.productretrieverservice.item;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.tes.productretrieverservice.exception.ItemJsonReadingException;
import org.tes.productretrieverservice.model.EbayUser;

import java.util.List;

@Component
public class DefaultEbayItemRequestSender implements EbayItemRequestSender {
    private final EbayItemRequestBuilder requestBuilder;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Value("${ebayItemSummaryApiUrl}")
    private String itemSummaryApiUrl;

    @Value("${ebayGetItemApiUrl}")
    private String getItemApiUrl;

    @Autowired
    public DefaultEbayItemRequestSender(
            EbayItemRequestBuilder requestBuilder,
            ObjectMapper objectMapper
    ) {
        this.requestBuilder = requestBuilder;
        this.objectMapper = objectMapper;
        this.restTemplate = requestBuilder.getRestTemplate();
    }

    @Override
    public JsonNode sendGetItemRequest(
            EbayUser user,
            String keyword
    ) {
        try {
            return objectMapper.readTree(restTemplate.exchange(
                    requestBuilder.buildRequestUrl(
                            itemSummaryApiUrl,
                            keyword
                    ),
                    HttpMethod.GET,
                    requestBuilder.buildAuthOnlyHttpRequestEntity(user.getId()),
                    String.class
            ).getBody());
        } catch (JsonProcessingException exception) {
            throw new ItemJsonReadingException(
                    "An exception occurred while reading JSON received from the request"
                            + " to retrieve an eBay item by a keyword "
                            + keyword
            );
        }
    }

    @Override
    public JsonNode sendGetItemRequest(
            EbayUser user,
            String keyword,
            String sort
    ) {
        try {
            return objectMapper.readTree(restTemplate.exchange(
                    requestBuilder.buildRequestUrl(
                            itemSummaryApiUrl,
                            keyword,
                            sort
                    ),
                    HttpMethod.GET,
                    requestBuilder.buildAuthOnlyHttpRequestEntity(user.getId()),
                    String.class
            ).getBody());
        } catch (JsonProcessingException exception) {
            throw new ItemJsonReadingException(
                    "An exception occurred while reading JSON received from the request"
                            + " to retrieve an eBay item by a keyword "
                            + keyword
            );
        }
    }

    @Override
    public JsonNode sendGetItemRequest(
            EbayUser user,
            String keyword,
            List<String> filters
    ) {
        try {
            return objectMapper.readTree(restTemplate.exchange(
                    requestBuilder.buildRequestUrl(
                            itemSummaryApiUrl,
                            keyword,
                            filters
                    ),
                    HttpMethod.GET,
                    requestBuilder.buildAuthOnlyHttpRequestEntity(user.getId()),
                    String.class
            ).getBody());
        } catch (JsonProcessingException exception) {
            throw new ItemJsonReadingException(
                    "An exception occurred while reading JSON received from the request"
                            + " to retrieve an eBay item by a keyword "
                            + keyword
            );
        }
    }

    @Override
    public JsonNode sendGetItemRequest(
            EbayUser user,
            String keyword,
            String sort,
            List<String> filters
    ) {
        try {
            return objectMapper.readTree(restTemplate.exchange(
                    requestBuilder.buildRequestUrl(
                            itemSummaryApiUrl,
                            keyword,
                            sort,
                            filters
                    ),
                    HttpMethod.GET,
                    requestBuilder.buildAuthOnlyHttpRequestEntity(user.getId()),
                    String.class
            ).getBody());
        } catch (JsonProcessingException exception) {
            throw new ItemJsonReadingException(
                    "An exception occurred while reading JSON received from the request"
                            + " to retrieve an eBay item by a keyword "
                            + keyword
            );
        }
    }

    @Override
    public JsonNode sendGetItemByIdRequest(
            EbayUser user,
            String itemId
    ) {
        try {
            return objectMapper.readTree(restTemplate.exchange(
                    requestBuilder.buildItemIdRequestUrl(
                            getItemApiUrl,
                            itemId
                    ),
                    HttpMethod.GET,
                    requestBuilder.buildAuthOnlyHttpRequestEntity(user.getId()),
                    String.class
            ).getBody());
        } catch (JsonProcessingException exception) {
            throw new ItemJsonReadingException(
                    "An exception occurred while reading JSON received from the request"
                            + " to retrieve an eBay item with id "
                            + itemId
            );
        }
    }

    @Override
    public JsonNode sendGetItemByIdRequest(
            EbayUser user,
            String itemId,
            String fieldgroups
    ) {
        try {
            return objectMapper.readTree(restTemplate.exchange(
                    requestBuilder.buildItemIdRequestUrl(
                            getItemApiUrl,
                            itemId,
                            fieldgroups
                    ),
                    HttpMethod.GET,
                    requestBuilder.buildAuthOnlyHttpRequestEntity(user.getId()),
                    String.class
            ).getBody());
        } catch (JsonProcessingException exception) {
            throw new ItemJsonReadingException(
                    "An exception occurred while reading JSON received from the request"
                            + " to retrieve an eBay item with id "
                            + itemId
            );
        }
    }
}
