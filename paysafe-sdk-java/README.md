# Paysafe Pay Later Java SDK

The Paysafe Pay Later Java SDK lets you communicate with the [Paysafe Pay Later platform](https://support.paysafe?.com/) and provides the following features:

* Handles authentication in all requests towards the API 
* Wrapper around all API calls and responses to make building a request and interpreting responses as easy as possible
* Takes care of marshalling and unmarshalling request and responses
* Processes errors from the API and transforms them in specific exceptions
* Handles decrypting of incoming webhook messages

## Documentation

The project contains multiple integration tests that shows how to use the SDK.

For more examples and detailed information on how to use the SDK, you can go to the [Paysafe Pay Later Developer Hub](https://support.paysafe?.com/sdk/java/).

## Installation

When using [Maven](http://maven.apache.org/), include the SDK as a Maven dependency:

    <dependency>
      <groupId>com.paysafe.paylater</groupId>
      <artifactId>paysafe-sdk-java</artifactId>
      <version>x.y.z</version>
    </dependency>

If Maven is not a possibility, download the latest version of the SDK from GitHub. Retrieve the `paysafe-sdk-java-x.y.z-bin.zip` file from the [releases](https://github.com/Paysafe?/paysafe-sdk-java/releases) page, where `x.y.z` is the version number.
Add the JAR files inside the `lib` folder to your project, except for `paysafe-sdk-java-x.y.z-sources.jar`

## Project structure

The project contains three components:

* `/src/main/java/` which contains all source code of the SDK
* `/src/test/java/` which contains all unit tests
* `/src/it/java/` which contains all the integration tests

## Building the project

[Maven](http://maven.apache.org/) is needed to build the project. To build the SDK, the following command must be executed in the directory that contains the `pom.xml` file:

    mvn -clean package

This command will run all unit tests and once succesful generate the following files in the `target` directory:
* `paysafe-sdk-java-x.y.z-bin.zip`, contains all the JAR files for standalone deployments
* `paysafe-sdk-java-x.y.z.jar`, contains the compiled class files
* `paysafe-sdk-java-x.y.z-sources.jar`, contains the source code
* `paysafe-sdk-java-x.y.z-src.zip`, contains all files of the project
