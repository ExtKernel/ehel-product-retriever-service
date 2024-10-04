package org.tes.productretrieverservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.tes.productretrieverservice.model.AuthCode;
import org.tes.productretrieverservice.model.EbayUser;
import org.tes.productretrieverservice.model.RefreshToken;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Profile("test")
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class EbayUserControllerIT extends EbayAbstractIntegrationTest {

    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void givenUser_whenSave_thenReturnSavedUser()
            throws Exception {
        EbayUser user = buildEbayUser();
        user.setId(1L);

        // test 200 OK and get the result
        EbayUser result = performPostRequestExpectedSuccess(
                "/secured/user",
                user,
                EbayUser.class
        );
        // check the result itself
        assertEquals(user, result);
    }

    @Test
    public void givenUserId_whenGenerateRefreshToken_thenReturnRefreshToken()
            throws Exception {
        EbayUser user = buildEbayUser();
        user.setId(1L);
        // save the user
        performEbayUserSaveRequest(user);

        AuthCode authCode = buildValidAuthCode();
        // save the auth code
        // it is required to generate a refresh token
        performGetRequest(
                buildSaveAuthCodeUri(authCode),
                AuthCode.class,
                status().is2xxSuccessful()
        );

        RefreshToken refreshToken = buildValidRefreshToken();
        refreshToken.setCreationDate(null);
        String mockRefreshTokenResponse = "{\"refresh_token\":\""
                + refreshToken.getToken()
                + "\",\"refresh_token_expires_in\":"
                + refreshToken.getExpiresIn()
                + "}";
        when(restTemplate.exchange(
                "https://api.ebay.com/identity/v1/oauth2/token",
                HttpMethod.POST,
                new HttpEntity<>(
                        buildStringAuthModelRequestBody(
                                user,
                                authCode
                        ),
                        buildBasicAuthHttpHeaders(
                                user.getClientId(),
                                user.getClientSecret()
                        )
                ),
                String.class
        )).thenReturn(new ResponseEntity<>(mockRefreshTokenResponse, HttpStatus.OK));

        // test 200 OK and get the result
        RefreshToken result = performPostRequestExpectedSuccess(
                "/secured/user/refresh-token/generate/1",
                null,
                RefreshToken.class
        );
        // check the result itself
        assertEquals(refreshToken, result);
    }

    @Test
    public void givenRefreshToken_whenSaveRefreshToken_thenReturnRefreshToken()
            throws Exception {
        EbayUser user = buildEbayUser();
        user.setId(1L);
        // save the user
        performEbayUserSaveRequest(user);

        RefreshToken refreshToken = buildValidRefreshToken();
        refreshToken.setId(1L);
        // test 200 OK and get the result
        RefreshToken result = performPostRequestExpectedSuccess(
                "/secured/user/refresh-token/save/1",
                refreshToken,
                RefreshToken.class
        );
        // check the result itself
        assertEquals(refreshToken, result);
    }

    @Test
    public void givenUserId_whenFindById_thenReturnUser()
            throws Exception {
        EbayUser user = buildEbayUser();
        user.setId(1L);
        // save the user
        performEbayUserSaveRequest(user);

        // test 200 OK and get the result
        EbayUser result = performGetRequestExpectedSuccess(
                "/secured/user/1",
                EbayUser.class
        );
        // check the result itself
        assertEquals(user, result);
    }

    @Test
    public void givenUserIdAndKeyword_whenGetItems_thenReturnItemsByKeyword()
            throws Exception {
        EbayUser user = buildEbayUser();
        user.setId(1L);

        String keyword = "thinkpad";

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString("https://api.ebay.com/buy/browse/v1/item_summary/search");
        String ebayRequestUri = uriComponentsBuilder
                .queryParam("q", keyword)
                .queryParam("limit", 10)
                .build().toUriString();

        uriComponentsBuilder = UriComponentsBuilder.fromUriString("/secured/user/1/item");
        String uri = uriComponentsBuilder
                .queryParam("keyword", keyword)
                .build().toUriString();
        String mockEbayJsonString = getEbayItemJsonString(buildEbayItem());

        performGetItemsExpectSuccess(
                user,
                restTemplate,
                ebayRequestUri,
                uri,
                mockEbayJsonString
        );
    }

    @Test
    public void givenUserIdAndKeywordAndSort_whenGetItems_thenReturnItemsByKeyword()
            throws Exception {
        EbayUser user = buildEbayUser();
        user.setId(1L);

        String keyword = "thinkpad";
        String sort = "price";

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString("https://api.ebay.com/buy/browse/v1/item_summary/search");
        String ebayRequestUri = uriComponentsBuilder
                .queryParam("q", keyword)
                .queryParam("limit", 10)
                .queryParam("sort", sort)
                .build().toUriString();

        uriComponentsBuilder = UriComponentsBuilder.fromUriString("/secured/user/1/item");
        String uri = uriComponentsBuilder
                .queryParam("keyword", keyword)
                .queryParam("sort", sort)
                .build().toUriString();
        String mockEbayJsonString = getEbayItemJsonString(buildEbayItem());

        performGetItemsExpectSuccess(
                user,
                restTemplate,
                ebayRequestUri,
                uri,
                mockEbayJsonString
        );
    }

    @Test
    public void givenUserIdAndKeywordAndSortAndFilters_whenGetItems_thenReturnItemsByKeyword()
            throws Exception {
        EbayUser user = buildEbayUser();
        user.setId(1L);

        String keyword = "thinkpad";
        String sort = "price";
        List<String> filters = new ArrayList<>();
        filters.add(keyword);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString("https://api.ebay.com/buy/browse/v1/item_summary/search");
        String ebayRequestUri = uriComponentsBuilder
                .queryParam("q", keyword)
                .queryParam("limit", 10)
                .queryParam("sort", sort)
                .queryParam("filter", filters)
                .build().toUriString();

        uriComponentsBuilder = UriComponentsBuilder.fromUriString("/secured/user/1/item");
        String uri = uriComponentsBuilder
                .queryParam("keyword", keyword)
                .queryParam("sort", sort)
                .queryParam("filters", filters)
                .build().toUriString();
        String mockEbayJsonString = getEbayItemJsonString(buildEbayItem());

        performGetItemsExpectSuccess(
                user,
                restTemplate,
                ebayRequestUri,
                uri,
                mockEbayJsonString
        );
    }

    @Test
    public void givenUserIdAndKeywordAndFilters_whenGetItems_thenReturnItemsByKeyword()
            throws Exception {
        EbayUser user = buildEbayUser();
        user.setId(1L);

        String keyword = "thinkpad";
        List<String> filters = new ArrayList<>();
        filters.add(keyword);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString("https://api.ebay.com/buy/browse/v1/item_summary/search");
        String ebayRequestUri = uriComponentsBuilder
                .queryParam("q", keyword)
                .queryParam("limit", 10)
                .queryParam("filter", filters)
                .build().toUriString();

        uriComponentsBuilder = UriComponentsBuilder.fromUriString("/secured/user/1/item");
        String uri = uriComponentsBuilder
                .queryParam("keyword", keyword)
                .queryParam("filters", filters)
                .build().toUriString();
        String mockEbayJsonString = getEbayItemJsonString(buildEbayItem());

        performGetItemsExpectSuccess(
                user,
                restTemplate,
                ebayRequestUri,
                uri,
                mockEbayJsonString
        );
    }

    @Test
    public void givenUpdatedUser_whenUpdate_thenReturnUpdatedUser()
            throws Exception {
        EbayUser user = buildEbayUser();
        user.setId(1L);

        performEbayUserSaveRequest(user);

        // update the user
        user.setClientId("updated-client-id");

        // test 200 OK and get the result
        EbayUser result = performPutRequestExpectedSuccess(
                "/secured/user",
                user,
                EbayUser.class
        );
        // check the result itself
        assertEquals(user, result);
    }

    @Test
    public void givenUserId_whenDeleteById_thenReturnNothing()
            throws Exception {
        EbayUser user = buildEbayUser();
        user.setId(1L);

        performEbayUserSaveRequest(user);

        assertDoesNotThrow(() -> performDeleteRequestExpectedSuccess("/secured/user/" + user.getId()));
    }

    // getItemById is not tested yet
}
