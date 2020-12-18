package org.exemplo.votacoes.dominio;

import org.exemplo.votacoes.dominio.exception.AssociadoNaoHabilitadoException;
import org.exemplo.votacoes.dominio.exception.VotacaoException;
import org.exemplo.votacoes.dominio.exception.VotoJaContabilizadoException;
import org.exemplo.votacoes.infraestrutura.AssociadoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VotacaoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VotacaoService.class);

    private final org.exemplo.votacoes.dominio.VotacaoRepository votacaoRepository;
    private final AssociadoService associadoService;
    private final org.exemplo.votacoes.dominio.VotoRepository votoRepository;

    public VotacaoService(org.exemplo.votacoes.dominio.VotacaoRepository votacaoRepository, AssociadoService associadoService, org.exemplo.votacoes.dominio.VotoRepository votoRepository) {
        this.votacaoRepository = votacaoRepository;
        this.associadoService = associadoService;
        this.votoRepository = votoRepository;
    }

    public Votacao criar(String pauta) {
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
        votacaoRepository.atualizar(votacao);
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
        votacaoRepository.atualizar(votacao);
        LOGGER.info("Voto computado: {}", voto);
        return voto;
    }

    private void validar(String idVotacao, String associado) throws VotacaoException {
        if (!associadoService.verificar(associado)) {
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
        votacaoRepository.atualizar(votacao);
        LOGGER.info("Votação encerrada: {}", votacao);
        return votacao;
    }
}
