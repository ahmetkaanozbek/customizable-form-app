It is a customizable form application which a person can set type of the fields of the form from the limited number of types. Also a person who will fill the form should fill it with the determined type (determined by the user who created the form) to not to cause an error. 

The application uses MongoDB as database and MongoDB Atlas to save data in the cloud. As web server, application uses embedded Apache Tomcat Server.

For the security part, application uses Spring Security. I configured my own security configuration which is different from the Spring’s default security settings (Form Login authentication etc.). A user can authenticate herself/himself by providing her/his credentials (username, password). After providing them through specific endpoint `“/api/auth/login”`, a JWT token will be returned by the server as response for the further authentications. Besides response endpoints `“/api/form/response/**”` and login and register endpoints `“/api/auth/**”`, all endpoints require authentication. Also, added a simple role-based access mechanism to protect user deletions from the database which means only a user who has the role of: `“ROLE_ADMIN”` can delete users from the database. In addition to that, for endpoints which includes path variables (IDs), I prevented users to not make any unauthorized actions such as deleting other user’s forms or fields by matching their identities provided by `SecurityContextHolder` with the associated ID’s in the database. For example, when deleting a form, that form’s manual reference to the user, which is the `userId` in database, should match with the authenticated `userId` extracted from `SecurityContextHolder`. Also, users’s passwords are held encrypted (with BCrypt) in database.

To test the functionality of the program, I wrote some unit and integration tests. However, for now, these tests are only testing `FieldController`, `FieldRepository` and `FieldService` classes’s methods. I have used Mockito, JUnit 5, and AssertJ to create my tests. Also, TestContainers are used to test customized Query Methods which are provided by `MongoRepository`.

To apply to Single Responsibility principle, I created different packages for different layers such as controller, service, repository, model, exception etc.

Also, added `GlobalExceptionHandler` class along with `@RestControllerAdvice` annotation to return more specific and informative responses. When any custom exception (which is defined in `exception` package) happens, response to the client will be informative about what caused to error along with a suitable HTTP Status Code. Furthermore, I have used Slf4j to inform server about what happened when custom exceptions are thrown.

There is also a Dockerfile to containerize the whole application. 

The program is still under development. Therefore, I will add more features, more field types etc. in the future.

If you encounter any issues or have suggestions or want to contribute code, I would be very happy. Thank you for your interest in my project, and I appreciate your support!
