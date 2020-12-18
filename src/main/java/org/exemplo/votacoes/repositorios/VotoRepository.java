package org.exemplo.votacoes.repositorios;

import org.exemplo.votacoes.dominios.Escolha;
import org.exemplo.votacoes.dominios.Voto;

import java.util.Optional;

public class VotoRepository {
    private VotoDao votoDao;

    public VotoRepository(VotoDao votoDao) {
        this.votoDao = votoDao;
    }

    public Voto salvar(Voto voto) {
        var entity = new VotoEntity(voto.getId(),
                voto.getVotacao(),
                voto.getAssociado(),
                voto.getEscolha().toString(),
                voto.getHorario());
        votoDao.save(entity);
        return voto;
    }

    public Optional<Voto> buscarPorVotacaoEAssociado(String idVotacao, String associado) {
        return votoDao.findByVotacaoAndAssociado(idVotacao, associado)
                .map(e -> new Voto(e.getVotacao(), e.getAssociado(), Escolha.valueOf(e.getEscolha())));
    }
}
