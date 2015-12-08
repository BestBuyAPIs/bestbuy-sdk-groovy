package com.bestbuy.test

import com.bestbuy.sdk.api.ApiConnector
import com.bestbuy.sdk.api.ClientConfiguration
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.HttpResponseException
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
        when:
            def apic =  new ApiConnector(ClientConfiguration.baseUrl)
            def response = apic.doGet('',[:])
        then: " throws an HttpResponseException as no valid API KEY is set"
            thrown HttpResponseException
    }
}
