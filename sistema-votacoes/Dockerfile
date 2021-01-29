FROM azul/zulu-openjdk-alpine:11.0.7

COPY build/libs/*.jar app.jar
COPY etc/opentelemetry-javaagent-all.jar opentelemetry-javaagent-all.jar

CMD ["java", "-javaagent:opentelemetry-javaagent-all.jar", "-XX:MaxRAMPercentage=80.0", "-Dfile.encoding=UTF8", "-Duser.country=BR", "-Duser.language=pt", "-Duser.timezone=America/Sao_Paulo", "-Dotel.exporter=jaeger", "-Dotel.exporter.jaeger.service.name=sistema-votacoes", "-Dotel.exporter.jaeger.endpoint=jaeger:14250", "-jar", "app.jar"]
