# CAMPSITE MANAGER APPLICATION
An application for reservations for a campsite. It allows users for creating reservations however there are some rules:
* A reservation can be made minimum 1 day and maximum 30 days before the arrival date
* A reservation can only be for 3 days
* Overlapping dates are not allowed

## Reservation API
It is a CRUD API that manages reservations.

### Create Reservation
* URL: /reservations
* Method: PUT
* Request Payload (All fields are required):
```
{
	"fullName": "John Doe",
	"email": "johndoe@gmail.com",
	"arrivalDate": "2018-09-11",
	"departureDate": "2018-09-14"
}
```
* Response Status: 201 Created
* Response Payload: **Returns the path to the reservation in Location header**

**Curl Example**
```
curl -X PUT --data '{"fullName": "Alex Ferguson", "email": "alex@gmail.com", "arrivalDate":"2018-09-17", "departureDate": "2018-09-20"}' localhost:8080/reservations -i -H "Content-Type: application/json"
```
```
HTTP/1.1 201
Location: /reservations/0983b3f6-9d93-4c2a-a981-257310d8d420
Content-Length: 0
Date: Wed, 29 Aug 2018 03:50:15 GMT
```

### GET Reservation
* URL: /reservations/{reservationId}
* Method: GET
* Response Status: 200 OK
* Response Payload:
```
{
    "email": "johndoe@gmail.com",
    "fullName": "John Doe",
    "arrivalDate": "2018-09-11",
    "departureDate": "2018-09-14",
    "duration": 3,
    "id": "73456da8-e0a9-4916-819b-4c841191ca02"
}
```

**Curl Example**
```
curl localhost:8080/reservations/0983b3f6-9d93-4c2a-a981-257310d8d420
```
```
{"email":"alex@gmail.com","fullName":"Alex Ferguson","arrivalDate":"2018-09-17","departureDate":"2018-09-20","duration":3,"id":"0983b3f6-9d93-4c2a-a981-257310d8d420"}%
```

### Update Reservation
* URL: /reservations/{reservationId}
* Method: POST
* Request Payload (All fields are required):
```
{
	"fullName": "John Ferguson",
	"email": "johndoe@gmail.com",
	"arrivalDate": "2018-09-11",
	"departureDate": "2018-09-14"
}
```
* Response Status: 204 No Content

**Curl Example**
```
curl -X POST --data '{"fullName": "Alex Ferguson", "email": "alex@gmail.com", "arrivalDate":"2018-09-18", "departureDate": "2018-09-20"}' localhost:8080/reservations/0983b3f6-9d93-4c2a-a981-257310d8d420 -i -H "Content-Type: application/json"

```
```
HTTP/1.1 204
Date: Wed, 29 Aug 2018 03:55:59 GMT
```

### Delete Reservation
* URL: /reservations/{reservationId}
* Method: DELETE
* Response Status: 204 No Content

**Curl Example**
```
curl DELETE localhost:8080/reservations/0983b3f6-9d93-4c2a-a981-257310d8d420 -i

```
```
HTTP/1.1 204
Date
```

## Campsite API

### Get Campsite Availability
* URL: /campsite
* Method: GET
* Response Status: 200 OK
* Response Payload:
```
{
    "availableDays": 27,
    "unavailableDates": [
        "(2018-09-11,2018-09-14)"
    ],
    "available": true
}
```

**Curl Example**
```
curl localhost:8080/campsite -i
```
```
HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Wed, 29 Aug 2018 03:58:55 GMT

{"availableDays":22,"unavailableDates":["(2018-09-11,2018-09-14)","(2018-09-20,2018-09-22)","(2018-09-17,2018-09-20)"],"available":true}%
```

## Setup
```
mvn package
```

## Running
```
cd target
java -jar -Dspring.profiles.active=dev-h2 campsite-manager-0.0.1-SNAPSHOT.jar
```

Alternatively, you can have mysql running in localhost:3306 with a database named `campsitemanager`. The default spring profile targets a mysql instance.
```
java -jar campsite-manager-0.0.1-SNAPSHOT.jar
```

