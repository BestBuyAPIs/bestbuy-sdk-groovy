package com.bestbuy.sdk
import com.bestbuy.sdk.api.BestBuyClient
/**
 * Created by tarmac on 10/23/15.
 */

class Examples {

    static void main(String[] args) {
        println 'START APP:  ' + args
        def apiKeyArgumentPrefix = "apiKey="
        BestBuyClient bby
        if (args?.count{it.startsWith(apiKeyArgumentPrefix)}>0) {
            bby = new BestBuyClient(args.find{it.startsWith(apiKeyArgumentPrefix)}.split(apiKeyArgumentPrefix)[1])
        } else {
            bby = new BestBuyClient()
        }

        productExamples(bby)
        categoriesExamples(bby)
        reviewsExamples(bby)
        storesExamples(bby)
        storeAvailabilityExamples(bby)
        openBoxExamples(bby)
        recommendationExamples(bby)
    }

    static private productExamples(BestBuyClient bby) {
        println "Product List -> HTTP code: ${bby.getProducts('')?.status}"
        println "Product List by description, showing SKU & NAME, with paging parameters -> HTTP code: ${bby.getProducts('(longDescription=iPhone*|sku=7619002)', [show:'sku,name', pageSize:15, page:5])?.status}"
        println "Product List by customer reviews, showing specific properties-> HTTP code: ${bby.getProducts('(customerReviewAverage>=4&customerReviewCount>100)', [show:'customerReviewAverage,customerReviewCount,name,sku'])?.status}"
        println "Product List -> HTTP code: ${bby.getProducts('(sku in(43900,2088495,7150065))')?.status}"
        println "Product List w/search keyword -> HTTP Code: ${bby.getProducts('((search=touchscreen&search=apple)&salePrice<500&categoryPath.id=pcmcat209000050006)', [show:'name,sku,salePrice'])?.status}"
        println "Product full details for a specific service plan -> HTTP code: ${bby.getProducts('(sku=9993374)',[show:'protectionPlanDetails.states,protectionPlanDetails.termsAndConditions,protectionPlanHighPrice,protectionPlanLowPrice,protectionPlanTerm,protectionPlanType'])?.status}"
    }

    static private categoriesExamples(BestBuyClient bby) {
        println "Categories List -> HTTP code: ${bby.getCategories('').status}"
        println "Category By Id -> HTTP code:  ${bby.getCategories('(id=abcat0011000)').status}"
        println "Category By Name -> HTTP code:  ${bby.getCategories('(name=Leisure Gifts)').status}"
        println "Category Path By Name -> HTTP code:  ${bby.getCategories('(name=Sony DSLR Camera*)', [show: 'path']).status}"
        println "SubCategories for Category Id  -> HTTP code:  ${bby.getCategories('(id=abcat0010000)', [show: 'subCategories']).status}"
        println "Categories with subcategory -> HTTP code: ${bby.getCategories('(subCategories.id=pcmcat140000050035)').status}"
    }

    static private reviewsExamples(BestBuyClient bby) {
        println "Reviews List -> HTTP response code: ${bby.getReviews('').status}"
        println "Reviews For specific SKU, showing id & sku -> HTTP response code: ${bby.getReviews('(sku=1780275)', [show:'id,sku']).status}"
        println "Reviews with rating higher than 4.0 -> HTTP response code: ${bby.getReviews('(rating>4.0)').status}"
        println "Reviews submitted on a specific date -> HTTP response code: ${bby.getReviews('(submissionTime=2014-04-29)').status}"
    }

    static private storesExamples(BestBuyClient bby) {
        println "Stores List -> HTTP response code: ${bby.getStores('').status}"
        println "Stores By Id -> HTTP response code: ${bby.getStores('(storeId=281)').status}"
        println "Stores In a city -> HTTP response code: ${bby.getStores('(city=San Juan)').status}"
        println "Stores within a zipcode -> HTTP response code: ${bby.getStores('(postalCode=55423)').status}"
        println "Stores within an Area (area function) -> HTTP response code: ${bby.getStores('(area(55423,10))', [show:'storeId,name']).status}"
        println "Stores within an Area (area function) -> HTTP response code: ${bby.getStores('(area(71.3,-156.8,1000))', [show:'storeId,name,distance']).status}"
        println "Store hours for a single store using a store identifier -> HTTP response code: ${bby.getStores('(storeId=1118)', [show:'hours,hoursAmPm,gmtOffset,detailedHours']).status}"
        println "Store services -> HTTP response code: ${bby.getStores('(storeId=1118)', [show:'services']).status}"
    }

    static private storeAvailabilityExamples(BestBuyClient bby) {
        println "Availability For store and product list -> HTTP response code: ${bby.getStores('(storeId=8042)', '(sku in (6461052,5909042))', [show:'storeId,storeType,name,city,region,products.name,products.sku,products']).status}"
        println "Express stores within a ten mile radius using a lat/long that have a SKU available -HTTP response code: ${bby.getStores('(area(44.882942,-93.2775,10)&storeType=Express)', '(sku=6461052)', [show:'storeId,storeType,city,region,name,products.name,products.sku,products']).status}"
    }

    static private openBoxExamples(BestBuyClient bby) {
        println "Open Box List -> HTTP response code: ${bby.getOpenBoxProducts('').status}"
        println "Open Box By Category -> HTTP response code: ${bby.getOpenBoxProducts('(categoryId=abcat0400000)').status}"
        println "Open Box By SKU in List -> HTTP response code: ${bby.getOpenBoxProducts('(sku in(5729048,7528703,4839357,8153056,8610161))').status}"
    }

    static private recommendationExamples(BestBuyClient bby) {
        println "Trending Products List -> HTTP response code: ${bby.getRecommendations('trendingViewed', '').status}"
        println "Trending Products List by Category -> HTTP response code: ${bby.getRecommendations('trendingViewed', '(categoryId=abcat0400000)').status}"
        println "Most Viewed Products List -> HTTP response code: ${bby.getRecommendations('mostViewed', '').status}"
        println "Most Viewed Products List by Category -> HTTP response code: ${bby.getRecommendations('mostViewed', '(categoryId=abcat0107000)').status}"
        // Recommendations By SKU
        println "Also Viewed -> HTTP response code: ${bby.getRecommendationsBySKU(5747275, 'alsoViewed', '').status}"
        println "Similar Products -> HTTP response code: ${bby.getRecommendationsBySKU(2874502, 'similar', '').status}"
    }

}
