package org.exemplo.votacoes.clientes;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "associado", path = "/users", url = "${associado.url}")
public interface AssociadoClient {

    @GetMapping("/{cpf}")
    String verificar(@PathVariable("cpf") String cpf);

}
