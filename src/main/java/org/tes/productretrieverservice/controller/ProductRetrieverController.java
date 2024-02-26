package org.tes.productretrieverservice.controller;

import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.tes.productretrieverservice.model.EbayItemEntity;
import org.tes.productretrieverservice.service.ProductRetrieverService;

import java.util.ArrayList;

@RequestMapping("/retrieve")
@AllArgsConstructor
@RestController
public class ProductRetrieverController {

    private ProductRetrieverService service;

    @GetMapping("/secured")
    public ArrayList<EbayItemEntity> retrieveItemsByKeyword(
            @RequestBody Map<String, Object> requestMap) {
        return service.retrieveEbayItemsByKeyword(requestMap);
    }

    @GetMapping("/secured/{itemId}")
    public EbayItemEntity retrieveItemByItemId(@PathVariable String itemId,
                                               @RequestParam boolean productFieldgroupsEnabled) {
        return service.retrieveEbayItemByItemId(itemId, productFieldgroupsEnabled);
    }
}
