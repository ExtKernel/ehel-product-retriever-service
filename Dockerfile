FROM amazoncorretto:21
LABEL authors="exkernel"

WORKDIR /app

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} /app/app.jar

EXPOSE 8000

CMD ["java", "-jar", "/app/app.jar"]