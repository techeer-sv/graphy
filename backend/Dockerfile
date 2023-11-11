FROM openjdk:11-jre-slim-buster
VOLUME /tmp

ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.9.0/wait /wait
RUN chmod +x /wait

COPY ./build/libs/backend-0.0.1-SNAPSHOT.jar app.jar
CMD /wait && java -jar /app.jar --spring.profiles.active=prod