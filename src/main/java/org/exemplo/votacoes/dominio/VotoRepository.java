package org.exemplo.votacoes.dominio;

import java.util.Optional;

public interface VotoRepository {
    Voto salvar(Voto voto);

    Optional<Voto> buscarPorVotacaoEAssociado(String idVotacao, String associado);
}
