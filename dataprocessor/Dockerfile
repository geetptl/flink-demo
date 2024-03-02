FROM maven:3.9.6-eclipse-temurin-17 AS builder
LABEL authors="geet"

COPY ./core/src /opt/core/src
COPY ./core/pom.xml /opt/core/pom.xml

RUN cd /opt/core && mvn clean install

COPY ./dataprocessor/src /opt/dataprocessor/src
COPY ./dataprocessor/pom.xml /opt/dataprocessor/pom.xml

RUN cd /opt/dataprocessor && mvn clean verify

FROM eclipse-temurin:17 AS runner
LABEL authors="geet"

ADD https://dlcdn.apache.org/flink/flink-1.18.1/flink-1.18.1-bin-scala_2.12.tgz /opt/flink-1.18.1.tgz

RUN tar -xvzf /opt/flink-1.18.1.tgz -C /opt/

COPY --from=builder /opt/dataprocessor/target/dataprocessor-*.jar /opt/dataprocessor.jar

COPY ./dataprocessor/docker-entrypoint.sh /

RUN chmod +x /docker-entrypoint.sh

CMD ["/docker-entrypoint.sh"]