package org.exemplo.votacoes.dominio;

import org.exemplo.votacoes.dominio.exception.VotacaoException;

public interface VotacaoRepository {
    Votacao buscarPorId(String id) throws VotacaoException;

    Votacao salvar(Votacao votacao);

    Votacao atualizar(Votacao votacao);
}
