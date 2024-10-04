package org.tes.productretrieverservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.tes.productretrieverservice.model.*;

import java.util.*;

public class TestFactory {
    protected final ObjectMapper objectMapper = new ObjectMapper();

    public EbayItem buildEbayItem() {
        EbayItem ebayItem = new EbayItem();

        ebayItem.setItemId("v1|146036695558|0");
        ebayItem.setLegacyItemId("146036695558");
        ebayItem.setTitle("Lenovo ThinkPad Laptop Model T480, Core i7-8550U, 16GB RAM, 256GB SSD M.2, UHD");
        ebayItem.setCategories(Arrays.asList(
                "PC Laptops & Netbooks",
                "Computers/Tablets & Networking",
                "Laptops & Netbooks"
        ));
        ebayItem.setPrice(289.00);
        ebayItem.setPriceCurrency("USD");
        ebayItem.setBuyingOptions(Arrays.asList("FIXED_PRICE", "BEST_OFFER"));
        ebayItem.setUrl("https://www.ebay.com/itm/146036695558?_skw=thinkpad&hash=item2200772206:g:EMYAAOSwEtlm61fs&amdata=enc%3AAQAJAAAA4H%2FqIbIc6o8B4%2BvpFzQyE8XErbDvfrCv2Gn45aMBzV4hNNPV5CajY8BAapxCuDZl8tR2rdScEcKq2fMYqxrGuj7ehTk14iAOE%2FClaWZUy793MaMPfFzYkMrp844pn00cYy5ih8NEI%2FT2MVfSralmEc40VRvXufLJc7veH7ONK8G1O5b%2B6GB7W7C01jiX6nsjHvH5nTZJHc7tBPqHZLxEjBVt13mYS96TEdnNMTX0f6EtBIFL%2F0bpbyvtP%2BI1WxfqGt%2Bf31H6t1nIeDibSTSWgFkEvw1bbrinmTNN91LgW92K");
        ebayItem.setCondition("Used");
        ebayItem.setShippingCost(0.00);
        ebayItem.setShippingCostCurrency("USD");
        ebayItem.setShippingCostType("FIXED");
        ebayItem.setItemLocationCountry("US");
        ebayItem.setPrimaryImgUrl("https://i.ebayimg.com/images/g/EMYAAOSwEtlm61fs/s-l225.jpg");
        ebayItem.setAdditionalImgUrls(Arrays.asList(
                "https://i.ebayimg.com/images/g/Ot0AAOSwjOtm61fu/s-l225.jpg",
                "https://i.ebayimg.com/images/g/nL4AAOSwQnpm61fs/s-l225.jpg"
        ));

        // Fields not directly available in the JSON but present in EbayItem class
        ebayItem.setShortDescription(null);
        ebayItem.setDescription(null);
        ebayItem.setCategory(null);
        ebayItem.setAspects(Collections.emptyList());
        ebayItem.setShippingType(null);
        ebayItem.setShippingRegions(Collections.emptyList());
        ebayItem.setItemLocationCity(null);

        return ebayItem;
    }

    public AuthCode buildValidAuthCode() {
        AuthCode authCode = new AuthCode(
                "test-auth-code",
                3600
        );
        authCode.setCreationDate(new Date());

        return authCode;
    }

    public AccessToken buildValidAccessToken() {
        AccessToken accessToken = new AccessToken(
                "test-access-token",
                3600
        );
        accessToken.setCreationDate(new Date());

        return accessToken;
    }

    public RefreshToken buildValidRefreshToken() {
        RefreshToken refreshToken = new RefreshToken(
                "test-refresh-token",
                3600
        );
        refreshToken.setCreationDate(new Date());

        return refreshToken;
    }

    public EbayUser buildEbayUser() {
        EbayUser user = new EbayUser(
                "test-username",
                "test-client-id",
                "test-client-secret"
        );
        user.setRedirectUrl("test-redirect-url");
        user.setAuthCodes(new ArrayList<>());
        user.setRefreshTokens(new ArrayList<>());

        return user;
    }

    public HttpHeaders buildBasicAuthHttpHeaders(
            String clientId,
            String clientSecret
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(
                Base64.getEncoder()
                        .encodeToString((clientId + ":" + clientSecret).getBytes()));

        return headers;
    }

    public HttpHeaders buildAccessTokenHttpHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }

    public String getEbayTokensJsonString() {
        return "{\n" +
                "  \"access_token\": \"test-access-token\",\n" +
                "  \"expires_in\": 3600,\n" +
                "  \"refresh_token\": \"test-refresh-token\",\n" +
                "  \"refresh_token_expires_in\": 3600,\n" +
                "  \"token_type\": \"User Access Token\"\n" +
                "}";
    }

    public JsonNode getEbayTokensJsonNode() throws JsonProcessingException {
        return objectMapper.readTree(
                "{\n" +
                        "  \"access_token\": \"test-access-token\",\n" +
                        "  \"expires_in\": 3600,\n" +
                        "  \"refresh_token\": \"test-refresh-token\",\n" +
                        "  \"refresh_token_expires_in\": 3600,\n" +
                        "  \"token_type\": \"User Access Token\"\n" +
                        "}"
        );
    }

    public Map<String, String> buildHashMapAuthModelRequestBody(
            EbayUser user,
            AuthCode authCode
    ) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("grant_type", "authorization_code");
        requestBody.put("code", authCode.getAuthCode());
        requestBody.put("redirect_uri", user.getRedirectUrl());

        return requestBody;
    }

    public String buildStringAuthModelRequestBody(
            EbayUser user,
            AuthCode authCode
    ) throws JsonProcessingException {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("grant_type", "authorization_code");
        requestBody.put("code", authCode.getAuthCode());
        requestBody.put("redirect_uri", user.getRedirectUrl());

        return objectMapper.writeValueAsString(requestBody);
    }

    public String buildStringRefreshTokenRequestBody(RefreshToken refreshToken)
            throws JsonProcessingException {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("grant_type", "refresh_token");
        requestBody.put("refresh_token", refreshToken.getToken());

        return objectMapper.writeValueAsString(requestBody);
    }

    public Map<String, String> buildHashMapRefreshTokenRequestBody(RefreshToken refreshToken) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("grant_type", "refresh_token");
        requestBody.put("refresh_token", refreshToken.getToken());

        return requestBody;
    }
}
