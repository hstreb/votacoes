package org.exemplo.votacoes.repositorios;

import org.exemplo.votacoes.dominios.EstadoVotacao;
import org.exemplo.votacoes.dominios.Resultado;
import org.exemplo.votacoes.dominios.Votacao;
import org.exemplo.votacoes.dominios.exception.VotacaoException;
import org.exemplo.votacoes.dominios.exception.VotacaoNaoEncontradaException;
import org.springframework.stereotype.Component;

@Component
public class VotacaoRepository {

    private VotacaoDao votacaoDao;

    public VotacaoRepository(VotacaoDao votacaoDao) {
        this.votacaoDao = votacaoDao;
    }

    public Votacao buscarPorId(String id) throws VotacaoException {
        return votacaoDao.findById(id)
                .map(e -> new Votacao(e.getId(),
                        e.getPauta(),
                        EstadoVotacao.valueOf(e.getEstado()),
                        e.getDuracao(),
                        new Resultado(e.getTotalSim(), e.getTotalNao()),
                        e.getHorarioCriacao(),
                        e.getHorarioInicio(),
                        e.getHorarioFim()))
                .orElseThrow(() -> new VotacaoNaoEncontradaException("Votaçao " + id + "nao encontrada!"));
    }
    public Votacao salvar(Votacao votacao) throws VotacaoException {
        var entity = new VotacaoEntity(votacao.getId(),
                votacao.getPauta(),
                votacao.getEstado().toString(),
                votacao.getDuracao(),
                votacao.getResultado().getTotalSim(),
                votacao.getResultado().getTotalNao(),
                votacao.getHorarioCriacao(),
                votacao.getHorarioInicio(),
                votacao.getHorarioFim());
        votacaoDao.save(entity);
        return votacao;
    }
}
