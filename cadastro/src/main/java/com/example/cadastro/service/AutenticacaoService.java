package com.example.cadastro.service;

import com.example.cadastro.entity.NivelUsuario;
import com.example.cadastro.entity.Usuario;
import com.example.cadastro.exception.*;
import com.example.cadastro.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

/**
 * Serviço principal de autenticação.
 * Implementa: RF01, RF03, RF04, RF05, RF06, RF07
 */
@Service
public class AutenticacaoService {

    private final UsuarioRepository repository;
    private final ValidacaoSenhaService validacaoSenhaService;

    public AutenticacaoService(UsuarioRepository repository,
                               ValidacaoSenhaService validacaoSenhaService) {
        this.repository = repository;
        this.validacaoSenhaService = validacaoSenhaService;
    }

    // ─────────────────────────────────────────────────
    //  RF01 — Cadastro de Usuário
    // ─────────────────────────────────────────────────

    public Usuario cadastrar(String nome, String login, String senha,
                             String email, NivelUsuario nivel) {

        // RF03 — Campos obrigatórios
        validarCampoObrigatorio(nome,  "nome");
        validarCampoObrigatorio(login, "login");
        validarCampoObrigatorio(senha, "senha");
        validarCampoObrigatorio(email, "email");

        // RF02 — Regras da senha
        if (!validacaoSenhaService.validarSenha(senha)) {
            throw new SenhaFracaException();
        }

        // Unicidade
        if (repository.existsByLogin(login)) {
            throw new UsuarioDuplicadoException("login", login);
        }
        if (repository.existsByEmail(email)) {
            throw new UsuarioDuplicadoException("e-mail", email);
        }

        Usuario usuario = new Usuario(nome, login, senha, email,
                nivel != null ? nivel : NivelUsuario.CLIENTE);

        return repository.save(usuario);
    }

    // ─────────────────────────────────────────────────
    //  RF04 — Login
    // ─────────────────────────────────────────────────

    public Usuario autenticar(String login, String senha) {

        // RF03 — Campos obrigatórios
        validarCampoObrigatorio(login, "login");
        validarCampoObrigatorio(senha, "senha");

        // RF05 — Usuário inexistente
        Usuario usuario = repository.findByLogin(login)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(login));

        // RF06 — Conta bloqueada
        if (usuario.isBloqueado()) {
            throw new ContaBloqueadaException(login);
        }

        // RF05 — Senha inválida
        if (!usuario.getSenha().equals(senha)) {
            usuario.incrementarTentativa();
            repository.save(usuario);

            if (usuario.isBloqueado()) {
                throw new ContaBloqueadaException(login);
            }
            throw new SenhaInvalidaException();
        }

        // Login bem-sucedido: reseta tentativas
        usuario.resetarTentativas();
        repository.save(usuario);

        return usuario;
    }

    // ─────────────────────────────────────────────────
    //  Alteração de Senha
    // ─────────────────────────────────────────────────

    public void alterarSenha(String login, String senhaAtual, String novaSenha) {

        validarCampoObrigatorio(login,     "login");
        validarCampoObrigatorio(senhaAtual, "senhaAtual");
        validarCampoObrigatorio(novaSenha,  "novaSenha");

        Usuario usuario = autenticar(login, senhaAtual);

        if (!validacaoSenhaService.validarSenha(novaSenha)) {
            throw new SenhaFracaException();
        }

        usuario.setSenha(novaSenha);
        repository.save(usuario);
    }

    private void validarCampoObrigatorio(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new CampoObrigatorioException(campo);
        }
    }
}