package com.byt3social.prospeccao.exceptions;

public class FailedToDeliverEmailException extends RuntimeException {
    public FailedToDeliverEmailException() {
        super("Não foi possível enviar o email");
    }
}
