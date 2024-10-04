package org.tes.productretrieverservice.integration;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.tes.productretrieverservice.model.*;

import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class EbayAbstractIntegrationTest extends AbstractIntegrationTest {

    public String buildSaveAuthCodeUri(AuthCode authCode) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString("/secured/auth-code");
        return uriComponentsBuilder
                .queryParam("code", authCode.getAuthCode())
                .queryParam("expires_in", authCode.getExpiresIn())
                .build().toUriString();
    }

    public EbayUser performEbayUserSaveRequest(EbayUser user)
            throws Exception {
        return performPostRequest(
                "/secured/user",
                user,
                EbayUser.class,
                status().is2xxSuccessful()
        );
    }

    public void performGetItemsExpectSuccess(
            EbayUser user,
            RestTemplate restTemplate,
            String ebayRequestUri,
            String testUri,
            String mockEbayJsonString
    ) throws Exception {
        user.setId(1L);
        // save the user
        performEbayUserSaveRequest(user);

        // save a valid refresh token
        RefreshToken refreshToken = buildValidRefreshToken();
        performPostRequest(
                "/secured/user/refresh-token/save/1",
                refreshToken,
                RefreshToken.class,
                status().is2xxSuccessful()
        );

        AccessToken accessToken = buildValidAccessToken();
        String mockAccessTokenResponse = "{\"access_token\":\""
                + accessToken.getToken()
                + "\",\"expires_in\":"
                + accessToken.getExpiresIn()
                + "}";
        when(restTemplate.exchange(
                "https://api.ebay.com/identity/v1/oauth2/token",
                HttpMethod.POST,
                new HttpEntity<>(
                        buildStringRefreshTokenRequestBody(refreshToken),
                        buildBasicAuthHttpHeaders(
                                user.getClientId(),
                                user.getClientSecret()
                        )
                ),
                String.class
        )).thenReturn(new ResponseEntity<>(mockAccessTokenResponse, HttpStatus.OK));

        when(restTemplate.exchange(
                ebayRequestUri,
                HttpMethod.GET,
                new HttpEntity<>(
                        null,
                        buildAccessTokenHttpHeaders(accessToken.getToken())
                ),
                String.class
        )).thenReturn(new ResponseEntity<>(mockEbayJsonString, HttpStatus.OK));

        List<LinkedHashMap> result = performGetRequestExpectedSuccess(testUri, List.class);
        List<EbayItem> mappedResult = result.stream()
                .map(ebayItemLinkedHashMap -> objectMapper.convertValue(ebayItemLinkedHashMap, EbayItem.class))
                .toList();

        EbayItem ebayItem = buildEbayItem();

        assertTrue(ebayItem.equals(mappedResult.get(0)));
    }

    public String getEbayItemJsonString(EbayItem ebayItem) {
        return  "{\n" +
                "  \"href\": \"https://api.ebay.com/buy/browse/v1/item_summary/search?q=thinkpad&limit=50&offset=0\",\n" +
                "  \"total\": 254690,\n" +
                "  \"next\": \"https://api.ebay.com/buy/browse/v1/item_summary/search?q=thinkpad&limit=50&offset=50\",\n" +
                "  \"limit\": 50,\n" +
                "  \"offset\": 0,\n" +
                "  \"itemSummaries\": [\n" +
                "    {\n" +
                "      \"itemId\": \"" + ebayItem.getItemId() + "\",\n" +
                "      \"title\": \"" + ebayItem.getTitle() + "\",\n" +
                "      \"leafCategoryIds\": [\"177\"],\n" +
                "      \"categories\": [\n" +
                "        {\"categoryId\": \"177\", \"categoryName\": \"PC Laptops & Netbooks\"},\n" +
                "        {\"categoryId\": \"58058\", \"categoryName\": \"Computers/Tablets & Networking\"},\n" +
                "        {\"categoryId\": \"175672\", \"categoryName\": \"Laptops & Netbooks\"}\n" +
                "      ],\n" +
                "      \"image\": {\"imageUrl\": \"https://i.ebayimg.com/images/g/EMYAAOSwEtlm61fs/s-l225.jpg\"},\n" +
                "      \"price\": {\"value\": \"289.00\", \"currency\": \"USD\"},\n" +
                "      \"itemHref\": \"https://api.ebay.com/buy/browse/v1/item/v1%7C146036695558%7C0\",\n" +
                "      \"seller\": {\"username\": \"tech_press_brand01\", \"feedbackPercentage\": \"100.0\", \"feedbackScore\": 6},\n" +
                "      \"condition\": \"Used\",\n" +
                "      \"conditionId\": \"3000\",\n" +
                "      \"thumbnailImages\": [{\"imageUrl\": \"https://i.ebayimg.com/images/g/EMYAAOSwEtlm61fs/s-l1600.jpg\"}],\n" +
                "      \"shippingOptions\": [\n" +
                "        {\"shippingCostType\": \"FIXED\", \"shippingCost\": {\"value\": \"0.00\", \"currency\": \"USD\"}}\n" +
                "      ],\n" +
                "      \"buyingOptions\": [\"FIXED_PRICE\", \"BEST_OFFER\"],\n" +
                "      \"itemWebUrl\": \"https://www.ebay.com/itm/146036695558?_skw=thinkpad&hash=item2200772206:g:EMYAAOSwEtlm61fs&amdata=enc%3AAQAJAAAA4H%2FqIbIc6o8B4%2BvpFzQyE8XErbDvfrCv2Gn45aMBzV4hNNPV5CajY8BAapxCuDZl8tR2rdScEcKq2fMYqxrGuj7ehTk14iAOE%2FClaWZUy793MaMPfFzYkMrp844pn00cYy5ih8NEI%2FT2MVfSralmEc40VRvXufLJc7veH7ONK8G1O5b%2B6GB7W7C01jiX6nsjHvH5nTZJHc7tBPqHZLxEjBVt13mYS96TEdnNMTX0f6EtBIFL%2F0bpbyvtP%2BI1WxfqGt%2Bf31H6t1nIeDibSTSWgFkEvw1bbrinmTNN91LgW92K\",\n" +
                "      \"itemLocation\": {\"postalCode\": \"018**\", \"country\": \"US\"},\n" +
                "      \"additionalImages\": [\n" +
                "        {\"imageUrl\": \"https://i.ebayimg.com/images/g/Ot0AAOSwjOtm61fu/s-l225.jpg\"},\n" +
                "        {\"imageUrl\": \"https://i.ebayimg.com/images/g/nL4AAOSwQnpm61fs/s-l225.jpg\"}\n" +
                "      ],\n" +
                "      \"adultOnly\": false,\n" +
                "      \"legacyItemId\": \"146036695558\",\n" +
                "      \"availableCoupons\": false,\n" +
                "      \"itemCreationDate\": \"2024-09-18T22:47:57.000Z\",\n" +
                "      \"topRatedBuyingExperience\": false,\n" +
                "      \"priorityListing\": true,\n" +
                "      \"listingMarketplaceId\": \"EBAY_US\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

    public String getProductFieldgroupsEbayItemJsonString(EbayItem ebayItem) {
        return  "{\n" +
                "  \"itemId\": \"v1|146036695558|0\",\n" +
                "  \"title\": \"Lenovo ThinkPad Laptop Model T480, Core i7-8550U, 16GB RAM, 256GB SSD M.2, UHD\",\n" +
                "  \"shortDescription\": \"10/100/1000 Gigabit Ethernet, IEEE 802.11ac and Bluetooth 4.1. The laptop is used but always via docking station which looks in very good condition physically. All parts are original and nothing has been changed.\",\n" +
                "  \"price\": {\n" +
                "    \"value\": \"289.00\",\n" +
                "    \"currency\": \"USD\"\n" +
                "  },\n" +
                "  \"condition\": \"Used\",\n" +
                "  \"itemLocation\": {\n" +
                "    \"city\": \"Methuen\",\n" +
                "    \"stateOrProvince\": \"Massachusetts\",\n" +
                "    \"postalCode\": \"018**\",\n" +
                "    \"country\": \"US\"\n" +
                "  },\n" +
                "  \"brand\": \"Lenovo\",\n" +
                "  \"seller\": {\n" +
                "    \"username\": \"tech_press_brand01\",\n" +
                "    \"feedbackPercentage\": \"100.0\",\n" +
                "    \"feedbackScore\": 6\n" +
                "  },\n" +
                "  \"shippingOptions\": [\n" +
                "    {\n" +
                "      \"shippingServiceCode\": \"USPS Priority Mail\",\n" +
                "      \"shippingCost\": {\n" +
                "        \"value\": \"0.00\",\n" +
                "        \"currency\": \"USD\"\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"localizedAspects\": [\n" +
                "    {\n" +
                "      \"name\": \"Brand\",\n" +
                "      \"value\": \"Lenovo\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"Model\",\n" +
                "      \"value\": \"Lenovo T480\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"RAM Size\",\n" +
                "      \"value\": \"16 GB\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"SSD Capacity\",\n" +
                "      \"value\": \"256 GB\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"Processor\",\n" +
                "      \"value\": \"Intel Core i7-8550U\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"Operating System\",\n" +
                "      \"value\": \"Windows 10 Pro\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

    public String getCompactFieldgroupsEbayItemJsonString(EbayItem ebayItem) {
        return  "{\n" +
                "    \"itemId\": \"v1|146036695558|0\",\n" +
                "    \"sellerItemRevision\": \"1\",\n" +
                "    \"price\": {\n" +
                "        \"value\": \"289.00\",\n" +
                "        \"currency\": \"USD\"\n" +
                "    },\n" +
                "    \"itemCreationDate\": \"2024-09-18T22:47:57.000Z\",\n" +
                "    \"gtin\": \"Does not apply\",\n" +
                "    \"estimatedAvailabilities\": [\n" +
                "        {\n" +
                "            \"availabilityThresholdType\": \"MORE_THAN\",\n" +
                "            \"availabilityThreshold\": 10,\n" +
                "            \"estimatedAvailabilityStatus\": \"IN_STOCK\",\n" +
                "            \"estimatedSoldQuantity\": 16,\n" +
                "            \"estimatedRemainingQuantity\": 34\n" +
                "        }\n" +
                "    ],\n" +
                "    \"shippingOptions\": [\n" +
                "        {\n" +
                "            \"shippingServiceCode\": \"USPS Priority Mail\",\n" +
                "            \"trademarkSymbol\": \"®\",\n" +
                "            \"shippingCarrierCode\": \"USPS\",\n" +
                "            \"type\": \"Expedited Shipping\",\n" +
                "            \"shippingCost\": {\n" +
                "                \"value\": \"0.00\",\n" +
                "                \"currency\": \"USD\"\n" +
                "            },\n" +
                "            \"quantityUsedForEstimate\": 1,\n" +
                "            \"minEstimatedDeliveryDate\": \"2024-10-03T10:00:00.000Z\",\n" +
                "            \"maxEstimatedDeliveryDate\": \"2024-10-05T10:00:00.000Z\",\n" +
                "            \"shippingCostType\": \"CALCULATED\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"taxes\": [\n" +
                "        {\n" +
                "            \"taxJurisdiction\": {\n" +
                "                \"region\": {\n" +
                "                    \"regionName\": \"Alabama\",\n" +
                "                    \"regionType\": \"STATE_OR_PROVINCE\"\n" +
                "                },\n" +
                "                \"taxJurisdictionId\": \"AL\"\n" +
                "            },\n" +
                "            \"taxType\": \"STATE_SALES_TAX\",\n" +
                "            \"shippingAndHandlingTaxed\": true,\n" +
                "            \"includedInPrice\": false,\n" +
                "            \"ebayCollectAndRemitTax\": true\n" +
                "        },\n" +
                "        {\n" +
                "            \"taxJurisdiction\": {\n" +
                "                \"region\": {\n" +
                "                    \"regionName\": \"Alaska\",\n" +
                "                    \"regionType\": \"STATE_OR_PROVINCE\"\n" +
                "                },\n" +
                "                \"taxJurisdictionId\": \"AK\"\n" +
                "            },\n" +
                "            \"taxType\": \"STATE_SALES_TAX\",\n" +
                "            \"shippingAndHandlingTaxed\": true,\n" +
                "            \"includedInPrice\": false,\n" +
                "            \"ebayCollectAndRemitTax\": true\n" +
                "        }\n" +
                "    ]\n" +
                "}";
    }
}
