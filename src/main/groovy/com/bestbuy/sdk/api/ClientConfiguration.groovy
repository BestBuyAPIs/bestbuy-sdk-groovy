package com.bestbuy.sdk.api

import groovy.util.logging.Log4j

@Log4j
class ClientConfiguration {

    static private Properties conf
    static {
        conf = new Properties()
        try {
            new File(ClassLoader.getSystemResource('bestbuy-sdk-groovy.properties').file).withInputStream {
                stream -> conf.load(stream)
            }
            log.debug "Configuration properties loaded correctly"
        } catch (Exception e) {
            log.error "Configuration properties loading failed: $e.message"
            throw e
        }
    }

    static String getDefaultApiKey() {
        System.getenv(conf.getProperty('bestbuy.api.keyEnvName'))
    }

    static String getBaseUrl() {
        conf.getProperty('bestbuy.api.baseUrl')
    }

    static String getBaseBetaUrl() {
        conf.getProperty('bestbuy.api.betaBaseUrl')
    }
}
