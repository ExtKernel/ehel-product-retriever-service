# Product Retriever Service
A service for retrieving items from marketplaces. Currently, only eBay is supported.

## Endpoints:

### eBay User Endpoints

**Base path:** `/user`

#### Save a new eBay user
- **Method:** `POST`
- **Path:** `/user`
- **Request body example:**
    ```json
    {
        "username": "johndoe",
        "clientId": "your-ebay-client-id",
        "clientSecret": "your-ebay-client-secret"
    }
    ```

#### Generate a refresh token for a user
- **Method:** `POST`
- **Path:** `/user/refresh-token/generate/{userId}`
- **Example:** `/user/refresh-token/generate/123`

#### Save a refresh token for a user
- **Method:** `POST`
- **Path:** `/user/refresh-token/save/{userId}`
- **Example:** `/user/refresh-token/save/123`
- **Request body example:**
    ```json
    {
        "token": "your-refresh-token",
        "expiresIn": 3600
    }
    ```

#### Find an eBay user by ID
*Note: This endpoint operates within this service and only accesses the service's database, not the eBay Developer API.*
- **Method:** `GET`
- **Path:** `/user/{userId}`
- **Example:** `/user/123`

#### Get an eBay item
- **Method:** `GET`
- **Path:** `/user/item`
- **Query parameters:**
    - `userId` (required)
    - `keyword` (required)
    - `sort` (optional)
    - `filters` (optional, can be multiple)

**Examples:**

1) **Basic search:**

   `GET /user/item?userId=123&keyword=smartphone`

   This will search for smartphones without any additional sorting or filtering.

2) **Search with sorting:**

   `GET /user/item?userId=123&keyword=smartphone&sort=price_asc`

   This will search for smartphones and sort the results by price in ascending order. You can visit eBay Developer API documentation for supported sort types. Supported sort types as of 30.07.2024 include:

   - **Price**
       - **Ascending:** `sort=price`
           - Items with the lowest total cost (price + shipping) are shown first.
       - **Descending:** `sort=-price`
           - Items with the highest total cost are shown first.

   - **Distance**
       - `sort=distance`
       - Items closest to the buyer's location are shown first.
       - **Important:** Include all required pickup filters when sorting by distance.

   - **Date**
       - **Listing date:** `sort=newlyListed`
           - Newly listed items are shown first.
       - **End date:** `sort=endingSoonest`
           - Items closest to their listing end date/time are shown first.

If no sort parameter is provided, the results default to "Best Match".

3) **Search with a single filter:**

    `GET /user/item?userId=123&keyword=smartphone&filters=condition:new`

    This will search for new smartphones.


4) **Search with multiple filters:**

    `GET /user/item?userId=123&keyword=smartphone&filters=condition:new&filters=brand:apple`

   This will search for new Apple smartphones. You can visit eBay Developer API documentation for supported filter types.

#### Get an eBay item by ID
- Method: `GET`
- Path: `/user/{itemId}`
- Query parameters:
  - `userId` (required)
  - `fieldgroups` (optional)

**Examples:**
1) `/user/item123456?userId=123&fieldgroups=PRODUCT`
2) `/user/item123456?userId=123&fieldgroups=COMPACT`
3) `/user/item123456?userId=123&fieldgroups=ADDITIONAL_SELLER_DETAILS`

You can visit eBay Developer API documentation for supported fieldgroups.

#### Delete an eBay user by ID
- Method: `DELETE`
- Path: `/user/{userId}`
- Example: `/user/123`

### Auth Code Endpoints
Base path: `/auth-code`

#### Save a new auth code
You can enter this endpoint in the "Your auth accepted" field on the "User Tokens" page of your eBay Developer account.
- Method: `GET`
- Path: `/auth-code`
- Query parameters:
  - `code` (required)
  - `expires_in` (required)
- Example: `/auth-code?code=your-auth-code&expires_in=3600`

#### Find the latest auth code
- Method: `GET`
- Path: `/auth-code/latest`

## Environment Variables

The following environment variables are used to configure the Product Retriever Service. They should be set in your environment or configuration files to ensure proper operation.

### eBay API URLs
- **EBAY_BROWSE_API_ITEM_SUMMARY_URL**
    - This environment variable specifies the URL for the eBay Browse API's item summary endpoint.

- **EBAY_BROWSE_API_GET_ITEM_URL**
    - This variable defines the URL for the eBay Browse API's get item endpoint.

### Token Retrieval
- **ACCESS_TOKEN_RETRIEVE_URI**
    - URL used to retrieve the access token from eBay’s OAuth2 system.

- **REFRESH_TOKEN_RETRIEVE_URI**
    - URL used to retrieve the refresh token from eBay’s OAuth2 system.

### Security and Roles
- **PRINCIPAL_ROLE_NAME**
      - Specifies the role name for the principal user in the application. The user for whom you will obtain an access token from your OAuth2 provider should have this role.

### Service Discovery
- **EUREKA_URI**
    - The URL for the Eureka service registry. Default: `http://localhost:8761/eureka`.

### Server Configuration
- **SERVER_PORT**
    - The port on which the application server will run. Default: `8000`.

### Spring Cloud and Security
- **OAUTH2_PROVIDER_ISSUER_URL**
    - The issuer URI for the OAuth2 provider.

- **OAUTH2_PROVIDER_CLIENT_ID**
    - Client ID for the OAuth2 provider's opaque token configuration.

- **OAUTH2_PROVIDER_CLIENT_SECRET**
    - Client secret for the OAuth2 provider's opaque token configuration.

- **OAUTH2_PROVIDER_INTROSPECTION_URL**
    - URL for the introspection endpoint of the OAuth2 provider.

### Additional Configuration
- **LIVERELOAD_PORT**
    - Port used for live reload functionality. Default: `35730`.

Ensure these variables are properly set to avoid runtime issues.
