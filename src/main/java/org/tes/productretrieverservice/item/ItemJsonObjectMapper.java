package org.tes.productretrieverservice.item;

import com.fasterxml.jackson.databind.JsonNode;
import org.tes.productretrieverservice.model.Item;

import java.util.List;

/**
 * An interface for mapping item JSON nodes to object representations.
 *
 * @param <T> the type of object to map.
 */
public interface ItemJsonObjectMapper<T extends Item> {

    /**
     * Maps items JSON node to object representations.
     *
     * @param itemsJsonNode the items JSON node.
     * @return object representations.
     */
    List<T> mapItemsJsonToItems(JsonNode itemsJsonNode);

    /**
     * Maps an item JSON node to an object representations.
     *
     * @param itemJsonNode the item JSON node.
     * @return the object representation.
     */
    T mapItemJsonToItem(JsonNode itemJsonNode);
}
