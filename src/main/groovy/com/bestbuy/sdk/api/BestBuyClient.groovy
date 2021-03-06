package com.bestbuy.sdk.api

import groovy.util.logging.Slf4j
import groovyx.net.http.HttpResponseDecorator

/**
 * BestBuy API Client, gives access to all the operations available in API definition
 * <br> You can find complete Best Buy API documentation <a href="https://developer.bestbuy.com/documentation" onclick="window.open('https://developer.bestbuy.com/documentation');return false;">here</a>
 * <br> Uses {@code @Log4j} to log events
 */
@Slf4j
class BestBuyClient {

    String API_KEY
    private ApiConnector apiConnector
    private ApiConnector betaApiConnector

    /**
     * Constructor accepting API key, and URLs to use
     * <br> You can check URLs in Best Buy API Documentation <a href="https://developer.bestbuy.com/documentation" onclick="window.open('https://developer.bestbuy.com/documentation');return false;">here</a>
     *
     * @params apiKey The API key
     * @params baseUrl API base URL for API v1 endpoints. By default '{@literal http://api.remix.bestbuy.com/v1/}' will be used
     * @params betaBaseUrl API base URL for API BETA endpoints. By default '{@literal http://api.bestbuy.com/beta/}' will be used
     */
    BestBuyClient(String apiKey, String baseUrl = ClientConfiguration.baseUrl, String betaBaseUrl = ClientConfiguration.baseBetaUrl) {
        if (apiKey==null || apiKey.trim().size()==0) {
            throw new IllegalArgumentException("Constructor called with no API KEY, either provided or set in environment variable.")
        }
        API_KEY = apiKey
        apiConnector = new ApiConnector(baseUrl)
        betaApiConnector = new ApiConnector(betaBaseUrl)
        log.debug "BestBuyClient created. URL: $baseUrl - BETA URL: ${betaBaseUrl}"
    }

    /**
     * Default constructor, will use a system variable to load your API key (BBY_API_KEY)
     * <br> URLs used will be set by default in constructor {@link #BestBuyClient(String, String, String)}
     * <br> You can check URLs in Best Buy API Documentation <a href="https://developer.bestbuy.com/documentation">here</a>
     */
    BestBuyClient() {
        this(ClientConfiguration.defaultApiKey)
    }

    /**
     * Get product list from API
     * @param query (Optional) a product query to be added to request
     * @param params (Optional)
     * @returns HttpResponseDecorator object with status & json data (if any)
     */
    HttpResponseDecorator getProducts(String query = null, Map params = [:]) {
        apiConnector.doGet("products${query?:''}", addDefaultParameters(params))
    }

    /**
     * Get Category list from API
     * @param query  category query to be added to request.
     * @param params Parameters to be added to request.
     * @return HttpResponseDecorator object with status & json data (if any)
     */
    HttpResponseDecorator getCategories(String query = null, Map params = [:]) {
        apiConnector.doGet("categories${query?:''}", addDefaultParameters(params))
    }

    /**
     * Get Reviews list from API
     * @param query to be added to request.
     * @param params Parameters to be added to request.
     * @return HttpResponseDecorator object with status & json data (if any)
     */
    HttpResponseDecorator getReviews(String query = null, Map params = [:]) {
        apiConnector.doGet("reviews${query?:''}", addDefaultParameters(params))
    }

    /**
     * Get Stores list from API
     * @param query to be added to request.
     * @param params Parameters to be added to request.
     * @return HttpResponseDecorator object with status & json data (if any)
     */
    HttpResponseDecorator getStores(String query = null, Map params = [:]) {
        apiConnector.doGet("stores${query?:''}", addDefaultParameters(params))
    }

    /**
     * Get Stores list from API, providing a products Query
     * @param storesQuery Stores query to be added to request.
     * @param productsQuery Products query to be added to request.
     * @param params Parameters to be added to request.
     * @return HttpResponseDecorator object with status & json data (if any)
     */
    HttpResponseDecorator getStores(String storesQuery, String productsQuery, Map params = [:]) {
        apiConnector.doGet("stores${storesQuery?:''}+products${productsQuery?:''}", addDefaultParameters(params))
    }

    /**
     * Get Open Box list from API
     * @param query to be added to request.
     * @param params Parameters to be added to request.
     * @return HttpResponseDecorator object with status & json data (if any)
     */
    HttpResponseDecorator getOpenBoxProducts(String query = null, Map params = [:]) {
        betaApiConnector.doGet("products/openBox${query?:''}", addDefaultParameters(params))
    }

    /**
     * Get Recommendations list from API
     * @param endpoint Recommendations endpoint to be requested (Check BBY documentation)
     * @param query to be added to request.
     * @param params Parameters to be added to request.
     * @return HttpResponseDecorator object with status & json data (if any)
     */
    HttpResponseDecorator getRecommendations(String endpoint, String query = null, Map params = [:]) {
        betaApiConnector.doGet("products/${endpoint}${query?:''}", addDefaultParameters(params))
    }

    /**
     * Get Recommendations for a specific Product from API
     * @param sku Product SKU to get recommendations for.
     * @param endpoint Recommendations endpoint to be requested (Check BBY documentation)
     * @param query to be added to request.
     * @param params Parameters to be added to request.
     * @return HttpResponseDecorator object with status & json data (if any)
     */
    HttpResponseDecorator getRecommendationsBySKU(long sku, String endpoint, String query = null, Map params = [:]) {
        betaApiConnector.doGet("products/${sku}/${endpoint}${query?:''}", addDefaultParameters(params))
    }

    private Map addDefaultParameters(Map params) {
        [apiKey: API_KEY, format: 'json'] << ((params?.keySet()?.size()>0)?params:[:])
    }
}
