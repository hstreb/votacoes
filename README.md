# Sistema de votações

## Objetivo 

No cooperativismo, cada associado possui um voto e as decisões são tomadas em assembleias,  por votação. Imagine que você deve criar uma solução backend para gerenciar essas sessões de  votação. 
Essa solução deve ser executada na nuvem e promover as seguintes funcionalidades através de  uma API REST:  
- Cadastrar uma nova votacao 
- Abrir uma sessão de votação em uma votacao (a sessão de votação deve ficar aberta por um  tempo determinado na chamada de abertura ou 1 minuto por default) 
- Receber votos dos associados em pautas (os votos são apenas 'Sim'/'Não'. Cada associado  é identificado por um id único e pode votar apenas uma vez por votacao) 
- Contabilizar os votos e dar o resultado da votação na votacao 
Para fins de exercício, a segurança das interfaces pode ser abstraída e qualquer chamada para as  interfaces pode ser considerada como autorizada. A escolha da linguagem, frameworks e  bibliotecas é livre (desde que não infrinja direitos de uso). 
É importante que as pautas e os votos sejam persistidos e que não sejam perdidos com o restart  da aplicação. 

## Tarefas bônus 
- Tarefa Bônus 1 - Integração com sistemas externos 
  - Integrar com um sistema que verifique, a partir do CPF do associado, se ele pode  votar 
    - Caso o CPF seja inválido, a API retornará o HTTP Status 404 (Not found).  Você pode usar geradores de CPF para gerar CPFs válidos 
    - Caso o CPF seja válido, a API retornará se o usuário pode (ABLE_TO_VOTE)  ou não pode (UNABLE_TO_VOTE) executar a operação 
    - GET https://user-info.herokuapp.com/users/{cpf} 
```sh
curl https://user-info.herokuapp.com/users/19839091069
{"status":"UNABLE_TO_VOTE"}
```
- Tarefa Bônus 2 - Mensageria e filas 
  - O resultado da votação precisa ser informado para o restante da plataforma, isso  deve ser feito preferencialmente através de mensageria. Quando a sessão de  votação fechar, poste uma mensagem com o resultado da votação 
- Tarefa Bônus 3 - Performance 
  - Imagine que sua aplicação possa ser usada em cenários que existam centenas de  milhares de votos. Ela deve se comportar de maneira performática nesses cenários ○ Testes de performance são uma boa maneira de garantir e observar como sua  aplicação se comporta 
- Tarefa Bônus 4 - Versionamento da API 
  - Como você versionaria a API da sua aplicação? Que estratégia usar? 

## O que será analisado 
- Simplicidade no design da solução (evitar over engineering) 
- Organização do código 
- Arquitetura do projeto 
- Boas práticas de programação (manutenibilidade, legibilidade etc) 
- Possíveis bugs 
- Tratamento de erros e exceções 
- Explicação breve do porquê das escolhas tomadas durante o desenvolvimento da solução 
- Uso de testes automatizados e ferramentas de qualidade 
- Limpeza do código 
- Documentação do código e da API 
- Logs da aplicação 
- Mensagens e organização dos commits 

## Dicas 
- Teste bem sua solução, evite bugs 

## Observações importantes 
- Não inicie o teste sem sanar todas as dúvidas 
- Iremos executar a aplicação para testá-la, cuide com qualquer dependência externa e deixe  claro caso haja instruções especiais para execução do mesmo

## Solução

### Decisões

- **Java 11** é a última versão de suporte longo (LTS)
- **Gradle** maior velocidade para baixar as dependências, simplicidade da DSL em groovy vs XML do Maven
- **Spring Boot** é um dos frameworks mais utilizado para criação de microservices atualmente
- **PostgreSQL** é open source, maduro e com grande utilização
- **Kafka** é uma das ferramentas mais utilizadas para mensageria e big data
- **Docker** é uma das formas mais simples para rodar as dependências localmente, além de gerar uma facilidade de deploy em gerenciadores de container
- **SpotBugs** ferramenta de análise estática de código leve que roda no build do gradle

### Exemplo de endpoints

#### Criar votacao
```
POST /v1/votacoes
{
    "pauta": "",
    "duracao": 1
}
```

#### Abrir sessão de votação
```
PATCH /v1/votacoes/{votacao}
{
    "estado": "EM_VOTACAO",
    "duracao": 10
}
```

#### Votar
```
POST /v1/votacoes/{votacao}/votos
{
    "id": "4cabf124-8d2e-4c9f-bc47-40ba12327b28",
    "voto": "SIM"
}
```

#### Contabilizar votos

```
PATCH /v1/votacoes/{votacao}
{
    "estado": "FINALIZADA"
}
resposta:
{
    "id": "51a55b51-8a1c-4417-9f69-ec636bf1fb12",
    "descricao": "",
    "estado": "FINALIZADA",
    "duracao": 1,
    "resultado": {
        "sim": 120,
        "nao": 20,
        "total": 140
    }
}
```

curl --header "Content-Type: application/json" \
--request POST \
--data '{"pauta":"pauta teste"}' \
http://localhost:8080/v1/votacoes


curl --header "Content-Type: application/json" \
--request PATCH \
--data '{"estado":"EM_ANDAMENTO","duracao":1}' \
http://localhost:8080/v1/votacoes/de5908a4-d649-4d98-aa2f-ba9ab26d02f8

curl --header "Content-Type: application/json" \
--request POST \
--data '{"associado":"19839091069","escolha":"SIM"}' \
http://localhost:8080/v1/votacoes/de5908a4-d649-4d98-aa2f-ba9ab26d02f8/votos
