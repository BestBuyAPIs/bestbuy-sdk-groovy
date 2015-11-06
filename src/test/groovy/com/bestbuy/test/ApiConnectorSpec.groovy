package com.bestbuy.test

import com.bestbuy.sdk.api.ApiConnector
import com.bestbuy.sdk.api.ApiResponse
import com.bestbuy.sdk.api.ClientConfiguration
import spock.lang.Specification

class ApiConnectorSpec extends Specification {

    def "Constructor with null/empty baseUrl will fail"() {
        when: "creating with null/empty baseUrl"
            new ApiConnector(baseUrl)
        then: "creation fails with exception"
            thrown IllegalArgumentException
        where:
            baseUrl << [null, '', '  ']
    }

    def "Constructor with invalid Url"() {
        setup: "Creating with a valid  "
            def apic =  new ApiConnector('notAnUrl')
        when: "trying to get any path from url"
            apic.doGet('',[:])
        then: "throws exception"
            thrown IllegalStateException
    }

    def "Creating an object with valid URL"() {
        setup: "Creating with a valid URL"
            def apic =  new ApiConnector(ClientConfiguration.baseUrl)
        when: "trying to get any path from url"
            def response = apic.doGet('',[:])
        then: "returns a valid ApiResponse object"
            response instanceof ApiResponse
    }
}
