package org.exemplo.votacoes.servicos;

import org.exemplo.votacoes.clientes.AssociadoClient;
import org.exemplo.votacoes.clientes.AssociadoStatus;
import org.exemplo.votacoes.dominios.Escolha;
import org.exemplo.votacoes.dominios.Votacao;
import org.exemplo.votacoes.dominios.Voto;
import org.exemplo.votacoes.dominios.exception.AssociadoNaoHabilitadoException;
import org.exemplo.votacoes.dominios.exception.VotacaoException;
import org.exemplo.votacoes.dominios.exception.VotoJaContabilizadoException;
import org.exemplo.votacoes.mensageria.VotacaoProducer;
import org.exemplo.votacoes.mensageria.VotoProducer;
import org.exemplo.votacoes.repositorios.VotacaoRepository;
import org.exemplo.votacoes.repositorios.VotoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class VotacaoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VotacaoService.class);

    private final VotacaoRepository votacaoRepository;
    private final AssociadoClient associadoClient;
    private final VotoRepository votoRepository;
    private final VotacaoProducer votacaoProducer;
    private final VotoProducer votoProducer;

    public VotacaoService(VotacaoRepository votacaoRepository,
                          AssociadoClient associadoClient,
                          VotoRepository votoRepository,
                          VotacaoProducer votacaoProducer, VotoProducer votoProducer) {
        this.votacaoRepository = votacaoRepository;
        this.associadoClient = associadoClient;
        this.votoRepository = votoRepository;
        this.votacaoProducer = votacaoProducer;
        this.votoProducer = votoProducer;
    }

    public Votacao criar(String pauta) throws VotacaoException {
        LOGGER.info("Criando nova votação com a pauta: {}", pauta);
        var votacao = new Votacao(pauta);
        var votacaoCriada = votacaoRepository.salvar(votacao);
        votacaoProducer.enviar(votacao);
        LOGGER.info("Votação criada: {}", votacaoCriada);
        return votacaoCriada;
    }

    public Votacao abrir(String id, Optional<Long> duracao) throws VotacaoException {
        LOGGER.info("Iniciando nova votação: {}", id);
        var votacao = votacaoRepository.buscarPorId(id)
                .abrir(duracao);
        var votacaoIniciada = votacaoRepository.salvar(votacao);
        votacaoProducer.enviar(votacao);
        LOGGER.info("Votação iniciada: {}", votacaoIniciada);
        return votacaoIniciada;
    }

    @Transactional
    public Voto votar(String idVotacao, String associado, Escolha escolha) throws VotacaoException {
        LOGGER.info("Iniciando novo voto: {}, {}, {}", idVotacao, associado, escolha);
        validar(idVotacao, associado);
        var voto = new Voto(idVotacao, associado, escolha);
        var votoComputado = votoRepository.salvar(voto);
        votoProducer.enviar(voto);
        var votacao = votacaoRepository.buscarPorId(idVotacao)
                .votar(escolha);
        votacaoRepository.salvar(votacao);
        LOGGER.info("Voto computado: {}", votoComputado);
        return votoComputado;
    }

    private void validar(String idVotacao, String associado) throws VotacaoException {
        if (associadoClient.verificar(associado).getStatus().equals(AssociadoStatus.UNABLE_TO_VOTE)) {
            throw new AssociadoNaoHabilitadoException("Associado " + associado + " não habilitado!");
        }
        var jaVotou = votoRepository.buscarPorVotacaoEAssociado(idVotacao, associado)
                .isPresent();
        if (jaVotou) {
            throw new VotoJaContabilizadoException("Voto de " + associado + " na votação " + idVotacao + " já contabilizado!");
        }
    }

    public Votacao encerrar(String id) throws VotacaoException {
        var votacao = votacaoRepository.buscarPorId(id)
                .encerrar();
        var votacaoEncerrada = votacaoRepository.salvar(votacao);
        votacaoProducer.enviar(votacao);
        LOGGER.info("Votação encerrada: {}", votacaoEncerrada);
        return votacaoEncerrada;
    }
}
