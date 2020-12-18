package org.exemplo.votacoes.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotacaoDao extends CrudRepository<VotacaoEntity, String> {
}
