package org.exemplo.votacoes.servicos;

import org.exemplo.votacoes.dominios.Resultado;
import org.exemplo.votacoes.dominios.Votacao;
import org.exemplo.votacoes.dominios.Voto;
import org.exemplo.votacoes.dominios.exception.VotacaoException;
import org.exemplo.votacoes.dominios.exception.VotacaoNaoEncontradaException;
import org.exemplo.votacoes.repositorios.AssociadoRepository;
import org.exemplo.votacoes.repositorios.VotacaoRepository;
import org.exemplo.votacoes.repositorios.VotoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.exemplo.votacoes.dados.ConstantesVotacao.*;
import static org.exemplo.votacoes.dominios.Escolha.NAO;
import static org.exemplo.votacoes.dominios.EstadoVotacao.EM_ANDAMENTO;
import static org.exemplo.votacoes.dominios.EstadoVotacao.NAO_INICIADA;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class VotacaoServiceTest {

    private static final String ASSOCIADO = "74066871097";

    @Mock
    private VotacaoRepository votacaoRepository;

    @Mock
    private AssociadoRepository associadoRepository;

    @Mock
    private VotoRepository votoRepository;

    @InjectMocks
    private VotacaoService votacaoService;

    @Test
    void deve_criar_uma_votacao_com_estado_nao_iniciado() throws VotacaoException {
        given(votacaoRepository.salvar(any()))
                .willReturn(VOTACAO_NAO_INICIADA);

        var votacaoCriada = votacaoService.criar(PAUTA);

        assertThat(votacaoCriada.getId())
                .isEqualTo(ID);
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
        given(votacaoRepository.buscarPorId(ID))
                .willReturn(VOTACAO_NAO_INICIADA);
        given(votacaoRepository.salvar(any()))
                .willReturn(VOTACAO_EM_ANDAMENTO);

        var votacaoCriada = votacaoService.abrir(ID, Optional.empty());

        assertThat(votacaoCriada.getId())
                .isEqualTo(ID);
        assertThat(votacaoCriada.getPauta())
                .isEqualTo(PAUTA);
        assertThat(votacaoCriada.getEstado())
                .isEqualTo(EM_ANDAMENTO);
        assertThat(votacaoCriada.getDuracao())
                .isEqualTo(DURACAO);
        assertThat(votacaoCriada.getResultado())
                .isEqualTo(RESULTADO);
        assertThat(votacaoCriada.getHorarioCriacao().truncatedTo(ChronoUnit.SECONDS))
                .isEqualTo(HORARIO);
        assertThat(votacaoCriada.getHorarioInicio().truncatedTo(ChronoUnit.SECONDS))
                .isAfterOrEqualTo(HORARIO);
        assertThat(votacaoCriada.getHorarioFim().truncatedTo(ChronoUnit.SECONDS))
                .isAfterOrEqualTo(votacaoCriada.getHorarioInicio());
    }

    @Test
    void nao_deve_abrir_uma_votacao_quando_nao_encontrar_uma_votacao() throws VotacaoException {
        given(votacaoRepository.buscarPorId(ID))
                .willThrow(new VotacaoNaoEncontradaException("Votação " + ID + " não encontrada!"));
        assertThatThrownBy(() -> votacaoService.abrir(ID, Optional.empty()))
                .isInstanceOf(VotacaoNaoEncontradaException.class)
                .hasMessage("Votação " + ID + " não encontrada!");
    }

    @Test
    void deve_encerrar_uma_votacao() throws VotacaoException {
        given(votacaoRepository.buscarPorId(ID))
                .willReturn(VOTACAO_EM_ANDAMENTO_HORARIO_FINALIZADO);
        given(votacaoRepository.salvar(any()))
                .willReturn(VOTACAO_ENCERRADA);

        var votacaoEncerrada = votacaoService.encerrar(ID);

        assertThat(votacaoEncerrada)
                .isEqualTo(VOTACAO_ENCERRADA);
    }

    @Test
    void nao_deve_encerrar_uma_votacao_quando_nao_encontrar_uma_votacao() throws VotacaoException {
        given(votacaoRepository.buscarPorId(ID))
                .willThrow(new VotacaoNaoEncontradaException("Votação " + ID + " não encontrada!"));
        assertThatThrownBy(() -> votacaoService.encerrar(ID))
                .isInstanceOf(VotacaoNaoEncontradaException.class)
                .hasMessage("Votação " + ID + " não encontrada!");
    }

    @Test
    void nao_deve_encerrar_uma_votacao_que_nao_esta_em_andamento() throws VotacaoException {
        given(votacaoRepository.buscarPorId(ID))
                .willReturn(VOTACAO_NAO_INICIADA);

        assertThatThrownBy(() -> votacaoService.encerrar(ID))
                .isInstanceOf(VotacaoException.class)
                .hasMessage("Votação " + ID + " não está em andamento para ser finalizada!");
    }

    @Test
    void nao_deve_encerrar_uma_votacao_antes_do_horario_final() throws VotacaoException {
        given(votacaoRepository.buscarPorId(ID))
                .willReturn(VOTACAO_EM_ANDAMENTO);

        assertThatThrownBy(() -> votacaoService.encerrar(ID))
                .isInstanceOf(VotacaoException.class)
                .hasMessage("Votação " + ID + " em andamento até " + HORARIO.plusMinutes(DURACAO) + "!");
    }

    @Test
    void deve_contabilizar_um_voto() throws VotacaoException {
        var resultadoEsperado = new Resultado(0L, 1L);
        var votacaoEsperada = new Votacao(ID, PAUTA, EM_ANDAMENTO, DURACAO, resultadoEsperado, HORARIO, HORARIO, HORARIO.plusMinutes(DURACAO));
        var votoEsperado = new Voto(ID, ASSOCIADO, NAO);
        given(associadoRepository.verificar(ASSOCIADO))
                .willReturn(true);
        given(votoRepository.buscarPorVotacaoEAssociado(ID, ASSOCIADO))
                .willReturn(Optional.empty());
        given(votoRepository.salvar(any()))
                .willReturn(votoEsperado);
        given(votacaoRepository.buscarPorId(ID))
                .willReturn(VOTACAO_EM_ANDAMENTO);
        given(votacaoRepository.salvar(votacaoEsperada))
                .willReturn(votacaoEsperada);

        var votoComputado = votacaoService.votar(ID, ASSOCIADO, NAO);

        assertThat(votoComputado.getId())
                .isEqualTo(votoEsperado.getId());
        assertThat(votoComputado.getVotacao())
                .isEqualTo(votoEsperado.getVotacao());
        assertThat(votoComputado.getAssociado())
                .isEqualTo(votoEsperado.getAssociado());
        assertThat(votoComputado.getEscolha())
                .isEqualTo(votoEsperado.getEscolha());
    }

    @Test
    void nao_deve_votar_duas_vezes() throws VotacaoException {
        var votoEsperado = new Voto(ID, ASSOCIADO, NAO);
        given(associadoRepository.verificar(ASSOCIADO))
                .willReturn(true);
        given(votoRepository.buscarPorVotacaoEAssociado(ID, ASSOCIADO))
                .willReturn(Optional.of(votoEsperado));

        assertThatThrownBy(() -> votacaoService.votar(ID, ASSOCIADO, NAO))
                .isInstanceOf(VotacaoException.class)
                .hasMessage("Voto de " + ASSOCIADO + " na votação " + ID + " já contabilizado!");
    }

    @Test
    void nao_deve_votar_quando_nao_habilitado() throws VotacaoException {
        given(associadoRepository.verificar(ASSOCIADO))
                .willReturn(false);

        assertThatThrownBy(() -> votacaoService.votar(ID, ASSOCIADO, NAO))
                .isInstanceOf(VotacaoException.class)
                .hasMessage("Associado " + ASSOCIADO + " não habilitado!");
    }

    @Test
    void nao_deve_votar_em_votacao_finalizada() throws VotacaoException {
        var votoEsperado = new Voto(ID, ASSOCIADO, NAO);
        given(associadoRepository.verificar(ASSOCIADO))
                .willReturn(true);
        given(votoRepository.salvar(any()))
                .willReturn(votoEsperado);
        given(votacaoRepository.buscarPorId(ID))
                .willReturn(VOTACAO_EM_ANDAMENTO_HORARIO_FINALIZADO);

        assertThatThrownBy(() -> votacaoService.votar(ID, ASSOCIADO, NAO))
                .isInstanceOf(VotacaoException.class)
                .hasMessage("Votação " + ID + " encerrada!");
    }
}
