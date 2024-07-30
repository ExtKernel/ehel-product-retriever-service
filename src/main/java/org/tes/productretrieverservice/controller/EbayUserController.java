package org.tes.productretrieverservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tes.productretrieverservice.item.EbayItemManager;
import org.tes.productretrieverservice.model.EbayItem;
import org.tes.productretrieverservice.model.EbayUser;
import org.tes.productretrieverservice.model.RefreshToken;
import org.tes.productretrieverservice.service.Oauth2UserService;

import java.util.List;
import java.util.Optional;

@RequestMapping("/secured/user")
@RestController
public class EbayUserController {
    private final Oauth2UserService<EbayUser, Long> service;
    private final EbayItemManager itemManager;

    @Autowired
    public EbayUserController(
            Oauth2UserService<EbayUser, Long> service,
            EbayItemManager itemManager
    ) {
        this.service = service;
        this.itemManager = itemManager;
    }

    @PostMapping()
    public EbayUser save(@RequestBody EbayUser user) {
        return service.save(Optional.of(user));
    }

    @PostMapping("/refresh-token/generate/{userId}")
    public RefreshToken generateRefreshToken(@PathVariable Long userId) {
        return service.generateRefreshToken(userId);
    }

    @PostMapping("/refresh-token/save/{userId}")
    public RefreshToken saveRefreshToken(
            @RequestBody RefreshToken refreshToken,
            @PathVariable Long userId
    ) {
        return service.saveRefreshToken(
                userId,
                Optional.of(refreshToken)
        );
    }

    @GetMapping("/{userId}")
    public EbayUser findById(@PathVariable Long userId) {
        return service.findById(userId);
    }

    @GetMapping("/item")
    public EbayItem getItem(
            @RequestParam("userId") Long userId,
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "filters", required = false) List<String> filters
    ) {
        EbayUser user = service.findById(userId);

        if (sort != null && filters != null) {
            return itemManager.getItem(
                    user,
                    keyword,
                    sort,
                    filters
            );
        } else if (sort != null) {
            return itemManager.getItem(
                    user,
                    keyword,
                    sort
            );
        } else if (filters != null) {
            return itemManager.getItem(
                    user,
                    keyword,
                    filters
            );
        } else {
            return itemManager.getItem(
                    user,
                    keyword
            );
        }
    }

    @GetMapping("/{itemId}")
    public EbayItem getItemById(
            @RequestParam("userId") Long userId,
            @PathVariable String itemId,
            @RequestParam(value = "fieldgroups", required = false) String fieldgroups
    ) {
        EbayUser user = service.findById(userId);

        if (fieldgroups != null) {
            return itemManager.getItemById(
                    user,
                    itemId,
                    fieldgroups
            );
        } else {
            return itemManager.getItemById(
                    user,
                    itemId
            );
        }
    }

    @PutMapping()
    public EbayUser update(@RequestBody EbayUser user) {
        return service.update(Optional.of(user));
    }

    @DeleteMapping("/{userId}")
    public void deleteById(@PathVariable Long userId) {
        service.deleteById(userId);
    }
}
