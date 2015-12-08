package com.bestbuy.sdk.api

import groovy.util.logging.Log4j
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient


/**
 * Implements and handles connection with API, providing GET operation
 * <br>Uses {@code @Log4j} to log events
 */
@Log4j
class ApiConnector {

    private RESTClient restClient

    /**
     * Constructor
     *
     * @param baseUrl URL for the base api connection
     */
    ApiConnector(String baseUrl) {
        if (!baseUrl || baseUrl.trim().size()==0) {
            throw new IllegalArgumentException("Base URL can't be null nor empty")
        }

        restClient = new RESTClient(baseUrl)
    }

    /**
     *  Default Constructor (private) as an Url must be provided to create object
     */
    private ApiConnector() {}

    /**
     * Get resource from API, using base Url and adding path and parameters
     *
     * @param path API path (endpoint) to be concatenated with base URL
     * @param params URL Parameters map to be added to GET request
     * @returns {@link HttpResponseDecorator} if attempt to reach endpoint can be processed(and a HTTP response is received), if unexpected exception happens event is logged and same exception is thrown
     */
    HttpResponseDecorator doGet(String path, Map params) {
        try {
            log.debug "GET ${restClient.uri}$path with params: $params"
            def response = restClient.get(
                    path: path,
                    query: params
            )
            log.debug "Status: ${response.status}"
            return response
        } catch (groovyx.net.http.HttpResponseException ex) {
            // HttpResponseExceptions are returned for 4xx and 5xx statuses
            throw ex
        } catch (Exception e) {
            log.error "RESTClient returned unexpected exception: ${e} $e.message"
            throw e
        }
    }
}