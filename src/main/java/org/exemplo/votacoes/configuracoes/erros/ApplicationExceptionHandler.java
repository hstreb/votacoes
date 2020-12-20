package org.exemplo.votacoes.configuracoes.erros;

import feign.FeignException;
import org.exemplo.votacoes.dominios.exception.AssociadoNaoHabilitadoException;
import org.exemplo.votacoes.dominios.exception.VotacaoException;
import org.exemplo.votacoes.dominios.exception.VotacaoNaoEncontradaException;
import org.exemplo.votacoes.dominios.exception.VotoJaContabilizadoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ApplicationExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ExceptionHandler(VotacaoNaoEncontradaException.class)
    @ResponseStatus(NOT_FOUND)
    public ResponseEntity<MensagemErro> notFound(VotacaoNaoEncontradaException exception, HttpServletRequest request) {
        LOGGER.error("", exception.getMessage());
        return retorno("Não encontrado", exception.getMessage(), request, NOT_FOUND);
    }

    @ExceptionHandler(VotoJaContabilizadoException.class)
    @ResponseStatus(CONFLICT)
    public ResponseEntity<MensagemErro> business(VotoJaContabilizadoException exception, HttpServletRequest request) {
        LOGGER.error("", exception.getMessage());
        return retorno("Conflito", exception.getMessage(), request, CONFLICT);
    }

    @ExceptionHandler(AssociadoNaoHabilitadoException.class)
    @ResponseStatus(FORBIDDEN)
    public ResponseEntity<MensagemErro> business(AssociadoNaoHabilitadoException exception, HttpServletRequest request) {
        LOGGER.error("", exception.getMessage());
        return retorno("Não permitido", exception.getMessage(), request, FORBIDDEN);
    }

    @ExceptionHandler(VotacaoException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<MensagemErro> business(VotacaoException exception, HttpServletRequest request) {
        LOGGER.error("", exception.getMessage());
        return retorno("Erro na requisição", exception.getMessage(), request, BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<MensagemErro> regrasValidacao(ConstraintViolationException exception, HttpServletRequest request) {
        LOGGER.error("", exception.getMessage());
        var mensagem = exception.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));
        return retorno("Erro na requisição", mensagem, request, BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<MensagemErro> argumentosNaoValidos(MethodArgumentNotValidException exception, HttpServletRequest request) {
        LOGGER.error("", exception.getMessage());
        var mensagem = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(","));
        return retorno("Erro na requisição", mensagem, request, BAD_REQUEST);
    }

    @ExceptionHandler(FeignException.NotFound.class)
    @ResponseStatus(NOT_FOUND)
    public ResponseEntity<MensagemErro> interno(FeignException.NotFound exception, HttpServletRequest request) {
        LOGGER.error("", exception.getMessage());
        return retorno("Não encontrado", "Associado não encontrado!", request, NOT_FOUND);
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

