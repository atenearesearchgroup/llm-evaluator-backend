# syntax=docker/dockerfile:experimental
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /workspace/app

COPY . /workspace/app
RUN --mount=type=cache,target=/root/.gradle ./gradlew clean build
RUN mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*-SNAPSHOT.jar)

FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/build/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
#COPY --from=build /workspace/app/docker-env.sh /app
#ARG REPLICATE_TOKEN
#ENV REPLICATE_TOKEN=$REPLICATE_TOKEN
#ENV REPLICATE_TOKEN=r8_LFfOsUnQt6QefErRCBnXr4KTVTAnDIW0yU1Tn
#ENV DATASOURCE_URL="jdbc:mysql://hermesanalyzer-backend-mysql-1:3306/mydatabase"
#ENV DATASOURCE_USERNAME=root
#ENV DATASOURCE_PASSWORD=verysecret
#RUN sh app/docker-env.sh
EXPOSE 8080
ENTRYPOINT ["java","-cp","app:app/lib/*","me/loopbreak/hermesanalyzer/HermesApplication"]