package org.exemplo.votacoes.dominios;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResultadoTest {

    @Test
    void deve_votar_sim_corretamente() {
        var resultado = new Resultado();

        var resultadoSim = resultado.votarSim();

        assertThat(resultadoSim.getTotalSim())
                .isEqualTo(1L);
        assertThat(resultadoSim.getTotalNao())
                .isEqualTo(0L);
        assertThat(resultadoSim.getVotos())
                .isEqualTo(1L);
        assertThat(resultadoSim.toString())
                .isEqualTo("Resultado{totalSim=1, totalNao=0, votos=1}");
    }

    @Test
    void deve_votar_nao_corretamente() {
        var resultado = new Resultado();

        var resultadoSim = resultado.votarNao();

        assertThat(resultadoSim.getTotalSim())
                .isEqualTo(0L);
        assertThat(resultadoSim.getTotalNao())
                .isEqualTo(1L);
        assertThat(resultadoSim.getVotos())
                .isEqualTo(1L);
        assertThat(resultadoSim.hashCode())
                .isEqualTo(29823);
    }

}
