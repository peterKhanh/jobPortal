# Importing JDK and copying required files
FROM openjdk:19-jdk AS build
WORKDIR /app
COPY pom.xml .
COPY src src

# Copy Maven wrapper
COPY mvnw .
COPY .mvn .mvn

# Set execution permission for the Maven wrapper
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests  

# Stage 2: Create the final Docker image using OpenJDK 17
FROM openjdk:17-jdk
VOLUME /tmp
####
# Copy the JAR from the build stage
COPY --from=build /app/target/JobFinder.jar JobFinder.jar
ENTRYPOINT ["java","-jar","/JobFinder.jar"]
EXPOSE 8080
