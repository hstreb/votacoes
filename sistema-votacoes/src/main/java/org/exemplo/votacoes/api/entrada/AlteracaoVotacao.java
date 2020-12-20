package org.exemplo.votacoes.api.entrada;

import org.exemplo.votacoes.dominios.EstadoVotacao;

import javax.validation.constraints.NotNull;

public class AlteracaoVotacao {
    @NotNull(message = "O campo 'estado' não pode ser nulo!")
    private EstadoVotacao estado;
    private Long duracao;

    public EstadoVotacao getEstado() {
        return estado;
    }

    public void setEstado(EstadoVotacao estado) {
        this.estado = estado;
    }

    public Long getDuracao() {
        return duracao;
    }

    public void setDuracao(Long duracao) {
        this.duracao = duracao;
    }
}
