package com.example.cadastro;

import com.example.cadastro.service.ValidacaoSenhaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes Unitários — ValidacaoSenhaService
 * RF02 — Regras da Senha (Caixa Cinza)
 *
 * Caixa Cinza: conhecemos a estrutura interna do service,
 * mas validamos apenas as entradas e saídas esperadas.
 */
@DisplayName("RF02 — Validação de Senha")
class ValidacaoSenhaServiceTest {

    private ValidacaoSenhaService service;

    @BeforeEach
    void setUp() {
        service = new ValidacaoSenhaService();
    }

    // ─── Senhas VÁLIDAS ────────────────────────────────────────

    @Test
    @DisplayName("Deve aceitar senha com 10 caracteres válidos")
    void deveAceitarSenhaValida10Chars() {
        assertTrue(service.validarSenha("Java@12345"));
    }

    @Test
    @DisplayName("Deve aceitar senha com 12 caracteres válidos")
    void deveAceitarSenhaValida12Chars() {
        assertTrue(service.validarSenha("Java@1234567"));
    }

    @Test
    @DisplayName("Deve aceitar senha com diferentes símbolos especiais")
    void deveAceitarSenhaComSimbolosDiferentes() {
        assertTrue(service.validarSenha("Abc#1234567")); // #
        assertTrue(service.validarSenha("Abc!1234567")); // !
        assertTrue(service.validarSenha("Abc$1234567")); // $
    }

    // ─── Senha NULA e VAZIA ────────────────────────────────────

    @Test
    @DisplayName("Deve rejeitar senha nula — RF03")
    void deveRejeitarSenhaNula() {
        assertFalse(service.validarSenha(null));
    }

    @Test
    @DisplayName("Deve rejeitar senha vazia — RF03")
    void deveRejeitarSenhaVazia() {
        assertFalse(service.validarSenha(""));
    }

    @Test
    @DisplayName("Deve rejeitar senha com apenas espaços — RF03")
    void deveRejeitarSenhaComEspacos() {
        assertFalse(service.validarSenha("          "));
    }

    // ─── Tamanho inválido ──────────────────────────────────────

    @Test
    @DisplayName("Deve rejeitar senha com menos de 10 caracteres")
    void deveRejeitarSenhaCurta() {
        assertFalse(service.validarSenha("Java@123")); // 8 chars
    }

    @Test
    @DisplayName("Deve rejeitar senha com mais de 12 caracteres")
    void deveRejeitarSenhaLonga() {
        assertFalse(service.validarSenha("Java@12345678")); // 13 chars
    }

    @Test
    @DisplayName("Deve rejeitar senha com exatamente 9 caracteres")
    void deveRejeitarSenhaCom9Chars() {
        assertFalse(service.validarSenha("Java@1234")); // 9 chars
    }

    @Test
    @DisplayName("Deve rejeitar senha com exatamente 13 caracteres")
    void deveRejeitarSenhaCom13Chars() {
        assertFalse(service.validarSenha("Java@12345678")); // 13 chars
    }

    // ─── Falta de componentes obrigatórios ────────────────────

    @Test
    @DisplayName("Deve rejeitar senha sem números")
    void deveRejeitarSenhaSemNumeros() {
        assertFalse(service.validarSenha("JavaJava@@")); // sem dígito
    }

    @Test
    @DisplayName("Deve rejeitar senha sem letras")
    void deveRejeitarSenhaSemLetras() {
        assertFalse(service.validarSenha("1234567@90")); // sem letra
    }

    @Test
    @DisplayName("Deve rejeitar senha sem caractere especial")
    void deveRejeitarSenhaSemEspecial() {
        assertFalse(service.validarSenha("Java123456")); // sem especial
    }

    @Test
    @DisplayName("Deve rejeitar senha apenas com números")
    void deveRejeitarSenhaSoNumeros() {
        assertFalse(service.validarSenha("1234567890"));
    }

    @Test
    @DisplayName("Deve rejeitar senha apenas com letras")
    void deveRejeitarSenhaSoLetras() {
        assertFalse(service.validarSenha("JavaJavaJa"));
    }
}