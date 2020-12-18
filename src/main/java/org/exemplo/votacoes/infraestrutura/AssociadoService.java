package org.exemplo.votacoes.infraestrutura;

import org.exemplo.votacoes.dominio.exception.VotacaoException;
import org.springframework.stereotype.Service;

@Service
public class AssociadoService implements org.exemplo.votacoes.dominio.AssociadoRepository {
    @Override
    public boolean verificar(String associado) throws VotacaoException {
        return true;
    }
}
