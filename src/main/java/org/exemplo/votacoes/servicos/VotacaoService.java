package org.exemplo.votacoes.servicos;

import org.exemplo.votacoes.dominios.Escolha;
import org.exemplo.votacoes.dominios.Votacao;
import org.exemplo.votacoes.dominios.Voto;
import org.exemplo.votacoes.dominios.exception.AssociadoNaoHabilitadoException;
import org.exemplo.votacoes.dominios.exception.VotacaoException;
import org.exemplo.votacoes.dominios.exception.VotoJaContabilizadoException;
import org.exemplo.votacoes.repositorios.AssociadoRepository;
import org.exemplo.votacoes.repositorios.VotacaoRepository;
import org.exemplo.votacoes.repositorios.VotoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VotacaoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VotacaoService.class);

    private final VotacaoRepository votacaoRepository;
    private final AssociadoRepository associadoRepository;
    private final VotoRepository votoRepository;

    public VotacaoService(VotacaoRepository votacaoRepository, AssociadoRepository associadoRepository, VotoRepository votoRepository) {
        this.votacaoRepository = votacaoRepository;
        this.associadoRepository = associadoRepository;
        this.votoRepository = votoRepository;
    }

    public Votacao criar(String pauta) throws VotacaoException {
        LOGGER.info("Criando nova votação com a pauta: {}", pauta);
        var votacao = new Votacao(pauta);
        votacaoRepository.salvar(votacao);
        LOGGER.info("Votação criada: {}", votacao);
        return votacao;
    }

    public Votacao abrir(String id, Optional<Long> duracao) throws VotacaoException {
        LOGGER.info("Iniciando nova votação: {}", id);
        var votacao = votacaoRepository.buscarPorId(id)
                .abrir(duracao);
        votacaoRepository.salvar(votacao);
        LOGGER.info("Votação iniciada: {}", votacao);
        return votacao;
    }

    public Voto votar(String idVotacao, String associado, Escolha escolha) throws VotacaoException {
        LOGGER.info("Iniciando novo voto: {}, {}, {}", idVotacao, associado, escolha);
        validar(idVotacao, associado);
        var voto = new Voto(idVotacao, associado, escolha);
        votoRepository.salvar(voto);
        var votacao = votacaoRepository.buscarPorId(idVotacao)
                .votar(escolha);
        votacaoRepository.salvar(votacao);
        LOGGER.info("Voto computado: {}", voto);
        return voto;
    }

    private void validar(String idVotacao, String associado) throws VotacaoException {
        if (!associadoRepository.verificar(associado)) {
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
        votacaoRepository.salvar(votacao);
        LOGGER.info("Votação encerrada: {}", votacao);
        return votacao;
    }
}
