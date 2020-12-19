package org.exemplo.votacoes.api.entrada;

import org.exemplo.votacoes.dominios.Escolha;

import javax.validation.constraints.NotNull;

public class CriacaoVoto {
    @NotNull(message = "O campo 'associado' não pode ser nulo!")
    private String associado;
    @NotNull(message = "O campo 'escolha' não pode ser nulo!")
    private Escolha escolha;

    public String getAssociado() {
        return associado;
    }

    public void setAssociado(String associado) {
        this.associado = associado;
    }

    public Escolha getEscolha() {
        return escolha;
    }

    public void setEscolha(Escolha escolha) {
        this.escolha = escolha;
    }
}
