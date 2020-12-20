package org.exemplo.votacoes.api;

import io.swagger.v3.oas.annotations.Operation;
import org.exemplo.votacoes.api.entrada.AlteracaoVotacao;
import org.exemplo.votacoes.api.entrada.CriacaoVotacao;
import org.exemplo.votacoes.api.entrada.CriacaoVoto;
import org.exemplo.votacoes.dominios.EstadoVotacao;
import org.exemplo.votacoes.dominios.Votacao;
import org.exemplo.votacoes.dominios.Voto;
import org.exemplo.votacoes.dominios.exception.VotacaoException;
import org.exemplo.votacoes.servicos.VotacaoService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(path = "/v1/votacoes")
public class VotacaoController {

    private final VotacaoService votacaoService;

    public VotacaoController(VotacaoService votacaoService) {
        this.votacaoService = votacaoService;
    }

    @Operation(summary = "Adiciona uma nova votação", description = "Adiciona uma nova votação no estado 'NAO_INICIADA'")
    @PostMapping
    @ResponseStatus(CREATED)
    public Votacao criar(@Valid @RequestBody CriacaoVotacao votacao) throws VotacaoException {
        return votacaoService.criar(votacao.getPauta());
    }

    @Operation(summary = "Altera uma votação", description = "Altera uma votação, caso passe o estado 'EM_ANDAMENTO' abre a sessão de votação, se passar o estado 'ENCERRADA' finaliza a votação e contabiliza os votos")
    @PatchMapping("/{id}")
    public Votacao alterar(@PathVariable String id, @Valid @RequestBody AlteracaoVotacao votacao) throws VotacaoException {
        if (votacao.getEstado().equals(EstadoVotacao.EM_ANDAMENTO)) {
            return votacaoService.abrir(id, Optional.ofNullable(votacao.getDuracao()));
        }
        return votacaoService.encerrar(id);
    }

    @Operation(summary = "Adiciona um novo voto a uma votação", description = "Adiciona um novo voto de um cooperado para uma votação somente quando a votação esta no estado 'EM_ANDAMENTO'")
    @PostMapping("/{id}/votos")
    @ResponseStatus(CREATED)
    public Voto votar(@PathVariable String id, @Valid @RequestBody CriacaoVoto voto) throws VotacaoException {
        return votacaoService.votar(id, voto.getAssociado(), voto.getEscolha());
    }
}
