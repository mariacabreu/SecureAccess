package com.example.cadastro.exception;

public class UsuarioDuplicadoException extends RuntimeException {
    public UsuarioDuplicadoException(String campo, String valor) {
        super("Já existe um usuário com " + campo + ": " + valor);
    }
}