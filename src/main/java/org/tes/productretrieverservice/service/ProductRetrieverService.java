package org.tes.productretrieverservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.tes.productretrieverservice.model.EbayItemEntity;

import java.util.*;

@Service
public class ProductRetrieverService {

    @Value("${ebayItemSummaryApiUrl}")
    private String ebayItemSummaryApiUrl;

    @Value("${ebayGetItemApiUrl}")
    private String ebayGetItemApiUrl;

    @Qualifier("productionEbayTokenRefresher")
    @Autowired
    private EbayTokenRefresher tokenRefresher;

    /**
     * The method retrieves an eBay item based on given keyword using eBay RESTful API.
     *
     * @param ebayRequestParams A map of expected values:
     *                         string "keyword", string "sort", list of strings "filters".
     *                          While "keyword" can't be null,
     *                          null values of "sort" and "filters" are acceptable.
     * @return An ArrayList of retrieved eBay items, written in EbayItemEntity objects.
     */
    @Cacheable(cacheNames = "EbayItemsCache")
    public ArrayList<EbayItemEntity> retrieveEbayItemsByKeyword(
            Map<String, Object> ebayRequestParams
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(tokenRefresher.getAccessToken());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(ebayItemSummaryApiUrl)
                .queryParam("q", ebayRequestParams.get("keyword"))
                .queryParam("limit", 10);

        if (ebayRequestParams.get("sort") != null) {
            uriBuilder.queryParam("sort", ebayRequestParams.get("sort"));
        }
        if (ebayRequestParams.get("filters") != null) {
            uriBuilder.queryParam("filter", ebayRequestParams.get("filters"));
        }

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<LinkedHashMap> response = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                request,
                LinkedHashMap.class
        );

        ArrayList<LinkedHashMap> itemSummariesArrayList =
                (ArrayList<LinkedHashMap>) response.getBody().get("itemSummaries");
        ArrayList<EbayItemEntity> retrievedEbayItems = new ArrayList<>();

        for (
                int itemsCounter = 0;
                itemsCounter < itemSummariesArrayList.size();
                itemsCounter = itemsCounter + 1
        ) {
            EbayItemEntity retrievedEbayItem = new EbayItemEntity();
            retrievedEbayItems.add(retrievedEbayItem.writeItemMap(itemSummariesArrayList.get(0)));
        }

        return retrievedEbayItems;
    }

    /**
     * The method decides which retriever method to call.
     *
     * @param itemID An ID of the product
     * @param productFieldgroupsEnabled Is request going to be
     *                                  made using "PRODUCT" value of "fieldgroups"
     *                                  parameter or not.
     *                                  Usually, false value will be used
     *                                  only while testing using eBay Sandbox environment.
     * @return An EbayItemEntity object with written data from a request
     */
    @Cacheable(cacheNames = "EbayItemCache")
    public EbayItemEntity retrieveEbayItemByItemId(String itemID, boolean productFieldgroupsEnabled) {
        if (productFieldgroupsEnabled) {
            return retrieveEbayItemByIdWithProductFieldgroups(itemID);
        } else {
            return retrieveEbayItemByIdWithCompactFieldgroups(itemID);
        }
    }

    private EbayItemEntity retrieveEbayItemByIdWithProductFieldgroups(String itemID) {
        String requestUrl = ebayGetItemApiUrl + "/" + itemID + "?fieldgroups=PRODUCT";

        return getEbayItemEntity(requestUrl);
    }

    private EbayItemEntity retrieveEbayItemByIdWithCompactFieldgroups(String itemID) {
        String requestUrl = ebayGetItemApiUrl + "/" + itemID + "?fieldgroups=COMPACT";

        return getEbayItemEntity(requestUrl);
    }

    private EbayItemEntity getEbayItemEntity(String requestUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(tokenRefresher.getAccessToken());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<LinkedHashMap> response = restTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                request,
                LinkedHashMap.class
        );

        Map<?, ?> itemMap = response.getBody();
        EbayItemEntity retrievedEbayItem = new EbayItemEntity();

        assert itemMap != null;
        return retrievedEbayItem.writeItemMap(itemMap);
    }
}
