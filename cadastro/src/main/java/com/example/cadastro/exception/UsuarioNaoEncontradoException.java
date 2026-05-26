package com.example.cadastro.exception;

public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException(String login) {
        super("Usuário não encontrado: " + login);
    }
}