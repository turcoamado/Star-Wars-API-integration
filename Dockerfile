# Stage 1: build with Maven + JDK 21
FROM maven:3.9.4-eclipse-temurin-21 as build
WORKDIR /workspace

# copy maven files first for better cache
COPY pom.xml .
# If exists settings.xml o .m2 options
RUN mvn -B -e -q dependency:go-offline

COPY src ./src
RUN mvn -B -e -DskipTests clean package

# Stage 2: runtime image
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the generated jar
COPY --from=build /workspace/target/*.jar app.jar

# Default port
EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]