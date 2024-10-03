FROM eclipse-temurin:21 AS build

WORKDIR /builder

COPY . .

RUN --mount=type=cache,target=/root/.gradle,sharing=private ./gradlew :examples:in-memory-bus:buildFatJar

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /builder/examples/in-memory-bus/build/libs/in-memory-bus-all.jar /opt/in-memory-bus-all.jar

EXPOSE 8080

CMD ["java", "-jar", "/opt/in-memory-bus-all.jar"]
