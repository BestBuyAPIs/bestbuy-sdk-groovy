package com.bestbuy.test

import com.bestbuy.sdk.api.ApiConnector
import com.bestbuy.sdk.api.ApiResponse
import com.bestbuy.sdk.api.ClientConfiguration
import groovyx.net.http.HttpResponseDecorator
import org.apache.http.HttpResponse
import org.apache.http.HttpStatus
import spock.lang.Specification

class ApiResponseSpec extends Specification {

    def "Constructor with null http response will fail"() {
        when: "creating with null http response"
            new ApiResponse(null)
        then: "throws NullPointerException"
            thrown NullPointerException
    }

    def "Constructor with valid response object"() {
        when: "Http response is successful and returns JSON data "
            def response = Mock(HttpResponseDecorator)
            response.data >> '{"sku": 3444, "name": "test"}'
            response.status >> 200
            def apiResponse = new ApiResponse(response)
        then: "object parses received data and allows to access JSON as Map"
            apiResponse.status == 200
            apiResponse.data.sku == 3444
            apiResponse.data.name == "test"
        when: "response returns invalid JSON data"
            response = Mock(HttpResponseDecorator)
            def rawDataNotJson = 'Unexpected string without JSON format'
            response.data >> rawDataNotJson
            apiResponse = new ApiResponse(response)
        then: "object tries to parse it, and returns null, but you can still get raw data and raw http response"
            apiResponse.data == null
            apiResponse.rawData == rawDataNotJson
            apiResponse.httpResponse == response
    }
}
