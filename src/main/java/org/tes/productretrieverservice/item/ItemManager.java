package org.tes.productretrieverservice.item;

/**
 * An interface to manage items in the context of requests.
 *
 * @param <Item> the type of item.
 * @param <ItemId> the type of identifier that the item uses.
 *  *                        Could be just a String ID or literally anything else,
 *  *                        based on the object.
 * @param <User> the type of user to use while building requests.
 */
public interface ItemManager<Item, ItemId, User extends org.tes.productretrieverservice.model.User> {

    /**
     * Retrieves an item.
     *
     * @param user the user to use while building the request.
     * @param itemId the id of the item.
     * @return the item.
     */
    Item getItem(
            User user,
            ItemId itemId
    );
}
