FROM azul/zulu-openjdk-alpine:11.0.7

COPY build/libs/*.jar app.jar

CMD ["java", "-XX:MaxRAMPercentage=80.0", "-Dfile.encoding=UTF8", "-Duser.country=BR", "-Duser.language=pt", "-Duser.timezone=America/Sao_Paulo", "-jar", "app.jar"]
