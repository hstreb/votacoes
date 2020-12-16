package org.exemplo.votacoes.dominio;

import java.util.Objects;
import java.util.UUID;

public class Votacao {

    private static final long DURACAO_UM_MINUTO = 1L;

    private final String id;
    private final String pauta;
    private final EstadoVotacao estado;
    private final Long duracao;
    private final Resultado resultado;

    public Votacao(String pauta) {
        this.id = UUID.randomUUID().toString();
        this.pauta = pauta;
        this.estado = EstadoVotacao.NAO_INICIADA;
        this.duracao = DURACAO_UM_MINUTO;
        this.resultado = new Resultado();
    }

    public Votacao(String id,
                   String pauta,
                   EstadoVotacao estado,
                   Long duracao,
                   Resultado resultado) {
        this.id = id;
        this.pauta = pauta;
        this.estado = estado;
        this.duracao = duracao;
        this.resultado = resultado;
    }

    public String getId() {
        return id;
    }

    public String getPauta() {
        return pauta;
    }

    public EstadoVotacao getEstado() {
        return estado;
    }

    public Long getDuracao() {
        return duracao;
    }

    public Resultado getResultado() {
        return resultado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Votacao votacao = (Votacao) o;
        return Objects.equals(id, votacao.id) && Objects.equals(pauta, votacao.pauta) && estado == votacao.estado && Objects.equals(duracao, votacao.duracao) && Objects.equals(resultado, votacao.resultado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pauta, estado, duracao, resultado);
    }

    @Override
    public String toString() {
        return "Votacao{" +
                "id='" + id + '\'' +
                ", pauta='" + pauta + '\'' +
                ", estado=" + estado +
                ", duracao=" + duracao +
                ", resultado=" + resultado +
                '}';
    }
}
