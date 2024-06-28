FROM maven:3.9.7-sapmachine-17 AS build
WORKDIR /app
# 首先单独复制pom.xml
COPY X-mysql/settings.xml /root/.m2/settings.xml
COPY pom.xml .
COPY common/pom.xml common/
COPY common/common-mybatis/pom.xml common/common-mybatis/
COPY common/common-redis/pom.xml common/common-redis/
COPY common/common-tools/pom.xml common/common-tools/
COPY service/pom.xml service/
COPY service/service-auth/pom.xml service/service-auth/
COPY service/service-gateway/pom.xml service/service-gateway/
COPY service/service-test/pom.xml service/service-test/
COPY service/service-test/service-consumer/pom.xml service/service-test/service-consumer/
COPY service/service-test/service-prod/pom.xml service/service-test/service-prod/
# 使用 Maven 下载所有依赖，利用 Docker 缓存优化 后续构建将不会重复下载maven依赖 若前面pom有更新 将重新下载
RUN mvn dependency:go-offline
COPY . .
RUN mvn clean package -DskipTests
RUN ls -l /app/service/service-test/service-prod/*

FROM openjdk:17
WORKDIR /app
COPY --from=build /app/service/service-test/service-prod/target/service-prod-1.0-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]