FROM maven:3.9.6-eclipse-temurin-17 AS builder
LABEL authors="geet"

COPY ./core/src /opt/core/src
COPY ./core/pom.xml /opt/core/pom.xml

RUN cd /opt/core && mvn clean install

COPY ./datagenerator/src /opt/datagenerator/src
COPY ./datagenerator/pom.xml /opt/datagenerator/pom.xml

RUN cd /opt/datagenerator && mvn clean verify

FROM eclipse-temurin:17 AS runner
LABEL authors="geet"

COPY --from=builder /opt/datagenerator/target/datagenerator-*.jar /opt/datagenerator.jar

COPY ./datagenerator/docker-entrypoint.sh /

RUN chmod +x /docker-entrypoint.sh

CMD ["/docker-entrypoint.sh"]