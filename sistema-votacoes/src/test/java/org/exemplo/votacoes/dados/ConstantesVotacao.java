package org.exemplo.votacoes.dados;

import org.exemplo.votacoes.dominios.Resultado;
import org.exemplo.votacoes.dominios.Votacao;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.exemplo.votacoes.dominios.EstadoVotacao.*;

public class ConstantesVotacao {

    public static final String ID = UUID.randomUUID().toString();
    public static final String PAUTA = "Esse teste vai passar?";
    public static final long DURACAO = 1L;
    public static final Resultado RESULTADO = new Resultado();
    public static final LocalDateTime HORARIO = LocalDateTime.now()
            .minusSeconds(1L)
            .truncatedTo(ChronoUnit.SECONDS);
    public static final Votacao VOTACAO_NAO_INICIADA = new Votacao(ID,
            PAUTA,
            NAO_INICIADA,
            DURACAO,
            RESULTADO,
            HORARIO,
            null,
            null);
    public static final Votacao VOTACAO_EM_ANDAMENTO = new Votacao(ID,
            PAUTA,
            EM_ANDAMENTO,
            DURACAO,
            RESULTADO,
            HORARIO,
            HORARIO,
            HORARIO.plusMinutes(DURACAO));
    public static final Votacao VOTACAO_EM_ANDAMENTO_HORARIO_FINALIZADO = new Votacao(ID,
            PAUTA,
            EM_ANDAMENTO,
            DURACAO,
            RESULTADO,
            HORARIO.minusMinutes(2L),
            HORARIO.minusMinutes(2L),
            HORARIO.minusMinutes(1L));
    public static final Votacao VOTACAO_ENCERRADA = new Votacao(ID,
            PAUTA,
            ENCERRADA,
            DURACAO,
            RESULTADO,
            HORARIO.minusMinutes(2L),
            HORARIO.minusMinutes(2L),
            HORARIO.minusMinutes(1L));
}
