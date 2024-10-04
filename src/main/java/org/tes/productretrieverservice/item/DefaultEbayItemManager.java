package org.tes.productretrieverservice.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tes.productretrieverservice.model.EbayItem;
import org.tes.productretrieverservice.model.EbayUser;

import java.util.List;

@Component
public class DefaultEbayItemManager implements EbayItemManager{
    private final EbayItemRequestSender requestSender;
    private final EbayItemJsonObjectMapper jsonObjectMapper;

    @Autowired
    public DefaultEbayItemManager(
            EbayItemRequestSender requestSender,
            EbayItemJsonObjectMapper jsonObjectMapper
    ) {
        this.requestSender = requestSender;
        this.jsonObjectMapper = jsonObjectMapper;
    }

    @Override
    public List<EbayItem> getItems(
            EbayUser user,
            String keyword
    ) {
        return jsonObjectMapper.mapItemsJsonToItems(requestSender.sendGetItemsRequest(
                user,
                keyword
        ));
    }

    @Override
    public List<EbayItem> getItems(
            EbayUser user,
            String keyword,
            String sort
    ) {
        return jsonObjectMapper.mapItemsJsonToItems(requestSender.sendGetItemsRequest(
                user,
                keyword,
                sort
        ));
    }

    @Override
    public List<EbayItem> getItems(
            EbayUser user,
            String keyword,
            List<String> filters
    ) {
        return jsonObjectMapper.mapItemsJsonToItems(requestSender.sendGetItemsRequest(
                user,
                keyword,
                filters
        ));
    }

    @Override
    public List<EbayItem> getItems(
            EbayUser user,
            String keyword,
            String sort,
            List<String> filters
    ) {
        return jsonObjectMapper.mapItemsJsonToItems(requestSender.sendGetItemsRequest(
                user,
                keyword,
                sort,
                filters
        ));
    }

    @Override
    public EbayItem getItemById(
            EbayUser user,
            String itemId
    ) {
        return jsonObjectMapper.mapItemJsonToItem(requestSender.sendGetItemByIdRequest(
                user,
                itemId
        ));
    }

    @Override
    public EbayItem getItemById(
            EbayUser user,
            String itemId,
            String fieldgroups
    ) {
        return jsonObjectMapper.mapItemJsonToItem(requestSender.sendGetItemByIdRequest(
                user,
                itemId,
                fieldgroups
        ));
    }
}
