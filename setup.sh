echo "# construir a aplicação"

./gradlew clean build

echo "# construir a imagem docker"

docker build -t hstreb/sistema-votacoes:0.0.1 .

