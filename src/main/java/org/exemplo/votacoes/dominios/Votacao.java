package org.exemplo.votacoes.dominios;

import org.exemplo.votacoes.dominios.exception.VotacaoException;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static java.time.LocalDateTime.now;
import static org.exemplo.votacoes.dominios.EstadoVotacao.EM_ANDAMENTO;
import static org.exemplo.votacoes.dominios.EstadoVotacao.NAO_INICIADA;

public class Votacao {

    private static final long DURACAO_UM_MINUTO = 1L;

    private final String id;
    private final String pauta;
    private final EstadoVotacao estado;
    private final Long duracao;
    private final Resultado resultado;
    private final LocalDateTime horarioCriacao;
    private final LocalDateTime horarioInicio;
    private final LocalDateTime horarioFim;

    public Votacao(String pauta) {
        this.id = UUID.randomUUID().toString();
        this.pauta = pauta;
        this.estado = NAO_INICIADA;
        this.duracao = DURACAO_UM_MINUTO;
        this.resultado = new Resultado();
        this.horarioCriacao = now();
        this.horarioInicio = null;
        this.horarioFim = null;
    }

    public Votacao(String id,
                   String pauta,
                   EstadoVotacao estado,
                   Long duracao,
                   Resultado resultado,
                   LocalDateTime horarioCriacao,
                   LocalDateTime horarioInicio,
                   LocalDateTime horarioFim) {
        this.id = id;
        this.pauta = pauta;
        this.estado = estado;
        this.duracao = duracao;
        this.resultado = resultado;
        this.horarioCriacao = horarioCriacao;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
    }

    public String getId() {
        return id;
    }

    public String getPauta() {
        return pauta;
    }

    public EstadoVotacao getEstado() {
        return estado;
    }

    public Long getDuracao() {
        return duracao;
    }

    public Resultado getResultado() {
        return resultado;
    }

    public LocalDateTime getHorarioCriacao() {
        return horarioCriacao;
    }

    public LocalDateTime getHorarioInicio() {
        return horarioInicio;
    }

    public LocalDateTime getHorarioFim() {
        return horarioFim;
    }

    public Votacao abrir(Optional<Long> duracao) throws VotacaoException {
        if (!this.estado.equals(NAO_INICIADA)) {
            throw new VotacaoException("Votação " + id + " não está aguardando para ser aberta!");
        }
        Long novaDuracao = duracao.orElse(this.duracao);
        return new Votacao(this.id,
                this.pauta,
                EM_ANDAMENTO,
                novaDuracao,
                this.resultado,
                this.horarioCriacao,
                now(),
                now().plusMinutes(novaDuracao));
    }

    public Votacao votar(Escolha escolha) throws VotacaoException {
        if (!this.estado.equals(EM_ANDAMENTO)) {
            throw new VotacaoException("Votação " + id + " não está em andamento para votar!");
        } else if (now().isAfter(this.horarioFim)) {
            throw new VotacaoException("Votação " + id + " encerrada!");
        }
        var resultadoComputado = escolha.equals(Escolha.SIM) ? this.resultado.votarSim() : this.resultado.votarNao();
        return new Votacao(this.id,
                this.pauta,
                this.getEstado(),
                this.duracao,
                resultadoComputado,
                this.horarioCriacao,
                this.horarioInicio,
                this.horarioFim);
    }

    public Votacao encerrar() throws VotacaoException {
        if (!this.estado.equals(EM_ANDAMENTO)) {
            throw new VotacaoException("Votação " + id + " não está em andamento para ser finalizada!");
        }
        if (now().isBefore(this.horarioFim)) {
            throw new VotacaoException("Votação " + id + " em andamento até " + horarioFim + "!");
        }
        return new Votacao(this.id,
                this.pauta,
                EstadoVotacao.ENCERRADA,
                this.duracao,
                this.resultado,
                this.horarioCriacao,
                this.horarioInicio,
                this.horarioFim);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Votacao votacao = (Votacao) o;
        return Objects.equals(id, votacao.id) && Objects.equals(pauta, votacao.pauta) && estado == votacao.estado && Objects.equals(duracao, votacao.duracao) && Objects.equals(resultado, votacao.resultado) && Objects.equals(horarioCriacao, votacao.horarioCriacao) && Objects.equals(horarioInicio, votacao.horarioInicio) && Objects.equals(horarioFim, votacao.horarioFim);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pauta, estado, duracao, resultado, horarioCriacao, horarioInicio, horarioFim);
    }

    @Override
    public String toString() {
        return "Votacao{" +
                "id='" + id + '\'' +
                ", pauta='" + pauta + '\'' +
                ", estado=" + estado +
                ", duracao=" + duracao +
                ", resultado=" + resultado +
                ", horarioCriacao=" + horarioCriacao +
                ", horarioInicio=" + horarioInicio +
                ", horarioFim=" + horarioFim +
                '}';
    }
}
