package org.exemplo.votacoes.mensageria;

import org.exemplo.votacoes.dominios.Voto;
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
public class VotoProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(VotoProducer.class);

    @Value("${topicos.votos.nome}")
    private String topicoVotos;

    private final KafkaTemplate<String, Voto> kafkaTemplate;

    public VotoProducer(KafkaTemplate<String, Voto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviar(Voto voto) {
        var message = MessageBuilder
                .withPayload(voto)
                .setHeader(TOPIC, topicoVotos)
                .setHeader(MESSAGE_KEY, voto.getId())
                .build();
        kafkaTemplate.send(message)
                .addCallback(sucesso(voto), falha(voto));
    }

    private SuccessCallback<SendResult<String, Voto>> sucesso(Voto voto) {
        return result -> LOGGER.debug("Voto enviado: '{}', no tópico {}, com offset {} e na partição {}",
                voto,
                result.getProducerRecord().topic(),
                result.getRecordMetadata().offset(),
                result.getRecordMetadata().partition());
    }

    private FailureCallback falha(Voto voto) {
        return error -> LOGGER.error("Erro ao enviar {}", voto, error);
    }
}
