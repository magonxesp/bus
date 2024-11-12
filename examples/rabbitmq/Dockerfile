FROM eclipse-temurin:21 AS build

WORKDIR /builder

COPY . .

RUN --mount=type=cache,target=/root/.gradle,sharing=private ./gradlew :examples:rabbitmq:buildFatJar

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /builder/examples/rabbitmq/build/libs/rabbitmq-all.jar /opt/rabbitmq-all.jar

EXPOSE 8080
EXPOSE 5005

CMD ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "/opt/rabbitmq-all.jar"]
