package org.exemplo.votacoes.dominios;

import java.util.Objects;

public class Resultado {
    private final Long totalSim;
    private final Long totalNao;
    private final Long votos;

    public Resultado() {
        this.totalSim = 0L;
        this.totalNao = 0L;
        this.votos = 0L;
    }

    public Resultado(Long totalSim, Long totalNao) {
        this.totalSim = totalSim;
        this.totalNao = totalNao;
        this.votos = totalSim + totalNao;
    }

    public Long getTotalSim() {
        return totalSim;
    }

    public Long getTotalNao() {
        return totalNao;
    }

    public Long getVotos() {
        return votos;
    }

    public Resultado votarSim() {
        return new Resultado(this.totalSim + 1, this.totalNao);
    }

    public Resultado votarNao() {
        return new Resultado(this.totalSim, this.totalNao + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resultado resultado = (Resultado) o;
        return Objects.equals(totalSim, resultado.totalSim) && Objects.equals(totalNao, resultado.totalNao) && Objects.equals(votos, resultado.votos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalSim, totalNao, votos);
    }

    @Override
    public String toString() {
        return "Resultado{" +
                "totalSim=" + totalSim +
                ", totalNao=" + totalNao +
                ", votos=" + votos +
                '}';
    }
}
