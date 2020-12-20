# Sistema de votações

Aplicação java responsável pela criação, atualização de uma votação e a contagem de votos.

## Criação e Execução

- dependências:
  - [java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
  - [docker](https://docs.docker.com/)
  - [docker-compose](https://docs.docker.com/compose/)
  - [curl](https://curl.se/)
  - [jq](https://stedolan.github.io/jq/)

- construir o projeto:

```shell
./gradlew clean build
```

- construir a imagem docker:

```shell
docker build -t hstreb/sistema-votacoes:0.0.1 .
```

- executar a aplicação

```shell
docker-compose up -d
```

## Chamadas de exemplo

- criar uma pauta, abrir a votação, executar 1000 votos com cpfs aleatórios e encerrar a votação
```shell
ID=$(curl --header "Content-Type: application/json" --request POST --data '{"pauta":"pauta teste"}' http://localhost:8080/v1/votacoes | jq -r '.id')
curl --header "Content-Type: application/json" --request PATCH --data '{"estado":"EM_ANDAMENTO","duracao":1}' http://localhost:8080/v1/votacoes/$ID
for i in {1..1000}; do \
    CPF=$(curl 'https://www.4devs.com.br/ferramentas_online.php' --data-raw 'acao=gerar_cpf&pontuacao=N');
    curl -i --header "Content-Type: application/json" --request POST --data '{"associado":"'$CPF'","escolha":"SIM"}' http://localhost:8080/v1/votacoes/$ID/votos;
done
curl -i --header "Content-Type: application/json" --request PATCH --data '{"estado":"ENCERRADA"}' http://localhost:8080/v1/votacoes/$ID
```

- acompanhar os eventos gerados no kafka executar em terminais diferentes

```shell
kafka-console-consumer --topic votacoes --bootstrap-server localhost:9092
```

```shell
kafka-console-consumer --topic votos --bootstrap-server localhost:9092
```
