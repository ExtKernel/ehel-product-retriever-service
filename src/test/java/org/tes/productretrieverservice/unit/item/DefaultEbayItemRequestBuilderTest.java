package org.tes.productretrieverservice.unit.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.tes.productretrieverservice.item.DefaultEbayItemRequestBuilder;
import org.tes.productretrieverservice.model.AccessToken;
import org.tes.productretrieverservice.model.EbayUser;
import org.tes.productretrieverservice.service.Oauth2UserService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class DefaultEbayItemRequestBuilderTest {

    @Mock
    private Oauth2UserService<EbayUser, Long> userService;

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    @Mock
    private RestTemplate restTemplate;

    private DefaultEbayItemRequestBuilder requestBuilder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        requestBuilder = new DefaultEbayItemRequestBuilder(userService);
    }

    @Test
    void testBuildHttpRequestEntity() {
        // Given
        Long userId = 1L;
        AccessToken accessToken = new AccessToken();
        accessToken.setToken("test-token");
        when(userService.generateAccessToken(userId)).thenReturn(accessToken);

        // When
        HttpEntity<?> entity = requestBuilder.buildHttpRequestEntity("", userId);

        // Then
        assertNotNull(entity);
        HttpHeaders headers = entity.getHeaders();
        assertEquals(MediaType.APPLICATION_JSON, headers.getContentType());
        assertEquals("Bearer test-token", headers.getFirst(HttpHeaders.AUTHORIZATION));
    }

    @Test
    void testBuildAuthOnlyHttpRequestEntity() {
        // Given
        Long userId = 1L;
        AccessToken accessToken = new AccessToken();
        accessToken.setToken("test-token");
        when(userService.generateAccessToken(userId)).thenReturn(accessToken);

        // When
        HttpEntity<?> entity = requestBuilder.buildAuthOnlyHttpRequestEntity(userId);

        // Then
        assertNotNull(entity);
        HttpHeaders headers = entity.getHeaders();
        assertEquals(MediaType.APPLICATION_JSON, headers.getContentType());
        assertEquals("Bearer test-token", headers.getFirst(HttpHeaders.AUTHORIZATION));
    }

    @Test
    void testBuildRequestUrl() {
        // Given
        String url = "http://api.example.com";
        String keyword = "test-item";

        // When
        String result = requestBuilder.buildRequestUrl(url, keyword);

        // Then
        assertEquals("http://api.example.com?q=test-item&limit=10", result);
    }

    @Test
    void testBuildRequestUrlWithSort() {
        // Given
        String url = "http://api.example.com";
        String keyword = "test-item";
        String sort = "price";

        // When
        String result = requestBuilder.buildRequestUrl(url, keyword, sort);

        // Then
        assertEquals("http://api.example.com?q=test-item&limit=10&sort=price", result);
    }

    @Test
    void testBuildRequestUrlWithFilters() {
        // Given
        String url = "http://api.example.com";
        String keyword = "test-item";
        List<String> filters = Arrays.asList("condition", "price");

        // When
        String result = requestBuilder.buildRequestUrl(url, keyword, filters);

        // Then
        assertEquals("http://api.example.com?q=test-item&limit=10&filter=condition&filter=price", result);
    }

    @Test
    void testBuildRequestUrlWithSortAndFilters() {
        // Given
        String url = "http://api.example.com";
        String keyword = "test-item";
        String sort = "price";
        List<String> filters = Arrays.asList("condition", "brand");

        // When
        String result = requestBuilder.buildRequestUrl(url, keyword, sort, filters);

        // Then
        assertEquals("http://api.example.com?q=test-item&limit=10&sort=price&filter=condition&filter=brand", result);
    }

    @Test
    void testBuildItemIdRequestUrl() {
        // Given
        String url = "http://api.example.com/items";
        String itemId = "12345";

        // When
        String result = requestBuilder.buildItemIdRequestUrl(url, itemId);

        // Then
        assertEquals("http://api.example.com/items/12345", result);
    }

    @Test
    void testBuildItemIdRequestUrlWithFieldgroups() {
        // Given
        String url = "http://api.example.com/items";
        String itemId = "12345";
        String fieldgroups = "full";

        // When
        String result = requestBuilder.buildItemIdRequestUrl(url, itemId, fieldgroups);

        // Then
        assertEquals("http://api.example.com/items/12345?fieldgroups=full", result);
    }

    @Test
    void testGetRestTemplate() {
        // When
        RestTemplate result = requestBuilder.getRestTemplate();

        // Then
        assertNotNull(result);
    }
}
