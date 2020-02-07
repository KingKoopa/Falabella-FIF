FROM openjdk:8-jdk-alpine
ADD target/beermaster-0.0.1-SNAPSHOT.jar beer.jar
ENTRYPOINT ["java", "-jar", "beer.jar"]
