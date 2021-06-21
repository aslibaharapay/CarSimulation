# Alpine Linux with OpenJDK JRE
FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine
# Copy  file
COPY car.simulator-0.0.1-SNAPSHOT.jar /car-simulator.war

CMD ["/usr/bin/java", "-jar", "-Dhttps.protocols=SSLv3,TLSv1.2 -Dfile.encoding=UTF-8 -Dserver.port=8080","/car-simulator.war"]
