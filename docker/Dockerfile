# Alpine Linux with OpenJDK JRE
FROM adoptopenjdk/openjdk11:jre-11.0.11_9-alpine
# Copy  file
COPY car.simulator-0.0.1-SNAPSHOT.jar /car-simulator.war

CMD ["java", "-jar","/car-simulator.war"]
