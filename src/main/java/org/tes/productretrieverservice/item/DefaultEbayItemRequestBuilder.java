package org.tes.productretrieverservice.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.tes.productretrieverservice.exception.RestTemplateResponseErrorHandler;
import org.tes.productretrieverservice.model.AuthCode;
import org.tes.productretrieverservice.model.EbayUser;
import org.tes.productretrieverservice.service.Oauth2UserService;

import java.util.List;

@Component
public class DefaultEbayItemRequestBuilder implements EbayItemRequestBuilder {
    private final Oauth2UserService<EbayUser, Long, AuthCode> userService;
    private final RestTemplateBuilder restTemplateBuilder;

    @Autowired
    public DefaultEbayItemRequestBuilder(
            Oauth2UserService<EbayUser, Long, AuthCode> userService,
            RestTemplateBuilder restTemplateBuilder
    ) {
        this.userService = userService;
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Override
    public HttpEntity buildHttpRequestEntity(
            String requestBody,
            Long userId
    ) {
        return buildAuthOnlyHttpRequestEntity(userId);
    }

    @Override
    public HttpEntity buildAuthOnlyHttpRequestEntity(Long userId) {
        return new HttpEntity(
                buildEmptyRequestBody(),
                buildHeaders(userService.generateAccessToken(userId).getAccessToken())
        );
    }

    @Override
    public String buildRequestUrl(
            String url,
            String keyword
    ) {
        return UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("q", keyword)
                .queryParam("limit", 10)
                .toUriString();
    }

    @Override
    public String buildRequestUrl(
            String url,
            String keyword,
            String sort
    ) {
        return UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("q", keyword)
                .queryParam("limit", 10)
                .queryParam("sort", sort)
                .toUriString();
    }

    @Override
    public String buildRequestUrl(
            String url,
            String keyword,
            List<String> filters
    ) {
        return UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("q", keyword)
                .queryParam("limit", 10)
                .queryParam("filter", filters)
                .toUriString();
    }

    @Override
    public String buildRequestUrl(
            String url,
            String keyword,
            String sort,
            List<String> filters
    ) {
        return UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("q", keyword)
                .queryParam("limit", 10)
                .queryParam("sort", sort)
                .queryParam("filter", filters)
                .toUriString();
    }

    @Override
    public String buildItemIdRequestUrl(
            String url,
            String itemId
    ) {
        return UriComponentsBuilder.fromHttpUrl(url + "/" + itemId).toUriString();
    }

    @Override
    public String buildItemIdRequestUrl(
            String url,
            String itemId,
            String fieldgroups
    ) {
        return UriComponentsBuilder.fromHttpUrl(url + "/" + itemId)
                .queryParam("fieldgroups", fieldgroups)
                .toUriString();
    }

    @Override
    public RestTemplate getRestTemplate() {
        return restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
    }

    /**
     * Builds the HTTP headers for the request with the given access token.
     *
     * @param accessToken the access token to include in the headers.
     * @return the constructed HTTP headers.
     */
    private HttpHeaders buildHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }

    private ObjectNode buildEmptyRequestBody() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.createObjectNode();
    }
}
