package com.example.cadastro.exception;

public class CampoObrigatorioException extends RuntimeException {
    public CampoObrigatorioException(String campo) {
        super("Campo obrigatório não informado: " + campo);
    }
}