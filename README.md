# course-catalog

A course catalog API created using Kotlin, Spring Boot and PostgresDB.

### Points to note

1. This was created for me to learn Kotlin, Spring Boot and PostgresDB.
2. This includes two controllers, one for courses and one for instructors.
3. An instrcutor can create many courses.
4. The file curl-commands.txt has a list of commands that can be used to test the app once it is up and running.
5. A dummy Greetings controller was created which is not relevant to the course-controller.
6. Initially H2 was used instead of PostgresDB, so its configurations can also be seen commented in the code.
7. To integration tests with PostgresDB, TestContainer has been used. FYI TestContainer requires docker.
8. If your system uses Colima for Docker, then add the following variables to the session before executing the integration tests:
  ```
    export TESTCONTAINERS_DOCKER_SOCKET_OVERRIDE=/var/run/docker.sock
    export DOCKER_HOST="unix://${HOME}/.colima/docker.sock"
  ```
9. To run the application:
   1. Start colima
   2. Start docker-compose
   3. Run ./gradlew bootRun
   4. Get the Auth0 access token by running the following:
   ```
   curl --request POST \
   --url https://dev-5mhvvrxyp13mmhvq.us.auth0.com/oauth/token \
   --header 'content-type: application/json' \
   --data 
    '{
      "client_id":"sZV0he2De2bRFndaJu3bznAsiaKy8GsS",
      "client_secret":"A3NbjkoqOWF6Ogc6jULvV3gF_pQqD9_pZd6eXA9NdYx5MR1FYuogImCXvRGvOOdA",
      "audience":"http://coursecatalog.demo",
      "grant_type":"client_credentials"
    }'
   ```
   5. Use the access token in the header of the requests sent.