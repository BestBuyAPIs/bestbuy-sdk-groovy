package com.bestbuy.test

import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.HttpResponseException
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

   def "Get operations with invalid API KEY throw HttpException" () {
       when:
            def bby = new BestBuyClient('invalidApiKey')
            bby.getProducts()
       then:
            thrown HttpResponseException
       when:
            bby.getOpenBoxProducts()
       then:
            thrown HttpResponseException
       when:
            bby.getReviews()
       then:
            thrown HttpResponseException
       when:
            bby.getStores()
       then:
            thrown HttpResponseException
       when:
            bby.getRecommendations('trendingViewed', '')
       then:
            thrown HttpResponseException
    }
}
