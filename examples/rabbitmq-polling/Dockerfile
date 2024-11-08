FROM eclipse-temurin:21 AS build

WORKDIR /builder

COPY . .

RUN --mount=type=cache,target=/root/.gradle,sharing=private ./gradlew :examples:rabbitmq-polling:buildFatJar

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /builder/examples/rabbitmq-polling/build/libs/rabbitmq-polling-all.jar /opt/rabbitmq-polling-all.jar

EXPOSE 8080

CMD ["java", "-jar", "/opt/rabbitmq-polling-all.jar"]
