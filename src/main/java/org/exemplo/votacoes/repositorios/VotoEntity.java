package org.exemplo.votacoes.repositorios;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "votos")
public class VotoEntity {

    @Id
    private String id;
    private String votacao;
    private String associado;
    private String escolha;
    private LocalDateTime horarioCriacao;

    public VotoEntity() {
    }

    public VotoEntity(String id, String votacao, String associado, String escolha, LocalDateTime horarioCriacao) {
        this.id = id;
        this.votacao = votacao;
        this.associado = associado;
        this.escolha = escolha;
        this.horarioCriacao = horarioCriacao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVotacao() {
        return votacao;
    }

    public void setVotacao(String votacao) {
        this.votacao = votacao;
    }

    public String getAssociado() {
        return associado;
    }

    public void setAssociado(String associado) {
        this.associado = associado;
    }

    public String getEscolha() {
        return escolha;
    }

    public void setEscolha(String escolha) {
        this.escolha = escolha;
    }

    public LocalDateTime getHorarioCriacao() {
        return horarioCriacao;
    }

    public void setHorarioCriacao(LocalDateTime horarioCriacao) {
        this.horarioCriacao = horarioCriacao;
    }
}
