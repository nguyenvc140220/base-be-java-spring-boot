FROM gradle:jdk17-alpine AS build
ENV HOME=/app
WORKDIR $HOME

COPY . .
RUN ["/usr/bin/gradle", "bootJar"]

#
# Package stage
#
FROM gradle:jdk17-alpine
ARG CONFIG_DIR="/opt/mkt-be"
ENV HOME=/app
WORKDIR $HOME

ENV TZ=Asia/Ho_Chi_Minh
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
COPY --from=build $HOME/mkt-api/build/libs/*.jar $HOME/app.jar
COPY ./mkt-api/src/main/resources ${CONFIG_DIR}
COPY ./mkt-common/src/main/resources/migrations/* ${CONFIG_DIR}/migrations/
ENV JVM_OPTS "-Xmx512M --spring.config.location=file://${CONFIG_DIR}/application.properties --logging.config=file://${CONFIG_DIR}/logback-spring.xml"

# Run app from jar file
ENTRYPOINT [ "sh", "-c", "java -jar app.jar ${JVM_OPTS}" ]

EXPOSE 8089

