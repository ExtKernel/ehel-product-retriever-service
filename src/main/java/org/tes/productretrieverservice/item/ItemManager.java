package org.tes.productretrieverservice.item;

import org.tes.productretrieverservice.model.User;

import java.util.List;

/**
 * An interface to manage items in the context of requests.
 *
 * @param <Item> the type of item.
 * @param <ItemId> the type of identifier that the item uses.
 *  *                        Could be just a String ID or literally anything else,
 *  *                        based on the object.
 * @param <UserType> the type of user to use while building requests.
 */
public interface ItemManager<Item, ItemId, UserType extends User> {

    /**
     * Retrieves an item.
     *
     * @param user the user to use while building the request.
     * @param itemId the id of the item.
     * @return the item.
     */
    List<Item> getItems(
            UserType user,
            ItemId itemId
    );
}
