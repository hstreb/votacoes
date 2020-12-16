package org.exemplo.votacoes.dominio;

import java.util.Objects;
import java.util.UUID;

public class Voto {
    private final String id;
    private final String votacao;
    private final String associado;
    private final Escolha escolha;

    public Voto(String votacao, String associado, Escolha escolha) {
        this.id = UUID.randomUUID().toString();
        this.votacao = votacao;
        this.associado = associado;
        this.escolha = escolha;
    }

    public String getId() {
        return id;
    }

    public String getVotacao() {
        return votacao;
    }

    public String getAssociado() {
        return associado;
    }

    public Escolha getEscolha() {
        return escolha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voto voto = (Voto) o;
        return Objects.equals(id, voto.id) && Objects.equals(votacao, voto.votacao) && Objects.equals(associado, voto.associado) && escolha == voto.escolha;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, votacao, associado, escolha);
    }

    @Override
    public String toString() {
        return "Voto{" +
                "id='" + id + '\'' +
                ", votacao='" + votacao + '\'' +
                ", associado='" + associado + '\'' +
                ", escolha=" + escolha +
                '}';
    }
}
