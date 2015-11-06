package com.bestbuy.test

import com.bestbuy.sdk.api.ApiConnector
import com.bestbuy.sdk.api.ApiResponse
import groovyx.net.http.HttpResponseDecorator

/**
 * Created by tarmac on 11/3/15.
 */

import spock.lang.*
import com.bestbuy.sdk.api.BestBuyClient
import com.bestbuy.sdk.api.ClientConfiguration

class BestBuyClientSpec extends Specification {

    @Shared
    def originalClientConfigApiKeyMethod


    def setupSpec() {
        originalClientConfigApiKeyMethod = ClientConfiguration.metaClass.getMetaMethod('getDefaultApiKey', [] as Class[])
    }

    def cleanupSpec() {
        //
        ClientConfiguration.metaClass.'static'.getDefaultApiKey = { ->
            originalClientConfigApiKeyMethod.invoke(delegate)
        }
    }


    def "Constructor with default values, using Environment Variable API KEY"() {
        when: "creating a BestBuyClient without parameters, and no environment variable"
            ClientConfiguration.metaClass.static.getDefaultApiKey = { ->
                // By returning null, we mock that environment variable is not set
                null
            }
            new BestBuyClient()
        then: "creation fails"
            thrown IllegalArgumentException
        when: "creating a BestBuyClient without parameters, and environment variable Set"
            ClientConfiguration.metaClass.static.getDefaultApiKey = { ->
                // Return a value as if environment variable is set
                'testApi'
            }
            def bby = new BestBuyClient()
        then: "API KEY is set with value from Environment Variable, and Urls with default values"
            bby.API_KEY == ClientConfiguration.defaultApiKey
    }

    def "Constructor with provided API KEY"() {
        when: "creating a BestBuyClient providing only API KEY"
            def apiKey = 'myBestBuyApiKey'
            def bby = new BestBuyClient(apiKey)
        then: "API KEY is set with value apiKey"
            bby.API_KEY == apiKey
    }

   def "Constructor with null parameters"() {
        when: "creating a BestBuyClient providing null values in one of the parameters"
            new BestBuyClient(apiKey, baseUrl, baseBetaUrl)
        then: "throws exception"
            thrown IllegalArgumentException
        where:
            apiKey        | baseUrl               | baseBetaUrl
            'apiKeyValue' | 'http://base.url.com' | null
            'apiKeyValue' | null                  | 'http://baseBeta.url.com'
            'apiKeyValue' | null                  | null
            null          | 'http://base.url.com' | 'http://baseBeta.url.com'
            null          | 'http://base.url.com' | null
            null          | null                  | 'http://baseBeta.url.com'
            null          | null                  | null
   }

   def "Retrieving products"() {
       setup:
            def bby = new BestBuyClient()
       when: "getting all products "
            def response = bby.getProducts('', [:])
       then: "an ApiResponse object is returned"
            response instanceof ApiResponse
       when: "getting products using query"
            response = bby.getProducts('(sku=1234)')
       then: "an ApiResponse object is returned"
            response instanceof ApiResponse
       when: "getting products using query"
           response = bby.getProducts('(sku=1234)')
       then: "an ApiResponse object is returned"
           response instanceof ApiResponse
       when: "getting products with extra parameters (pagination or others) "
           response = bby.getProducts(null,[page:4])
       then: "an ApiResponse object is returned"
           response instanceof ApiResponse
       when: "getting products with query & extra parameters (pagination or others) "
           response = bby.getProducts('(category=someCategory)',[page:2])
       then: "an ApiResponse object is returned"
           response instanceof ApiResponse
   }

    def "Retrieving categories"() {
        setup:
            def bby = new BestBuyClient()
        when: "getting all categories "
            def response = bby.getCategories('', [:])
        then: "an ApiResponse object is returned"
            response instanceof ApiResponse
        when: "using query"
            response = bby.getCategories('(sku=1234)')
        then: "an ApiResponse object is returned"
            response instanceof ApiResponse
        when: "using query"
            response = bby.getCategories('(sku=1234)')
        then: "an ApiResponse object is returned"
            response instanceof ApiResponse
        when: "with extra parameters (pagination or others) "
            response = bby.getCategories(null,[page:4])
        then: "an ApiResponse object is returned"
            response instanceof ApiResponse
        when: "with query & extra parameters (pagination or others) "
            response = bby.getCategories('(property=someProperty)',[page:2])
        then: "an ApiResponse object is returned"
            response instanceof ApiResponse
    }

