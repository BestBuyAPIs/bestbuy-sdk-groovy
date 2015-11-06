package com.bestbuy.sdk.api

/**
 * Created by tarmac on 11/3/15.
 */

class ClientConfiguration {

    static String getDefaultApiKey() {
        System.getenv('BBY_API_KEY')
    }

    static String getBaseUrl() {
        'http://api.remix.bestbuy.com/v1/'
    }

    static String getBaseBetaUrl() {
        'http://api.bestbuy.com/beta/'
    }
}
