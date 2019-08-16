# Anagram Api

A Spring boot web api for serving anagrams of english words

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

You will need to have [Java](https://www.oracle.com/technetwork/java/javase/downloads/jdk12-downloads-5295953.html) Installed. 

```

```

### Installing

The steps to get this system up and running are to call gradlew build,

```
gradlew build
```

And repeat

```
until finished
```

End with an example of getting some data out of the system or using it for a little demo

## Running the tests


###Prerequisites

[Ruby](https://ruby-doc.org/)


Tests for this system were provided in ruby scripts. You can execute them with the command 
```ruby anagram_test.rb```

### Break down into end to end tests

These test test the endpoints response status as well as functionality of the web api

```
>ruby anagram_test.rb
Loaded suite anagram_test
Started
.......
Finished in 0.527031 seconds.
------------------------------------------------------------------------------------------------------------------------
7 tests, 20 assertions, 0 failures, 0 errors, 0 pendings, 0 omissions, 0 notifications
100% passed
------------------------------------------------------------------------------------------------------------------------
13.28 tests/s, 37.95 assertions/s
```

## Deployment

A [Heroku](https://heroku.com) CI/CD pipeline is watching, building and deploying off PR into the master branch at
https://anagram-api-chris.herokuapp.com/

## Built With

* [Spring Boot](https://spring.io/) - The web framework used
* [Gradle](https://gradle.org/) - Dependency Management

## Authors

* **Chris Abedi** - *Initial work* - [chrisabedi](https://github.com/chrisabedi)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone whose code was used
* Inspiration
* etc
