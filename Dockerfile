FROM public.ecr.aws/amazoncorretto/amazoncorretto:17

VOLUME /tmp

COPY build/EmPersistenceService-0.0.1-SNAPSHOT.jar /opt/app.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/opt/app.jar" ]