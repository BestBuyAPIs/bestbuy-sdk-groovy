# Best Buy API Groovy SDK
This library allows easy access to Best Buy API. 
To use this library you will need an API KEY, which you can get from BestBuy's site.  
You can find complete documentation at [Best Buy Developer's site](https://developer.bestbuy.com/documentation)

## Usage
This library is a gradle project, and all commands described below can be executed by just using the gradle script included in the root directory: gradlew (or gradlew.bat in Win).

### Create Jar
To create the jar to be included in your project, just run:

```
./gradlew build
```

### Using the library in your project
With the jar generated in the previous step included in your project classpath, you can use BestBuyClient class to start interacting with the API. 

```groovy
def bby = new BestBuyClient()
println "BEST BUY PRODUCTS: ${bby.getProducts().data}"
```

The above example assumes you have set an environment variable 'BBY_API_KEY' with your Api Key.

If you want to provide it directly in your code, you can just call the correct constructor

```groovy
def bby = new BestBuyClient("myApiKeyValue")
println "BEST BUY PRODUCTS: ${bby.getProducts().data}"
```
#### Dependencies
There are two dependencies for this library (as you can check in build.gradle file):
 - Log4j
 - HttpBuilder 
 
If you want to generate a distribution zip with complete dependencies jars included, you can run command: 

```
./gradlew distZip
```

This command will generate the ZIP file in path ./build/distributions
    
### More Examples
Please check the com.bestbuy.Example class to see all

You can run the examples with the 'run' task. If you have the BBY_API_KEY environment variable set:

```
./gradlew run 
```

If not, just provide it as an argument(e.g. 'myApiKeyValue':

```
./gradlew run -PexampleArgs=apiKey:myApiKeyValue
```
 
### Running tests
The spock tests can be executed by gradle's 'test' task:

```
./gradlew test
```

### Generating Groovy Doc
Complete Groovy Doc for the library can be generated with 'groovyDoc' 

```
./gradlew groovyDoc
```
