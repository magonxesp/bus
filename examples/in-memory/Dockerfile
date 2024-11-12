FROM eclipse-temurin:21 AS build

WORKDIR /builder

COPY . .

RUN --mount=type=cache,target=/root/.gradle,sharing=private ./gradlew :examples:in-memory:buildFatJar

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /builder/examples/in-memory/build/libs/in-memory-all.jar /opt/in-memory-all.jar

EXPOSE 8080
EXPOSE 5005

CMD ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "/opt/in-memory-all.jar"]
