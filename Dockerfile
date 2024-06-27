FROM maven:3.9.7-sapmachine-17 AS build
WORKDIR /app
COPY X-mysql/settings.xml /root/.m2/settings.xml
COPY . .
# 使用 Maven 解析所有依赖，利用 Docker 缓存优化
#RUN mvn dependency:go-offline
RUN mvn clean package -DskipTests
RUN ls -l /app/service/service-test/service-prod/*

FROM openjdk:17
WORKDIR /app
COPY --from=build /app/service/service-test/service-prod/target/service-prod-1.0-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]
