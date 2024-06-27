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
    public EbayItem getItem(
            EbayUser user,
            String keyword
    ) {
        return jsonObjectMapper.mapItemJsonToItem(requestSender.sendGetItemRequest(
                user,
                keyword
        ));
    }

    @Override
    public EbayItem getItem(
            EbayUser user,
            String keyword,
            String sort
    ) {
        return jsonObjectMapper.mapItemJsonToItem(requestSender.sendGetItemRequest(
                user,
                keyword,
                sort
        ));
    }

    @Override
    public EbayItem getItem(
            EbayUser user,
            String keyword,
            List<String> filters
    ) {
        return jsonObjectMapper.mapItemJsonToItem(requestSender.sendGetItemRequest(
                user,
                keyword,
                filters
        ));
    }

    @Override
    public EbayItem getItem(
            EbayUser user,
            String keyword,
            String sort,
            List<String> filters
    ) {
        return jsonObjectMapper.mapItemJsonToItem(requestSender.sendGetItemRequest(
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
