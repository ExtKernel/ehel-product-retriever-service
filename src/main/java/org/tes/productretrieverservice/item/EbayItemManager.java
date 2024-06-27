package org.tes.productretrieverservice.item;

import org.tes.productretrieverservice.model.EbayItem;
import org.tes.productretrieverservice.model.EbayUser;

import java.util.List;

public interface EbayItemManager extends ItemManager<EbayItem, String, EbayUser> {

    @Override
    EbayItem getItem(
            EbayUser user,
            String keyword
    );

    EbayItem getItem(
            EbayUser user,
            String keyword,
            String sort
    );

    EbayItem getItem(
            EbayUser user,
            String keyword,
            List<String> filters
    );

    EbayItem getItem(
            EbayUser user,
            String keyword,
            String sort,
            List<String> filters
    );

    EbayItem getItemById(
            EbayUser user,
            String itemId
    );

    EbayItem getItemById(
            EbayUser user,
            String itemId,
            String fieldgroups
    );
}
