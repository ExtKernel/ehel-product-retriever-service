package org.tes.productretrieverservice.item;

import com.fasterxml.jackson.databind.JsonNode;
import org.tes.productretrieverservice.model.User;

/**
 * An interface for sending item-related requests.
 *
 * @param <ItemIdentificator> the type of identifier that the item uses.
 *                        Could be just a String ID or literally anything else,
 *                        based on the object.
 * @param <UserType> the type of user to be used while building a request.
 */
public interface ItemRequestSender<ItemIdentificator, UserType extends User> {

    /**
     * Sends a request to get items.
     *
     * @param user the user to be used while building the request.
     * @param ItemIdentificator the identificator that will be used to find the items.
     * @return a JSON node of the items.
     */
    JsonNode sendGetItemsRequest(
            UserType user,
            ItemIdentificator ItemIdentificator
    );
}
