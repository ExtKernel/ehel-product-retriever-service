package org.tes.productretrieverservice.model;

import lombok.Data;

import java.util.*;

// Uncomment everything if you want to add persistence layer
//@Entity
@Data
public class EbayItemEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", nullable = false)
//    private long id;

//    @Column(name = "item_id")
    private String itemId;

//    @Column(name = "legacy_item_id)
    private String legacyItemId;

//    @Column(name = "title")
    private String title;

//    @Column(name = "short_description")
    private String shortDescription;

//    @Column(name = "description")
    private String description;

//    @Column(name = "categories")
    private List<String> categories;

//    @Column(name = "category")
    private String category;

//    @ElementCollection
//    @Column(name = "aspect_groups")
//    private List<Map<String, String>> aspectGroups;

//    @ElementCollection
//    @Column(name = "aspects")
    private List<Map<String, String>> aspects;

//    @Column(name = "price")
    private double price;

//    @Column(name = "price_currency")
    private String priceCurrency;

//    @ElementCollection
//    @Column(name = "buying_options")
    private List<String> buyingOptions;

//    @Column(name = "url")
    private String url;

//    @Column(name = "condition")
    private String condition;

//    @Column(name = "shipping_type")
    private String shippingType;

//    @Column(name = "shipping_cost")
    private double shippingCost;

//    @Column(name = "shipping_cost_currency")
    private String shippingCostCurrency;

//    @Column(name = "shipping_cost_type")
    private String shippingCostType;

//    @Column(name = "shipping_regions")
    private List<String> shippingRegions;

//    @Column(name = "item_location_country")
    private String itemLocationCountry;

//    @Column(name = "item_location_city")
    private String itemLocationCity;

//    @Column(name = "primary_img_url")
    private String primaryImgUrl;

