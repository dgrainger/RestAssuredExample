# Rest Assured Example

This is a simple example of Rest Assured and a Gradle project which uses it.

In the root directory of the project is the PowerPoint presentation named
**2020-04-20-Rest-Assured.pptx**.

The presentation will go through the basic and at the section for _Examples_
you want to come here and run through the examples.

At the time of writing this, I'm using external websites to hit the endpoints.
This means that if the websites change or they take them down, this code
might fail to work.

## The Files

The first file to look at is the **build.gradle**. The _dependencies_ section
shows how to include the necessary libraries into your Gradle project. The
dependencies are:

```
rest-assured
json-path
xml-path
json-schema-validator
junit
assertj-core
hamcrest
```

To run Rest Assured you need the _rest-assured_ dependency.<br>
When doing API testing you are going to get back JSON and XML responses.<br>
The _json-path_ and _xml-path_ dependencies will let you manipulate the
responses.<br>
Additionally, there is a _json-schema-validator_ dependency. This will let
you compare the response against a JSON schema. A good way to smoke test
that the response is in the correct format.<br>
Finally, the _junit_, _assertj-core_ and _hamcrest_ dependencies are
because we have set this up using a JUnit test framework. It is assumed
you know how JUnit and matcher libraries, like Hamcrest and AssertJ, work.

## RestAssuredExample
This is using a REST endpoint at https://official-joke-api.appspot.com.
There are a few different endpoints but the few we care about are:

* https://official-joke-api.appspot.com/jokes/programming/random
* https://official-joke-api.appspot.com/jokes/general/random
* https://official-joke-api.appspot.com/jokes/ten
* https://official-joke-api.appspot.com/random_joke

When we call these endpoints there are specific things we can test for.
For example, the _/jokes/general/random_ endpoint will have the key "type"
equal to "general". So we can check for that.

Additionally, the schema for the joke API is consistent. So I can use
schema validation to confirm the response has the correct schema. 

The way this works is:

* you create a schema to validate against
* you put the schema in the classpath for the project
* use the _matchesJsonSchemaInClasspath()_ method

If we look at the schema file:

<pre>
{
  "$schema": "http://json-schema.org/draft-04/schema#",
  "type": "array",
  "items": {
    "properties": {
      "id": {
        "type": "number"
      },
      "type": {
        "type": "string"
      },
      "setup": {
        "type": "string"
      },
      "punchline": {
        "type": "string"
      }
    }
  },
  "required": ["id", "type", "setup", "punchline"]
}
</pre> 

* First line determines the type of schema we are validating against
* Next is the type of JSON which gets returned. In this case it is an array.
* Then we have a list of the items in the response
* For us, it is a simple response and the only items are properties
* We then list the different properties and the data type for each property
* Finally, we have the required field. This is an array of all the fields which must appear.
There are 4 fields and they are all required.

## RestAssuredExample2

This is using an endpoint at https://postman-echo.com. Normally this for
testing Postman but we can also use it to test Rest Assured as well.

With this endpoint we can test GET, PUT and Basic Authentication.

## RestAssuredExample3

This is using the Telus website to show cookie manipulation and how we
can hit a second endpoint in the same class.

With the cookie manipulation we set the province of the user to AB for
Alberta. The F5 load balancer at Telus knows that I am actually in ON
for Ontario. I set the "prov" cookie to "AB" but when I ask the F5 load
balancer which province I'm in, it confirms that I am in ON.

An interesting thing, if I use a VPN to set my country to the USA then
the F5 at Telus cannot figure out which province I'm in and the test
fails because the cookie is not set.

The second test in this example simply changes the RestAssured *baseURI*
at the beginning of the test, overriding the value set in the *setUp()*
method.
