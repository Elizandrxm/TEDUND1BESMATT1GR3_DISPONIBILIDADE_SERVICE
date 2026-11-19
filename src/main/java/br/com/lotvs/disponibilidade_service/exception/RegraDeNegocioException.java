package br.com.lotvs.disponibilidade_service.exception;

import org.springframework.http.HttpStatus;

public class RegraDeNegocioException extends RuntimeException {

    private final HttpStatus status;

    public RegraDeNegocioException(String mensagem, HttpStatus status) {
        super(mensagem);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
