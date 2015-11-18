package com.bestbuy.sdk.api

import groovy.json.JsonSlurper
import groovy.util.logging.Log
import groovy.util.logging.Log4j
import groovyx.net.http.HttpResponseDecorator

@Log4j
class ApiResponse {

    private HttpResponseDecorator httpResponse
    private Map data

    /**
     *
     * @param response An HttpResponseDecorator must be provided
     */
    ApiResponse(HttpResponseDecorator response) {
        if (!response) throw new NullPointerException("Response provided is null")
        httpResponse = response
        // API returns XML sometimes specially in errors (403, etc.)
        // Data is set only if message from API is a valid JSON
        if (httpResponse.data instanceof Map) {
            data = httpResponse.data
        }
    }

    /**
     * Get Status response
     * @return HTTP status code
     */
    int getStatus () {
        httpResponse.status
    }

    /**
     * Get Received JSON data when status is 200
     * @return Map with JSON data
     */
    Map getData() {
        data
    }

    /**
     * Get raw httpResponse data received
     * @return HTTP status code
     */
    Object getRawData() {
        httpResponse.data
    }

    /**
     * Get Http Response obtained
     * @return HttpResponseDecorator from api call
     */
    HttpResponseDecorator getHttpResponse() {
        httpResponse
    }
}


