import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class VotacaoSimulacao extends Simulation {

  val baseUrl = System.getProperty("VOTACAO_URL", "http://localhost:8080")

  val protocolHttp = http
    .baseUrl(baseUrl)
    .contentTypeHeader("application/json")
    .acceptHeader("application/json;charset=UTF-8")

  val duracaoVotacao = 1

  val cpfs = csv("cpfs.csv").eager.random

  val cenarioVotacao = scenario("Realizar uma Votacão")
    .exec(http("criar_votacao")
      .post("/v1/votacoes")
      .body(StringBody("""{"pauta": "Teste"}""".stripMargin))
      .check(status.is(201), jmesPath("id").saveAs("id")))
    .exec(http("abrir_votacao")
      .patch("/v1/votacoes/${id}")
      .body(StringBody(s"""{"estado":"EM_ANDAMENTO", "duracao":${duracaoVotacao}}""".stripMargin))
      .check(status.is(200)))
    .doWhileDuring(true, duracaoVotacao.minutes) {
      feed(cpfs)
        .exec(http("votar")
          .post("/v1/votacoes/${id}/votos")
          .body(StringBody("""{"associado":"${cpf}","escolha":"SIM"}""")))
    }
    .pause(1.second)
    .exec(http("fechar_votacao")
      .patch("/v1/votacoes/${id}")
      .body(StringBody("""{"estado":"ENCERRADA"}""".stripMargin))
      .check(status.is(200)))

  setUp(cenarioVotacao.inject(rampUsers(1000) during(5.minutes)).protocols(protocolHttp))
}
