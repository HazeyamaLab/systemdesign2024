ARG PROJECT_NAME=systemdesign2024

# 1. ビルドコンテナ
FROM eclipse-temurin:21.0.5_11-jdk-alpine as builder
WORKDIR /workdir

# Gradleの実行に必要なものだけコピー
COPY ./gradlew .
COPY ./gradle ./gradle
COPY ./build.gradle .
COPY ./settings.gradle .

# 依存関係をインストールさせるためのビルド
RUN ./gradlew war

# ソースコードをコピー
COPY ./src ./src

# .warファイルをビルド
RUN ./gradlew war

# 2. プロダクション用のコンテナ
FROM tomcat:11.0.2-jdk21-temurin-noble
ARG PROJECT_NAME
EXPOSE 8080

# .warをコピー
COPY --from=builder /workdir/build/libs/${PROJECT_NAME}.war /usr/local/tomcat/webapps
