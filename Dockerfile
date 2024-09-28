# 1. 기본 JDK 이미지 사용
FROM openjdk:17-jdk-slim

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. .env 파일을 복사 (필요할 경우)
COPY .env /app/.env

# 4. 애플리케이션 JAR 파일 복사
# ARG로 JAR 파일 경로를 설정 (빌드 후 jar 위치)
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# 5. 환경 변수와 함께 Java 애플리케이션 실행
ENTRYPOINT ["java","-jar","/app/app.jar"]