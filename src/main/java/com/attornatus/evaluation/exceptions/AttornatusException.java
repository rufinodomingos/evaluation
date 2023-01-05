package com.attornatus.evaluation.exceptions;

public class AttornatusException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AttornatusException(String mensagem) {
        super(mensagem);
    }
}
