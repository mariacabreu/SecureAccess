package com.example.cadastro.service;

import org.springframework.stereotype.Service;

/**
 * RF02 — Regras de validação da senha:
 *  - Possuir letras
 *  - Possuir números
 *  - Possuir caracteres especiais (!@#$%&*())
 *  - Mínimo 10 caracteres
 *  - Máximo 12 caracteres
 */
@Service
public class ValidacaoSenhaService {

    public boolean validarSenha(String senha) {
        // RF03 — nulos e vazios não permitidos
        if (senha == null || senha.isBlank()) {
            return false;
        }

        // RF02 — tamanho entre 10 e 12
        if (senha.length() < 10 || senha.length() > 12) {
            return false;
        }

        boolean possuiNumero   = senha.matches(".*\\d.*");
        boolean possuiLetra    = senha.matches(".*[a-zA-Z].*");
        boolean possuiEspecial = senha.matches(".*[!@#$%&*()].*");

        return possuiNumero && possuiLetra && possuiEspecial;
    }
}