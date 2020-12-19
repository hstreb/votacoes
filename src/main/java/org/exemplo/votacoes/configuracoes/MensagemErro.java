package org.exemplo.votacoes.configuracoes;

import java.time.LocalDateTime;

public class MensagemErro {

    private String erro;
    private String mensagem;
    private String caminho;
    private int codigoResposta;
    private LocalDateTime horario;

    public MensagemErro(String erro, String mensagem, String caminho, int codigoResposta) {
        this.erro = erro;
        this.mensagem = mensagem;
        this.caminho = caminho;
        this.codigoResposta = codigoResposta;
        this.horario = LocalDateTime.now();
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public int getCodigoResposta() {
        return codigoResposta;
    }

    public void setCodigoResposta(int codigoResposta) {
        this.codigoResposta = codigoResposta;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }
}
