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
    ApiResponse(HttpResponseDecorator response){
        if (!response) throw new NullPointerException("Response provided is null")
        httpResponse = response
        // Try to parse response as a JSON to create Map. If response data is not JSON, set as null
        try {
            data = ((Map)(new JsonSlurper()).parseText(httpResponse.data))?.asImmutable()
        } catch (Exception e) {
            log.debug "Data parsed as JSON failed"
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
     * Get Received JSON data as a Map
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


