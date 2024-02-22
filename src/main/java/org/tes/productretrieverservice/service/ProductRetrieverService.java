package org.tes.productretrieverservice.service;

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

    @Value("${ebayBrowseApiURL}")
    private String ebayApiUrl;

    @Value("${ebayApiToken}")
    private String ebayToken;

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
    public ArrayList<Optional<EbayItemEntity>> retrieveEbayItemsByKeyword(
            Map<String, Object> ebayRequestParams
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(ebayToken);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(ebayApiUrl)
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
        ArrayList<Optional<EbayItemEntity>> retrievedEbayItems = new ArrayList<>();

        for (
                int itemsCounter = 0;
                itemsCounter < itemSummariesArrayList.size();
                itemsCounter = itemsCounter + 1
        ) {
            LinkedHashMap<?, ?> itemMap = itemSummariesArrayList.get(0);

            Optional<EbayItemEntity> retrievedEbayItem = Optional.of(new EbayItemEntity());
            retrievedEbayItem.get()
                    .setItemId((String) itemMap.get("itemId"));
            retrievedEbayItem.get()
                    .setTitle((String) itemMap.get("title"));

            ArrayList<LinkedHashMap<?, ?>> itemCategoriesMapsArrayList = (ArrayList<LinkedHashMap<?, ?>>) itemMap.get("categories");
            ArrayList<String> itemCategoriesNamesArrayList = new ArrayList<>();

            for (
                    int categoriesMapsCounter = 0;
                    categoriesMapsCounter < itemCategoriesMapsArrayList.size();
                    categoriesMapsCounter = categoriesMapsCounter + 1
            ) {
                LinkedHashMap<?, ?> itemAdditionalImageMap =
                        itemCategoriesMapsArrayList.get(categoriesMapsCounter);

                itemCategoriesNamesArrayList.add((String) itemAdditionalImageMap.get("categoryName"));
            }
            retrievedEbayItem.get()
                            .setCategories(itemCategoriesNamesArrayList);

            retrievedEbayItem.get()
                    .setPrimaryImgUrl((String)
                            (((LinkedHashMap<?, ?>)
                                    itemMap.get("image")).get("imageUrl")));
            retrievedEbayItem.get()
                    .setPrice(Double.parseDouble((String)
                            ((LinkedHashMap<?, ?>) itemMap.get("price")).get("value")));
            retrievedEbayItem.get()
                    .setPriceCurrency((String)
                            ((LinkedHashMap<?, ?>) itemMap.get("price")).get("currency"));
            retrievedEbayItem.get()
                    .setBuyingOptions((List<String>) itemMap.get("buyingOptions"));
            retrievedEbayItem.get()
                    .setUrl((String) itemMap.get("itemHref"));
            retrievedEbayItem.get()
                    .setCondition((String) itemMap.get("condition"));
            retrievedEbayItem.get()
                    .setShippingCost(Double.parseDouble((String)
                            ((LinkedHashMap<?, ?>)
                                    ((LinkedHashMap<?, ?>)
                                            ((ArrayList<?>)
                                                    itemMap.get("shippingOptions")).get(0)).get("shippingCost")).get("value")));
            retrievedEbayItem.get()
                    .setShippingCostCurrency((String)
                            ((LinkedHashMap<?, ?>)
                                    ((ArrayList<LinkedHashMap<?, ?>>)
                                            itemMap.get("shippingOptions")).get(0).get("shippingCost")).get("currency"));

            ArrayList<LinkedHashMap<?, ?>> itemAdditionalImagesMapsArrayList =
                    (ArrayList<LinkedHashMap<?, ?>>) itemMap.get("additionalImages");
            ArrayList<String> itemAdditionalImagesArrayList = new ArrayList<>();

            for (
                    int additionalImagesMapsCounter = 0;
                    additionalImagesMapsCounter < itemAdditionalImagesMapsArrayList.size();
                    additionalImagesMapsCounter = additionalImagesMapsCounter + 1
            ) {
                LinkedHashMap<?, ?> itemAdditionalImageMap =
                        itemAdditionalImagesMapsArrayList.get(additionalImagesMapsCounter);

                itemAdditionalImagesArrayList.add((String) itemAdditionalImageMap.get("imageUrl"));
            }

            retrievedEbayItem.get().setAdditionalImgUrls(itemAdditionalImagesArrayList);

            retrievedEbayItems.add(retrievedEbayItem);
        }

        return retrievedEbayItems;
    }
}