    def "Retrieving reviews"() {
        setup:
        def bby = new BestBuyClient()
        when: "getting all reviews "
        def response = bby.getReviews('', [:])
        then: "an ApiResponse object is returned"
        response instanceof ApiResponse
        when: "using query"
        response = bby.getReviews('(property=someValue)')
        then: "an ApiResponse object is returned"
        response instanceof ApiResponse
        when: "using query"
        response = bby.getReviews('(sku=1234)')
        then: "an ApiResponse object is returned"
        response instanceof ApiResponse
        when: "with extra parameters (pagination or others) "
        response = bby.getReviews(null,[page:4])
        then: "an ApiResponse object is returned"
        response instanceof ApiResponse
        when: "with query & extra parameters (pagination or others) "
        response = bby.getReviews('(property=someValue)',[page:2])
        then: "an ApiResponse object is returned"
        response instanceof ApiResponse
    }

    def "Retrieving Stores"() {
        setup:
            def bby = new BestBuyClient()
        when: "getting all  "
            def response = bby.getStores('', [:])
        then: "an ApiResponse object is returned"
            response instanceof ApiResponse
        when: "using query"
            response = bby.getStores('(property=someValue)')
        then: "an ApiResponse object is returned"
            response instanceof ApiResponse
        when: "using query"
            response = bby.getStores('(property=someValue)')
        then: "an ApiResponse object is returned"
            response instanceof ApiResponse
        when: "with extra parameters (pagination or others) "
            response = bby.getStores(null,[page:4])
        then: "an ApiResponse object is returned"
            response instanceof ApiResponse
        when: "with query & extra parameters (pagination or others) "
            response = bby.getStores('(property=someValue)',[page:2])
        then: "an ApiResponse object is returned"
            response instanceof ApiResponse
        when: "with stores&product queries as well as extra parameters (pagination or others) "
            response = bby.getStores('(property=someValue)', '(productProperty=someOtherValue)',[page:2])
        then: "an ApiResponse object is returned"
            response instanceof ApiResponse
    }

    def "Retrieving openBox Products"() {
        setup:
            def bby = new BestBuyClient()
        when: "getting all openBox "
            def response = bby.getOpenBoxProducts('', [:])
        then: "an ApiResponse object is returned"
            response instanceof ApiResponse
        when: "using query"
            response = bby.getOpenBoxProducts('(property=someValue)')
        then: "an ApiResponse object is returned"
            response instanceof ApiResponse
        when: "using query"
            response = bby.getOpenBoxProducts('(sku=1234)')
        then: "an ApiResponse object is returned"
            response instanceof ApiResponse
        when: "with extra parameters (pagination or others) "
            response = bby.getOpenBoxProducts(null,[page:4])
        then: "an ApiResponse object is returned"
            response instanceof ApiResponse
        when: "with query & extra parameters (pagination or others) "
            response = bby.getOpenBoxProducts('(property=someValue)',[page:2])
        then: "an ApiResponse object is returned"
            response instanceof ApiResponse
    }

    def "Retrieving Recommendations"() {
        setup:
            def bby = new BestBuyClient()
        when: "getting all recommendations"
            def response = bby.getRecommendations('trendingViewed','', [:])
        then: "an ApiResponse object is returned"
            response instanceof ApiResponse
        when: "using query"
            response = bby.getRecommendations('trendingViewed','(property=someValue)')
        then: "an ApiResponse object is returned"
            response instanceof ApiResponse
        when: "using query"
            response = bby.getRecommendations('trendingViewed','(property=someValue)')
        then: "an ApiResponse object is returned"
            response instanceof ApiResponse
        when: "with extra parameters (pagination or others) "
            response = bby.getRecommendations('trendingViewed',null,[page:4])
        then: "an ApiResponse object is returned"
            response instanceof ApiResponse
        when: "with query & extra parameters (pagination or others) "
            response = bby.getRecommendations('trendingViewed','(property=someValue)',[page:2])
        then: "an ApiResponse object is returned"
            response instanceof ApiResponse
        when: "with sku Filter & queries & extra parameters (pagination or others) "
            response = bby.getRecommendationsBySKU(1234,'trendingViewed','(property=someValue)',[page:2])
        then: "an ApiResponse object is returned"
            response instanceof ApiResponse
    }
}
