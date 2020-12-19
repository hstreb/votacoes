package org.exemplo.votacoes.api.entrada;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CriacaoVotacao {
    @NotNull(message = "O campo 'pauta' não pode ser nulo!")
    @NotEmpty(message = "O campo 'pauta' não pode ser vazio!")
    @NotBlank(message = "O campo 'pauta' não pode ser em branco!")
    @Max(message = "O campo 'pauta' não pode ter mais que 1024 caracteres!", value = 1024)
    private String pauta;

    public String getPauta() {
        return pauta;
    }

    public void setPauta(String pauta) {
        this.pauta = pauta;
    }

    @Override
    public String toString() {
        return "CriacaoVotacao{" +
                "pauta='" + pauta + '\'' +
                '}';
    }
}
