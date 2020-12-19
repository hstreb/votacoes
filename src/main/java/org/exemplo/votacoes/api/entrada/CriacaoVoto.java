package org.exemplo.votacoes.api.entrada;

import org.exemplo.votacoes.dominios.Escolha;

public class CriacaoVoto {
    private String associado;
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
