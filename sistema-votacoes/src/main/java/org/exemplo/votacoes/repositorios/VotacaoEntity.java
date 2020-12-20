package org.exemplo.votacoes.repositorios;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "votacoes")
public class VotacaoEntity {

    @Id
    private String id;
    private String pauta;
    private String estado;
    private Long duracao;
    private Long totalSim;
    private Long totalNao;
    private LocalDateTime horarioCriacao;
    private LocalDateTime horarioInicio;
    private LocalDateTime horarioFim;

    public VotacaoEntity() {
    }

    public VotacaoEntity(String id,
                         String pauta,
                         String estado,
                         Long duracao,
                         Long totalSim,
                         Long totalNao,
                         LocalDateTime horarioCriacao,
                         LocalDateTime horarioInicio,
                         LocalDateTime horarioFim) {
        this.id = id;
        this.pauta = pauta;
        this.estado = estado;
        this.duracao = duracao;
        this.totalSim = totalSim;
        this.totalNao = totalNao;
        this.horarioCriacao = horarioCriacao;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPauta() {
        return pauta;
    }

    public void setPauta(String pauta) {
        this.pauta = pauta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getDuracao() {
        return duracao;
    }

    public void setDuracao(Long duracao) {
        this.duracao = duracao;
    }

    public Long getTotalSim() {
        return totalSim;
    }

    public void setTotalSim(Long totalSim) {
        this.totalSim = totalSim;
    }

    public Long getTotalNao() {
        return totalNao;
    }

    public void setTotalNao(Long totalNao) {
        this.totalNao = totalNao;
    }

    public LocalDateTime getHorarioCriacao() {
        return horarioCriacao;
    }

    public void setHorarioCriacao(LocalDateTime horarioCriacao) {
        this.horarioCriacao = horarioCriacao;
    }

    public LocalDateTime getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(LocalDateTime horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public LocalDateTime getHorarioFim() {
        return horarioFim;
    }

    public void setHorarioFim(LocalDateTime horarioFim) {
        this.horarioFim = horarioFim;
    }
}
