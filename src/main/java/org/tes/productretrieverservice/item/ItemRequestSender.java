package org.tes.productretrieverservice.item;

import com.fasterxml.jackson.databind.JsonNode;
import org.tes.productretrieverservice.model.User;

/**
 * An interface for sending item-related requests.
 *
 * @param <ItemId> the type of identifier that the item uses.
 *                        Could be just a String ID or literally anything else,
 *                        based on the object.
 * @param <UserType> the type of user to be used while building a request.
 */
public interface ItemRequestSender<ItemId, UserType extends User> {

    /**
     * Sends a request to get an item.
     *
     * @param user the user to be used while building the request.
     * @param ItemId the id of the item.
     * @return a JSON node of the item.
     */
    JsonNode sendGetItemRequest(
            UserType user,
            ItemId ItemId
    );
}
