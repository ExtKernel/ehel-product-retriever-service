package org.tes.productretrieverservice.model;

import lombok.Data;

import java.util.List;

// Uncomment everything if you want to add persistence layer
//@Entity
@Data
public class EbayItemEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", nullable = false)
    private long id;

//    @Column(name = "epid")
    private long epid;

//    @Column(name = "item_id")
    private String itemId;

//    @Column(name = "title")
    private String title;

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

//    @Column(name = "shipping_cost")
    private double shippingCost;

//    @Column(name = "shipping_cost_currency")
    private String shippingCostCurrency;

//    @Column(name = "primary_img_url")
    private String primaryImgUrl;

//    @ElementCollection
//    @Column(name = "additional_imgs_urls")
    private List<String> additionalImgUrls;
}
