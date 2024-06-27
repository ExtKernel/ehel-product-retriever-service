package org.tes.productretrieverservice.item;

import com.fasterxml.jackson.databind.JsonNode;
import org.tes.productretrieverservice.model.Item;

/**
 * An interface for mapping item JSON nodes to object representations.
 *
 * @param <T> the type of object to map.
 */
public interface ItemJsonObjectMapper<T extends Item> {

    /**
     * Maps an item JSON node to an object representation.
     *
     * @param itemJsonNode the item JSON node.
     * @return the object representation.
     */
    T mapItemJsonToItem(JsonNode itemJsonNode);
}
