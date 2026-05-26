package com.example.cadastro.exception;

public class ContaBloqueadaException extends RuntimeException {
    public ContaBloqueadaException(String login) {
        super("Conta bloqueada após 3 tentativas inválidas: " + login);
    }
}