package org.tes.productretrieverservice.item;

import org.tes.productretrieverservice.model.EbayItem;
import org.tes.productretrieverservice.model.EbayUser;

import java.util.List;

public interface EbayItemManager extends ItemManager<EbayItem, String, EbayUser> {

    @Override
    List<EbayItem> getItems(
            EbayUser user,
            String keyword
    );

    List<EbayItem> getItems(
            EbayUser user,
            String keyword,
            String sort
    );

    List<EbayItem> getItems(
            EbayUser user,
            String keyword,
            List<String> filters
    );

    List<EbayItem> getItems(
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
