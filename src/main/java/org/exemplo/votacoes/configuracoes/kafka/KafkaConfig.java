package org.exemplo.votacoes.configuracoes.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Value("${topicos.votacoes.nome}")
    private String topicoVotacoes;

    @Value("${topicos.votacoes.particoes}")
    private Integer topicoVotacoesParticoes;

    @Value("${topicos.votacoes.replicas}")
    private Integer topicoVotacoesReplicas;

    @Value("${topicos.votos.nome}")
    private String topicoVotos;

    @Value("${topicos.votos.particoes}")
    private Integer topicoVotosParticoes;

    @Value("${topicos.votos.replicas}")
    private Integer topicoVotosReplicas;

    @Bean
    public NewTopic topicoVotacoes() {
        return TopicBuilder.name(topicoVotacoes)
                .partitions(topicoVotacoesParticoes)
                .replicas(topicoVotacoesReplicas)
                .compact()
                .build();
    }

    @Bean
    public NewTopic topicoVotos() {
        return TopicBuilder.name(topicoVotos)
                .partitions(topicoVotosParticoes)
                .replicas(topicoVotosReplicas)
                .compact()
                .build();
    }
}
