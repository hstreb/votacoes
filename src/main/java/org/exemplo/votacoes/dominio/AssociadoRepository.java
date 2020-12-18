package org.exemplo.votacoes.dominio;

import org.exemplo.votacoes.dominio.exception.VotacaoException;

public interface AssociadoRepository {
    boolean verificar(String associado) throws VotacaoException;
}
