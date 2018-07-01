FROM openjdk:9-jre
MAINTAINER Jose Maria Alvarez <josemaria.alvarezfernandez@elcorteingles.es>

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/myservice/myservice.jar"]

# Add the service itself
ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/myservice/myservice.jar
