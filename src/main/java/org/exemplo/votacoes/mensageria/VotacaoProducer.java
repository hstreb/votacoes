package org.exemplo.votacoes.mensageria;

import org.exemplo.votacoes.dominios.Votacao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.SuccessCallback;

import static org.springframework.kafka.support.KafkaHeaders.MESSAGE_KEY;
import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@Component
public class VotacaoProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(VotacaoProducer.class);

    @Value("${topicos.votacoes.nome}")
    private String topicoVotacoes;

    private final KafkaTemplate<String, Votacao> kafkaTemplate;

    public VotacaoProducer(KafkaTemplate<String, Votacao> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviar(Votacao votacao) {
        var message = MessageBuilder
                .withPayload(votacao)
                .setHeader(TOPIC, topicoVotacoes)
                .setHeader(MESSAGE_KEY, votacao.getId())
                .build();
        kafkaTemplate.send(message)
                .addCallback(sucesso(votacao), falha(votacao));
    }

    private SuccessCallback<SendResult<String, Votacao>> sucesso(Votacao votacao) {
        return result -> LOGGER.debug("Votação enviada: '{}', no tópico {}, com offset {} e na partição {}",
                votacao,
                result.getProducerRecord().topic(),
                result.getRecordMetadata().offset(),
                result.getRecordMetadata().partition());
    }

    private FailureCallback falha(Votacao votacao) {
        return error -> LOGGER.error("Erro ao enviar {}", votacao, error);
    }
}