//    @ElementCollection
//    @Column(name = "additional_imgs_urls")
    private List<String> additionalImgUrls;

    /**
     * The method writes data from a JSON Map to EbayItemEntity object.
     *
     * @param itemMap A converted to Map JSON retrieved either from eBay Browse API's
     *                "getItem" endpoint with "PRODUCT" or "COMPACT" values of
     *                "fieldgroups" parameter
     *                or from "item_summary" endpoint with any value of
     *                "q", "sort" and "filters" parameters
     * @return An EbayItemEntity object with mapped from the Map values
     */
    public EbayItemEntity writeItemMap(Map<?, ?> itemMap) {
        EbayItemEntity retrievedEbayItem = new EbayItemEntity();

        retrievedEbayItem
                .setItemId((String) itemMap.get("itemId"));
        retrievedEbayItem
                .setLegacyItemId((String) itemMap.getOrDefault("legacyItemId", null));
        retrievedEbayItem
                .setTitle((String) itemMap.getOrDefault("title", null));
        retrievedEbayItem
                .setShortDescription((String)
                        itemMap.getOrDefault("shortDescription", null));
        retrievedEbayItem.setDescription((String) itemMap.getOrDefault("description", null));
        retrievedEbayItem
                .setCategory((String) itemMap.getOrDefault("categoryPath", null));
        retrievedEbayItem
                .setAspects((List<Map<String, String>>)
                        itemMap.getOrDefault("localizedAspects", null));
        retrievedEbayItem
                .setPrimaryImgUrl((String)
                        (((LinkedHashMap<?, ?>)
                                itemMap.get("image")).getOrDefault("imageUrl", null)));
        retrievedEbayItem
                .setPrice(Double.parseDouble((String)
                        ((LinkedHashMap<?, ?>) itemMap.get("price")).get("value")));
        retrievedEbayItem
                .setPriceCurrency((String)
                        ((LinkedHashMap<?, ?>) itemMap.get("price")).get("currency"));
        retrievedEbayItem
                .setBuyingOptions((List<String>) itemMap.getOrDefault("buyingOptions", null));
        retrievedEbayItem
                .setUrl((String) itemMap.getOrDefault("itemWebUrl", null));
        retrievedEbayItem
                .setCondition((String) itemMap.getOrDefault("condition", null));
        retrievedEbayItem
                .setShippingType((String)
                        ((ArrayList<LinkedHashMap<?, ?>>)
                                itemMap.get("shippingOptions")).get(0)
                                .getOrDefault("type", null));
        retrievedEbayItem
                .setShippingCost(Double.parseDouble((String)
                        ((LinkedHashMap<?, ?>)
                                ((LinkedHashMap<?, ?>)
                                        ((ArrayList<?>)
                                                itemMap.get("shippingOptions")).get(0))
                                        .get("shippingCost")).get("value")));
        retrievedEbayItem
                .setShippingCostCurrency((String)
                        ((LinkedHashMap<?, ?>)
                                ((ArrayList<LinkedHashMap<?, ?>>)
                                        itemMap.get("shippingOptions")).get(0)
                                        .get("shippingCost")).get("currency"));
        retrievedEbayItem
                .setShippingCostType((String)
                        ((ArrayList<LinkedHashMap<?, ?>>)
                                itemMap.get("shippingOptions")).get(0)
                                .getOrDefault("shippingCostType", null));
        retrievedEbayItem
                .setItemLocationCity((String)
                        ((LinkedHashMap<?, ?>)
                                itemMap.getOrDefault("itemLocation", null))
                                .getOrDefault("city", null));
        retrievedEbayItem
                .setItemLocationCountry((String)
                        ((LinkedHashMap<?, ?>)
                                itemMap.getOrDefault("itemLocation", null))
                                .getOrDefault("country", null));

        // Writing item shipping locations
        if (
                itemMap.getOrDefault("shipToLocations", null) != null
        ) {
            List<String> itemShippingLocationsNamesList = new ArrayList<>();
            ArrayList<LinkedHashMap<?, ?>> itemShippingLocationsMapsArraylist =
                    (ArrayList<LinkedHashMap<?,?>>)
                            ((LinkedHashMap<?,?>)
                                    itemMap.get("shipToLocations")).get("regionIncluded");

            for (LinkedHashMap<?, ?> includedRegionMap : itemShippingLocationsMapsArraylist) {
                itemShippingLocationsNamesList.add((String) includedRegionMap.get("regionName"));
            }

            retrievedEbayItem
                    .setShippingRegions(itemShippingLocationsNamesList);
        } else {
            retrievedEbayItem.setShippingRegions(null);
        }

        // Writing item additional images urls
        if (itemMap.getOrDefault("additionalImages", null) != null) {
            List<String> itemAdditionalImagesList = new ArrayList<>();
            ArrayList<LinkedHashMap<?, ?>> itemAdditionalImagesMapsArrayList =
                    (ArrayList<LinkedHashMap<?, ?>>) itemMap.get("additionalImages");

            for (LinkedHashMap<?, ?> itemAdditionalImageMap : itemAdditionalImagesMapsArrayList) {
                itemAdditionalImagesList.add((String) itemAdditionalImageMap.get("imageUrl"));
            }

            retrievedEbayItem
                    .setAdditionalImgUrls(itemAdditionalImagesList);
        } else {
            retrievedEbayItem.setAdditionalImgUrls(null);
        }

        // Writing item categories
        if (itemMap.getOrDefault("categories", null) != null) {
            ArrayList<LinkedHashMap<?, ?>> itemCategoriesMapsArrayList =
                    (ArrayList<LinkedHashMap<?, ?>>) itemMap.get("categories");
            List<String> itemCategoriesNamesList = new ArrayList<>();

            for (LinkedHashMap<?, ?> itemAdditionalImageMap : itemCategoriesMapsArrayList) {
                itemCategoriesNamesList.add((String) itemAdditionalImageMap.get("categoryName"));
            }

            retrievedEbayItem
                    .setCategories(itemCategoriesNamesList);
        } else {
            retrievedEbayItem.setCategories(null);
        }

        return retrievedEbayItem;
    }
}
