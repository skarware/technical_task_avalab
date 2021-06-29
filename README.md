# technical_task_avalab
Yet another technical task for a dream job

## About the project

##### Simple REST-alike API for ocr data creation in database an caching in Redis.

One of job tasks I got while looking for my second software developer position.\
Application made using <i><b>Java 11, Spring Boot v2.5.1, Spring Web, Spring Data JPA, Spring Data Redis, H2-in-memory-database, Hibernate, Redis, Swagger, Spock + Groovy, Flyway, Lombok and Maven, ...</b></i>.
 
Made a decent effort to write clean OOP code to my Date.now() best understanding.

## How to set up the application

Open terminal and use git clone command to download the remote GitHub repository to your computer:
```
git clone https://github.com/skarware/technical_task_avalab.git
```
It will create a new folder with same name as GitHub repository "technical_task_avalab". All the project files and git data will be cloned into it. After cloning complete change directories into that new folder:
```
cd technical_task_avalab
```

Give a `mvnw` file execution permission or you will see error: `./mvnw: command not found`
```
chmod +x mvnw
```

## How to use

To launch the application run this command (uses maven wrapper):
```
./mvnw clean spring-boot:run
```
Or using your installed maven version:
```
mvn clean spring-boot:run
```
<b>For interacting with API one can use <b><i>Swagger</i></b> on http://localhost:8080/swagger-ui/
