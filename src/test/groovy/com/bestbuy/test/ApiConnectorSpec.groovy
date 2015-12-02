package com.bestbuy.test

import com.bestbuy.sdk.api.ApiConnector
import com.bestbuy.sdk.api.ApiResponse
import com.bestbuy.sdk.api.ClientConfiguration
import spock.lang.Specification

class ApiConnectorSpec extends Specification {

    def "Constructor with null/empty baseUrl will fail"() {
        when:
            new ApiConnector(baseUrl)
        then:
            thrown IllegalArgumentException
        where:
            baseUrl << [null, '', '  ']
    }

    def "Constructor with invalid Url fails on doGet"() {
        setup:
            def apic =  new ApiConnector('notAnUrl')
        when:
            apic.doGet('',[:])
        then:
            thrown IllegalStateException
    }

    def "Creating an object with valid URL"() {
        setup:
            def apic =  new ApiConnector(ClientConfiguration.baseUrl)
        when:
            def response = apic.doGet('',[:])
        then:
            response instanceof ApiResponse
    }
}
