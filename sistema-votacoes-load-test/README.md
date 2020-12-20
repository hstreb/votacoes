# Projeto de teste de performance do sistema de votações

O teste de performance está configurado com um cenário para aumentar até 1000 execuções simultâneas em 5 minutos das seguintes requisições:

- 1 criação de uma votação 
- 1 abertura de uma votação
- enquanto a votação estiver aberta (1 minuto) vai realizando requisições para votar, usando uma lista de 1000 CPFs (gerados em https://www.4devs.com.br/ferramentas_online.php)

> OBS: Caso seja necessário testar mais chamadas de votos para uma votação é preciso aumentar o número de cpfs e o tempo de uma votação.

## Dependências

- [gradle](http://gradle.org/)
- [gatling](http://gatling.io/)

## Rodar

- Estar com o sistema-votacoes rodando, segue a [documentação](../sistema-votacoes/README.md)

- Rodar local
```shell
./gradlew clean gatlingRun-VotacaoSimulacao
```

- Rodar em outro ambiente
```shell
./gradlew clean gatlingRun-VotacaoSimulacao -D VOTACAO_URL=<URL do servidor>
```
