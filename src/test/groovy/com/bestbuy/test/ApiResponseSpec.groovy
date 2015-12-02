package com.bestbuy.test

import com.bestbuy.sdk.api.ApiResponse
import groovyx.net.http.HttpResponseDecorator
import spock.lang.Specification

class ApiResponseSpec extends Specification {

    def "Constructor with null http response will fail"() {
        when:
            new ApiResponse(null)
        then:
            thrown NullPointerException
    }

    def "Constructor with valid response object"() {
        when:
            def response = Mock(HttpResponseDecorator)
            response.data >> ["sku": 3444, "name": "test"]
            response.status >> 200
            def apiResponse = new ApiResponse(response)
        then:
            apiResponse.status == 200
            apiResponse.data.sku == 3444
            apiResponse.data.name == "test"
        when:
            response = Mock(HttpResponseDecorator)
            def rawDataNotJson = 'Unexpected string without JSON format'
            response.data >> rawDataNotJson
            apiResponse = new ApiResponse(response)
        then:
            apiResponse.data == null
            apiResponse.rawData == rawDataNotJson
            apiResponse.httpResponse == response
    }
}
