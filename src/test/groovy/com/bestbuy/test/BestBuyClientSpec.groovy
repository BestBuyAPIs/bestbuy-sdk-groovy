package com.bestbuy.test

import com.bestbuy.sdk.api.ApiResponse

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
        when:
            ClientConfiguration.metaClass.static.getDefaultApiKey = { ->
                // By returning null, we mock that environment variable is not set
                null
            }
            new BestBuyClient()
        then:
            thrown IllegalArgumentException
        when:
            ClientConfiguration.metaClass.static.getDefaultApiKey = { ->
                // Return a value as if environment variable is set
                'testApi'
            }
            def bby = new BestBuyClient()
        then:
            bby.API_KEY == ClientConfiguration.defaultApiKey
    }

    def "Constructor with provided API KEY"() {
        when:
            def apiKey = 'myBestBuyApiKey'
            def bby = new BestBuyClient(apiKey)
        then:
            bby.API_KEY == apiKey
    }

   def "Constructor with null parameters"() {
        when:
            new BestBuyClient(apiKey, baseUrl, baseBetaUrl)
        then:
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
       when:
            def response = bby.getProducts('', [:])
       then:
            response instanceof ApiResponse
       when:
            response = bby.getProducts('(sku=1234)')
       then:
            response instanceof ApiResponse
       when:
           response = bby.getProducts('(sku=1234)')
       then:
           response instanceof ApiResponse
       when:
           response = bby.getProducts(null,[page:4])
       then:
           response instanceof ApiResponse
       when:
           response = bby.getProducts('(category=someCategory)',[page:2])
       then:
           response instanceof ApiResponse
   }

    def "Retrieving categories"() {
        setup:
            def bby = new BestBuyClient()
        when:
            def response = bby.getCategories('', [:])
        then:
            response instanceof ApiResponse
        when:
            response = bby.getCategories('(sku=1234)')
        then:
            response instanceof ApiResponse
        when:
            response = bby.getCategories('(sku=1234)')
        then:
            response instanceof ApiResponse
        when:
            response = bby.getCategories(null,[page:4])
        then:
            response instanceof ApiResponse
        when:
            response = bby.getCategories('(property=someProperty)',[page:2])
        then:
            response instanceof ApiResponse
    }

    def "Retrieving reviews"() {
        setup:
            def bby = new BestBuyClient()
        when:
            def response = bby.getReviews('', [:])
        then:
            response instanceof ApiResponse
        when:
            response = bby.getReviews('(property=someValue)')
        then:
            response instanceof ApiResponse
        when:
            response = bby.getReviews('(sku=1234)')
        then:
            response instanceof ApiResponse
        when:
            response = bby.getReviews(null,[page:4])
        then:
            response instanceof ApiResponse
        when:
            response = bby.getReviews('(property=someValue)',[page:2])
        then:
            response instanceof ApiResponse
    }

    def "Retrieving Stores"() {
        setup:
            def bby = new BestBuyClient()
        when:
            def response = bby.getStores('', [:])
        then:
            response instanceof ApiResponse
        when:
            response = bby.getStores('(property=someValue)')
        then:
            response instanceof ApiResponse
        when:
            response = bby.getStores('(property=someValue)')
        then:
            response instanceof ApiResponse
        when:
            response = bby.getStores(null,[page:4])
        then:
            response instanceof ApiResponse
        when:
            response = bby.getStores('(property=someValue)',[page:2])
        then:
            response instanceof ApiResponse
        when:
            response = bby.getStores('(property=someValue)', '(productProperty=someOtherValue)',[page:2])
        then:
            response instanceof ApiResponse
    }

    def "Retrieving openBox Products"() {
        setup:
            def bby = new BestBuyClient()
        when:
            def response = bby.getOpenBoxProducts('', [:])
        then:
            response instanceof ApiResponse
        when:
            response = bby.getOpenBoxProducts('(property=someValue)')
        then:
            response instanceof ApiResponse
        when:
            response = bby.getOpenBoxProducts('(sku=1234)')
        then:
            response instanceof ApiResponse
        when:
            response = bby.getOpenBoxProducts(null,[page:4])
        then:
            response instanceof ApiResponse
        when:
            response = bby.getOpenBoxProducts('(property=someValue)',[page:2])
        then:
            response instanceof ApiResponse
    }

    def "Retrieving Recommendations"() {
        setup:
            def bby = new BestBuyClient()
        when:
            def response = bby.getRecommendations('trendingViewed','', [:])
        then:
            response instanceof ApiResponse
        when:
            response = bby.getRecommendations('trendingViewed','(property=someValue)')
        then:
            response instanceof ApiResponse
        when:
            response = bby.getRecommendations('trendingViewed','(property=someValue)')
        then:
            response instanceof ApiResponse
        when:
            response = bby.getRecommendations('trendingViewed',null,[page:4])
        then:
            response instanceof ApiResponse
        when:
            response = bby.getRecommendations('trendingViewed','(property=someValue)',[page:2])
        then:
            response instanceof ApiResponse
        when:
            response = bby.getRecommendationsBySKU(1234,'trendingViewed','(property=someValue)',[page:2])
        then:
            response instanceof ApiResponse
    }
}
