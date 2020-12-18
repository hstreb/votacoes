package org.exemplo.votacoes.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VotoDao extends CrudRepository<VotoEntity, String> {
    Optional<VotoEntity> findByVotacaoAndAssociado(String votacao, String associado);
}
