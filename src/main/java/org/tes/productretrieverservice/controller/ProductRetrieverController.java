package org.tes.productretrieverservice.controller;

import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.tes.productretrieverservice.model.EbayItemEntity;
import org.tes.productretrieverservice.service.ProductRetrieverService;

import java.util.ArrayList;
import java.util.Optional;

@RequestMapping("/retrieve")
@AllArgsConstructor
@RestController
public class ProductRetrieverController {

    private ProductRetrieverService service;

    @GetMapping("/secured")
    public ArrayList<Optional<EbayItemEntity>> retrieveByKeyword(
            @RequestBody Map<String, Object> requestMap) {
        return service.retrieveEbayItemsByKeyword(requestMap);
    }
}
