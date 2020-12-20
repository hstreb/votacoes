package org.exemplo.votacoes.dominios;

import org.exemplo.votacoes.dominios.exception.VotacaoException;
import org.junit.jupiter.api.Test;

import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.exemplo.votacoes.dados.ConstantesVotacao.*;
import static org.exemplo.votacoes.dominios.Escolha.SIM;
import static org.exemplo.votacoes.dominios.EstadoVotacao.*;

class VotacaoTest {

    @Test
    void deve_criar_uma_votacao_com_estado_aguardando() {
        var votacaoCriada = new Votacao(PAUTA);

        assertThat(votacaoCriada.getId())
                .isNotEqualTo(ID);
        assertThat(votacaoCriada.getPauta())
                .isEqualTo(PAUTA);
        assertThat(votacaoCriada.getEstado())
                .isEqualTo(NAO_INICIADA);
        assertThat(votacaoCriada.getDuracao())
                .isEqualTo(DURACAO);
        assertThat(votacaoCriada.getResultado())
                .isEqualTo(RESULTADO);
        assertThat(votacaoCriada.getHorarioCriacao().truncatedTo(ChronoUnit.SECONDS))
                .isAfterOrEqualTo(HORARIO);
        assertThat(votacaoCriada.getHorarioInicio())
                .isNull();
        assertThat(votacaoCriada.getHorarioFim())
                .isNull();
    }

    @Test
    void deve_abrir_uma_votacao() throws VotacaoException {
        var votacaoAberta = VOTACAO_NAO_INICIADA
                .abrir(Optional.empty());

        assertThat(votacaoAberta.getId())
                .isEqualTo(ID);
        assertThat(votacaoAberta.getPauta())
                .isEqualTo(PAUTA);
        assertThat(votacaoAberta.getEstado())
                .isEqualTo(EM_ANDAMENTO);
        assertThat(votacaoAberta.getDuracao())
                .isEqualTo(DURACAO);
        assertThat(votacaoAberta.getResultado())
                .isEqualTo(RESULTADO);
        assertThat(votacaoAberta.getHorarioCriacao().truncatedTo(ChronoUnit.SECONDS))
                .isEqualTo(HORARIO);
        assertThat(votacaoAberta.getHorarioInicio().truncatedTo(ChronoUnit.SECONDS))
                .isAfterOrEqualTo(HORARIO);
        assertThat(votacaoAberta.getHorarioFim().truncatedTo(ChronoUnit.SECONDS))
                .isAfterOrEqualTo(HORARIO.plusMinutes(DURACAO));
    }

    @Test
    void nao_deve_abrir_uma_votacao_quando_estado_diferente_de_nao_iniciada() {
        assertThatThrownBy(() -> VOTACAO_EM_ANDAMENTO.abrir(Optional.empty()))
                .isInstanceOf(VotacaoException.class)
                .hasMessage("Votação " + ID + " não está aguardando para ser aberta!");
    }

    @Test
    void deve_votar_corretamente() throws VotacaoException {
        var resultadoEsperado = new Resultado(1L, 0L);
        var votacaoEsperada = new Votacao(ID,
                PAUTA,
                EM_ANDAMENTO,
                DURACAO,
                resultadoEsperado,
                HORARIO,
                HORARIO,
                HORARIO.plusMinutes(DURACAO));

        var votoComputado = VOTACAO_EM_ANDAMENTO.votar(SIM);

        assertThat(votoComputado)
                .isEqualTo(votacaoEsperada);
    }

    @Test
    void nao_deve_votar_quando_nao_em_votacao() {
        assertThatThrownBy(() -> VOTACAO_ENCERRADA.votar(SIM))
                .isInstanceOf(VotacaoException.class)
                .hasMessage("Votação " + ID + " não está em andamento para votar!");
    }

    @Test
    void nao_deve_votar_quando_passou_horario_fim() {
        assertThatThrownBy(() -> VOTACAO_EM_ANDAMENTO_HORARIO_FINALIZADO.votar(SIM))
                .isInstanceOf(VotacaoException.class)
                .hasMessage("Votação " + ID + " encerrada!");
    }

    @Test
    void deve_encerrar_corretamente() throws VotacaoException {
        var votacaoEsperada = new Votacao(ID,
                PAUTA,
                ENCERRADA,
                DURACAO,
                RESULTADO,
                HORARIO.minusMinutes(2L),
                HORARIO.minusMinutes(2L),
                HORARIO.minusMinutes(1L));

        var votacaoEncerrada = VOTACAO_EM_ANDAMENTO_HORARIO_FINALIZADO.encerrar();

        assertThat(votacaoEncerrada)
                .isEqualTo(votacaoEsperada);
    }

    @Test
    void nao_deve_encerrar_quando_estado_diferente_de_em_andamento() {
        assertThatThrownBy(() -> VOTACAO_NAO_INICIADA.encerrar())
                .isInstanceOf(VotacaoException.class)
                .hasMessage("Votação " + ID + " não está em andamento para ser finalizada!");
    }

    @Test
    void nao_deve_encerrar_enquando_nao_chegar_hora_fim_votacao() {
        assertThatThrownBy(() -> VOTACAO_EM_ANDAMENTO.encerrar())
                .isInstanceOf(VotacaoException.class)
                .hasMessage("Votação " + ID + " em andamento até " + HORARIO.plusMinutes(DURACAO) + "!");
    }

}
