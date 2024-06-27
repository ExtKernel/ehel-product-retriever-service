package org.tes.productretrieverservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class EbayItem extends Item {
    private String itemId;
    private String legacyItemId;
    private String title;
    private String shortDescription;
    private String description;
    private List<String> categories;
    private String category;
    private List<Map<String, String>> aspects;
    private double price;
    private String priceCurrency;
    private List<String> buyingOptions;
    private String url;
    private String condition;
    private String shippingType;
    private double shippingCost;
    private String shippingCostCurrency;
    private String shippingCostType;
    private List<String> shippingRegions;
    private String itemLocationCountry;
    private String itemLocationCity;
    private String primaryImgUrl;
    private List<String> additionalImgUrls;
}
