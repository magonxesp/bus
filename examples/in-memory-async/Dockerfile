FROM eclipse-temurin:21 AS build

WORKDIR /builder

COPY . .

RUN --mount=type=cache,target=/root/.gradle,sharing=private ./gradlew :examples:in-memory-async:buildFatJar

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /builder/examples/in-memory-async/build/libs/in-memory-async-all.jar /opt/in-memory-async-all.jar

EXPOSE 8080

CMD ["java", "-jar", "/opt/in-memory-async-all.jar"]
