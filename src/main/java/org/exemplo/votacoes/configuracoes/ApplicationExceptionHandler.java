package org.exemplo.votacoes.configuracoes;

import org.exemplo.votacoes.api.VotacaoController;
import org.exemplo.votacoes.dominios.exception.AssociadoNaoHabilitadoException;
import org.exemplo.votacoes.dominios.exception.VotacaoException;
import org.exemplo.votacoes.dominios.exception.VotacaoNaoEncontradaException;
import org.exemplo.votacoes.dominios.exception.VotoJaContabilizadoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ApplicationExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ExceptionHandler(VotacaoNaoEncontradaException.class)
    @ResponseStatus(NOT_FOUND)
    public ResponseEntity<MensagemErro> notFound(VotacaoNaoEncontradaException exception, HttpServletRequest request) {
        LOGGER.error("", exception);
        return retorno("Não encontrado", exception.getMessage(), request, NOT_FOUND);
    }

    @ExceptionHandler(VotoJaContabilizadoException.class)
    @ResponseStatus(CONFLICT)
    public ResponseEntity<MensagemErro> business(VotoJaContabilizadoException exception, HttpServletRequest request) {
        LOGGER.error("", exception);
        return retorno("Conflito", exception.getMessage(), request, CONFLICT);
    }

    @ExceptionHandler(AssociadoNaoHabilitadoException.class)
    @ResponseStatus(FORBIDDEN)
    public ResponseEntity<MensagemErro> business(AssociadoNaoHabilitadoException exception, HttpServletRequest request) {
        LOGGER.error("", exception);
        return retorno("Não permitido", exception.getMessage(), request, FORBIDDEN);
    }

    @ExceptionHandler(VotacaoException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<MensagemErro> business(VotacaoException exception, HttpServletRequest request) {
        LOGGER.error("", exception);
        return retorno("Erro na requisição", exception.getMessage(), request, BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ResponseEntity<MensagemErro> interno(Exception exception, HttpServletRequest request) {
        LOGGER.error("", exception);
        return retorno("Erro inesperado", exception.getMessage(), request, INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<MensagemErro> retorno(String erro, String message, HttpServletRequest request, HttpStatus httpStatus) {
        return ResponseEntity
                .status(httpStatus)
                .body(new MensagemErro(erro, message, request.getRequestURI(), httpStatus.value()));
    }
}

