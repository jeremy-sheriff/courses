FROM gradle:7.5-jdk17 AS build
WORKDIR /app
COPY build.gradle.kts settings.gradle.kts /app/
RUN gradle dependencies --no-daemon || true
COPY . /app
RUN gradle build --no-daemon -x test

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/courses-0.0.1-SNAPSHOT.jar /app/courses.jar
EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=prod
CMD ["java", "-jar", "courses.jar"]
