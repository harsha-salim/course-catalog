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
