# Rest-Api-Framework
Visa Framework Developer - Assignment

#Sample APIs Used for automation
</br><pre>
1.	GET : http://reqres.in/api/users
	Request Parameters : page=<pageNumber>
	Request Headers : contentType=application/json
	
2.	POST : http://reqres.in/api/register
	Request Body : { "email": "sydney@fife", "password": "pistol" }
	Request Headers : contentType=application/json
	
Note : for further details, please refer to : <a href="http://reqres.in/">http://reqres.in/</a>
</pre>

#Dependencies
</br><pre>
1. org.apache.httpcomponents.httpclient : 4.5.2
2. org.json.json : 20160810
3. org.codehaus.jackson.jackson-mapper-asl : 1.5.0
4. org.testng.testng : 6.10

</pre>

#Project Structure
</br><pre>

<pre>
src
|
|-->api
|	|
|	|-->config
|	|		|
|	|		|--> global.properties
|	|		|--> getUsersPage.properties
|	|		|--> postLoginCredentials.properties
|	|
|	|-->model
|			|
|			|--> Get.java
|			|--> LoginCredentials.java
|			|--> Page.java
|			|--> Token.java
|			|--> User.java
|
|-->main
	|
	|-->master
	|		|
	|		|--> ApiResponse.java
	|		|--> Constants.java
	|		|--> ServiceApi.java
	|
	|-->tests
			|
			|--> VerifyGetUsersPage.java
			|--> VerifyPostLoginCredentials.java
</pre>

#api.config
</br><pre>
This package contains the configuration properties files of 2 types:

1. global.properties : contains generic parameters for all the API calls, below is a description for these parameters:
	
	a) Test.env : Base / Environment URL for all the API calls

2. <apiName>.properties : these contain the specific parameters for that particular API, below is a description for these parameters:
	
	a) url : relative URL of the API (Note: environment/base URL is defined in the global.properties)
	b) request.method : the HTTP method pertaining to the API call
	c) request.header.<header-name> : to specify a header in the API call (these can also be added from the code by using addHeader() method of ServiceApi
	d) response.schema : name of the model class specifying the JSON schema for the API Response
	e) request.schema (applicable only to POST requests) : name of the model class specifying the JSON schema for request 
</pre>
#api.model
</br><pre>
This package defines the POJO classes, specifying the JSON schema for API requests and responses.

In order to maintain parity between POST and GET request, a generic class "Get" serves as a model for all GET requests. This class contain only one data member, which is a map of String, String type and it holds key value pairs for all the query parameters to be added to request URL.

</pre>
#main.master
</br><pre>
This package defines the core framework classes :

1. ServiceAPI : This class encapsulates an API call, it takes the name of the API specific properties file as an argument in its constructor and sets the parameters environmentURL, relativeURL, HTTPMethodType, RequestHeaders and ResponseModel. ResponseModel object is created dynamically by class loading using reflection. Following are some of the important methods it exposes:

	a) public void addRequestHeader(String key, String value) : to add a request header, key-value pair
	
	b) public boolean hit(Object requestModel) : takes a request model object and makes the api call, it updates the data member "responseModel", which can be retrieved by calling the method "getResponseModel()"
	
	c) public Object getResponseModel() : this method parses the JSON response into the response model object, for response JSON schema validation and returns it for further use.

2. ApiResponse : This class handles making GET and POST calls to the APIs (using Apache HTTP Client) and serialization/de-serialization of the requests and responses using Jackson.

3. Constants : This class defines all the constants used in the master package classes.

</pre>
#main.test
</br><pre>
This package contains all the TestNG test classes.
</pre>
