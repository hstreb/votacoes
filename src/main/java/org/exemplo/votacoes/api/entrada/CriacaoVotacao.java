package org.exemplo.votacoes.api.entrada;

public class CriacaoVotacao {
    private String pauta;

    public String getPauta() {
        return pauta;
    }

    public void setPauta(String pauta) {
        this.pauta = pauta;
    }

    @Override
    public String toString() {
        return "CriacaoVotacao{" +
                "pauta='" + pauta + '\'' +
                '}';
    }
}
