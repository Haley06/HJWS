FROM java:8
EXPOSE 8089

VOLUME /tmp
ADD fatigue.jar  /app.jar
ADD 源码/src/main/resources/application.yml  /application.yml
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar", "--spring.config.location=application.yml"]
