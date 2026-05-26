package com.example.cadastro.entity;

import jakarta.persistence.*;

/**
 * Entidade que representa um usuário do sistema.
 * RF01 — Cadastro de Usuário
 * RF06 — Bloqueio de Conta (após 3 tentativas inválidas)
 * RF07 — Níveis de Usuário (ADMIN, GERENTE, CLIENTE)
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false, unique = true)
    private String email;

    // RF07 — Nível do usuário
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NivelUsuario nivel = NivelUsuario.CLIENTE;

    // RF06 — Controle de bloqueio
    private int tentativasInvalidas = 0;

    private boolean bloqueado = false;

    // ===================== Construtores =====================

    public Usuario() {}

    public Usuario(String nome, String login, String senha, String email, NivelUsuario nivel) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.email = email;
        this.nivel = nivel;
    }

    // ===================== Getters / Setters =====================

    public Long getId() { return id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public NivelUsuario getNivel() { return nivel; }
    public void setNivel(NivelUsuario nivel) { this.nivel = nivel; }

    public int getTentativasInvalidas() { return tentativasInvalidas; }
    public void setTentativasInvalidas(int tentativasInvalidas) {
        this.tentativasInvalidas = tentativasInvalidas;
    }

    public boolean isBloqueado() { return bloqueado; }
    public void setBloqueado(boolean bloqueado) { this.bloqueado = bloqueado; }

    /**
     * Incrementa tentativas e bloqueia após 3 falhas (RF06).
     */
    public void incrementarTentativa() {
        this.tentativasInvalidas++;
        if (this.tentativasInvalidas >= 3) {
            this.bloqueado = true;
        }
    }

    /**
     * Reseta tentativas após login bem-sucedido.
     */
    public void resetarTentativas() {
        this.tentativasInvalidas = 0;
        this.bloqueado = false;
    }
}