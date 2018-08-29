#### CAMPSITE MANAGER APPLICATION
An application for reservations for a campsite. It allows users for creating reservations however there are some rules:
* A reservation can be made minimum 1 day and maximum 30 days before the arrival date
* A reservation can only be for 3 days
* Overlapping dates are not allowed

##### Reservation API
It is a CRUD API that manages reservations.

###### Create Reservation
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

###### GET Reservation
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

###### Update Reservation
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

###### Delete Reservation
* URL: /reservations/{reservationId}
* Method: DELETE
* Response Status: 204 No Content

##### Campsite API

###### Get Campsite Availability
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

##### Setup
```
mvn package
```

##### Running
```
cd target
java -jar campsite-manager-0.0.1-SNAPSHOT.jar
```