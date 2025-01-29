FROM maven:3.9.4-eclipse-temurin-21 as build
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build ./build/target/*.jar ./app.jar
ENTRYPOINT java -jar app.jar