# Requirements

## The problem – Video rental store
For a video rental store we want to create a system for managing the rental
administration. ​ We want three primary functions.

1. Have an inventory of films
   I just got the Top 50 from imdb.com and transformed the html to a json file.
   I did the most simple thing and just made up the film type (New Release, Regular, Old Film)
   based on the approximate release date of the film.  It gives some films of each type
   for testing so does the job.

2. Calculate the price for rentals.
   The ReST GET endpoint calculates everything you might want to see when
   requesting the price before renting videos:
   - The price
   - The bonus points
   - The videos being rented.
   This is a GET because it doesn't change any data and is idempotent.  This means that it
   is not normal to use a json request payload as HTTP GET doesn't normally do
   this and parameters are request parameters.  I decided to encode the list
   of video ids as a comma-separated-list as I have seen many large public facing ReST
   apis to this.  ReST nomenclature evangelists may have a different idea
   on the best way to do this.  It may cause trouble with some clients
   as the comma may need to be URL-encoded.
   As ReST services should work on a resource, the resource in this case
   is the RentVideosPrice, which contains the information mentioned above.

3. Keep track of the customers “bonus” points
   As there is a requirement of the api that it can process renting videos,
   I made a request to rent videos update the customer's points.


# Implementalion
I used DropWizard as recommended to create ReST webservices - obviously using json.

# Testing
I used integration tests for testing the java api and added integration tests
to test http calls.  For POST requests, I put standard json request payloads
in files.  I would add more coverage if I had more time.  Also, with more
time I would have added cucumber features rather than java integration
tests.

# Running the integration tests

```bash
gradle integrationTest

OR

./gradlew integrationTest
```


# Running the tests

```bash
gradle test

OR

./gradlew test
```

# Running the Program

```bash
gradle run

OR

./gradlew run
```

# Example Queries

In order to run these example queries, please run the web container first
using `gradle run` from the project root.

## Get the price to rent a video
```bash
curl -X GET 'http://localhost:8080/videoPrice?videoIds=1%2C2%2C3&dayCount=4'
```

## Rent videos(s)
```bash
curl -X POST \
  http://localhost:8080/customers/1 \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 8cc10e54-020d-509d-c0e8-bbc45d124ba6' \
  -d '{
    "date" : "2017-05-24",
    "rentalDays" : 4,
    "videoIds" : [1,2,3]
}'
```


## Get the current rented videos and bonus points of a customer to check renting
```bash
curl -X GET  'http://localhost:8080/customers/1'
```

# Out of scope
- I didn't implement any functionality to calculate surcharge as it wasn't in
 'We want three primary functions.', also I was running a bit short on
 time.
- I didn't put any checks to see if videos are rented to someone else or to
  the customer trying to rent them.  This could be done in version 1.1.
- The system is not transactional - there are lots of ways of ensuring this
  depending on the storage system used e.g. ACID in the case of rdbms
  and eventually consistent in the case of Cassandra (timestamp based)