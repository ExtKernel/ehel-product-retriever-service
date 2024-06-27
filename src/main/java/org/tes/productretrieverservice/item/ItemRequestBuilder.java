package org.tes.productretrieverservice.item;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

/**
 * An interface for building item request related objects.
 *
 * @param <AuthObjectId> the id of an object that will provide a data for auth.
 * @param <ItemId> the type of identifier that the item uses.
 *                        Could be just a String ID or literally anything else,
 *                        based on the object.
 */
public interface ItemRequestBuilder<AuthObjectId, ItemId> {

    /**
     * Builds an HTTP request entity.
     *
     * @param requestBody a request body to include in the entity.
     * @param authObjectId the id of the object that will provide a data for auth.
     * @return the HTTP request entity.
     */
    HttpEntity buildHttpRequestEntity(
            String requestBody,
            AuthObjectId authObjectId
    );

    /**
     * Builds an HTTP request entity.
     * The entity will include only headers to authenticate/authorize.
     *
     * @param authObjectId the id of the object that will provide a data for auth.
     * @return the HTTP request entity.
     */
    HttpEntity buildAuthOnlyHttpRequestEntity(
            AuthObjectId authObjectId
    );


    /**
     * Builds a request URL.
     *
     * @param url the url.
     * @param ItemId the id of the item.
     * @return the full request URL.
     */
    String buildRequestUrl(
            String url,
            ItemId ItemId
    );

    RestTemplate getRestTemplate();
}
