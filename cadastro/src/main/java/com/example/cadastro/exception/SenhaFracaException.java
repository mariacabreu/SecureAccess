package com.example.cadastro.exception;

public class SenhaFracaException extends RuntimeException {
    public SenhaFracaException() {
        super("A senha não atende aos requisitos de segurança: " +
                "mínimo 10 e máximo 12 caracteres, com letras, " +
                "números e caracteres especiais (!@#$%&*()).");
    }
}