package org.exemplo.votacoes.api.entrada;

import org.exemplo.votacoes.dominios.EstadoVotacao;

import java.util.Optional;

public class AlteracaoVotacao {
    private EstadoVotacao estado;
    private Optional<Long> duracao;

    public EstadoVotacao getEstado() {
        return estado;
    }

    public void setEstado(EstadoVotacao estado) {
        this.estado = estado;
    }

    public Optional<Long> getDuracao() {
        return duracao;
    }

    public void setDuracao(Optional<Long> duracao) {
        this.duracao = duracao;
    }
}
