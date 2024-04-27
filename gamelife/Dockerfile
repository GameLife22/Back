FROM maven:3.9.6-eclipse-temurin-21 as maven_build
WORKDIR /gamelife
ARG USE_MAVEN_PROFILE=${USE_MAVEN_PROFILE}
COPY pom.xml .
COPY src ./src
RUN --mount=type=cache,target=/root/.m2  mvn -Dmaven.test.skip=true package
RUN mkdir -p target/docker-packaging && cd target/docker-packaging && jar -xf ../gamelife*.war

FROM eclipse-temurin:latest
WORKDIR /gamelife
ARG DOCKER_PACKAGING_DIR=/gamelife/target/docker-packaging
COPY --from=maven_build ${DOCKER_PACKAGING_DIR}/WEB-INF/lib /gamelife/lib
COPY --from=maven_build ${DOCKER_PACKAGING_DIR}/WEB-INF/classes /gamelife/classes
COPY --from=maven_build ${DOCKER_PACKAGING_DIR}/META-INF /gamelife/META-INF
CMD java -cp .:classes:lib/* \
         -Dspring.application.json=${SPRING_APPLICATION_JSON} \
         -Djava.security.egd=file:/dev/./urandom \
         fr.sqli.formation.gamelife.GamelifeApplication