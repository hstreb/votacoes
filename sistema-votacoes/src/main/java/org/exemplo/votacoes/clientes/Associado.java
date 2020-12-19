package org.exemplo.votacoes.clientes;

public class Associado {
    private AssociadoStatus status;

    public Associado() {
    }

    public Associado(AssociadoStatus status) {
        this.status = status;
    }

    public AssociadoStatus getStatus() {
        return status;
    }

    public void setStatus(AssociadoStatus status) {
        this.status = status;
    }
}
