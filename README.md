# digicert-user-management

# The project project can be run on both local environments using docker-compose

To Run The unit tests and the application, please clone the repository to your local machine.
After cloning the repository, the unit test commands can be run using Maven, and to run the project Docker will be required.

The project was tested using Maven version 3.9.6 and Docker version 4.27.1

To be able to run project unit tests, go to the location where the project was cloned, under the project root directory:
Run:  
## mvn test

To run the application (docker required), go to the location where the project was cloned, under the project root directory:
Run:  
## docker-compose up

Please take note that both port numbers 8080 and 3306 are used for Application API and Mysql respectively
The MySQL docker image that was used comes with a super user root and password of password, so to run the project successfully, it was a requirement from the instructions to use those credentials for the application API, and if those credentials are used the mySQL container breaks or the application is not able to connect to the database.

To run the application without the abovementioned issues, both MYSQL_USER and MYSQL_PASSWORD on the docker-compose.yml must be updated to different credentials.
Once all the containers are running, the URL below can be used to access the API"

http://localhost:8080/api/v1/users

Payload: {
    "id": 2,
    "firstName": "Thabo",
    "lastName": "Pebane",
    "email": "thabo@gmail.com"
}

# The API supports, 
- listing of all users
- Get user by Id
- update user
- Add new user
- Delete user by Id
- 
- 



