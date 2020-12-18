package org.exemplo.votacoes.repositorios;

import org.exemplo.votacoes.dominios.exception.VotacaoException;
import org.springframework.stereotype.Component;

@Component
public class AssociadoRepository {
    public boolean verificar(String associado) throws VotacaoException {
        return true;
    }
}
