package com.example.cadastro.controller;

import com.example.cadastro.entity.NivelUsuario;
import com.example.cadastro.entity.Usuario;
import com.example.cadastro.exception.*;
import com.example.cadastro.service.AutenticacaoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller MVC para as telas de autenticação.
 */
@Controller
public class AutenticacaoController {

    private final AutenticacaoService autenticacaoService;

    public AutenticacaoController(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

    // ─── Tela de Login ────────────────────────────────────────
    @GetMapping({"/", "/login"})
    public String paginaLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String realizarLogin(@RequestParam String login,
                                @RequestParam String senha,
                                HttpSession session,
                                Model model) {
        try {
            Usuario usuario = autenticacaoService.autenticar(login, senha);
            session.setAttribute("usuarioLogado", usuario);
            return "redirect:/dashboard";

        } catch (ContaBloqueadaException e) {
            model.addAttribute("erro", "Conta bloqueada após 3 tentativas. Contate o administrador.");
        } catch (UsuarioNaoEncontradoException e) {
            model.addAttribute("erro", "Usuário não encontrado.");
        } catch (SenhaInvalidaException e) {
            model.addAttribute("erro", "Senha incorreta.");
        } catch (CampoObrigatorioException e) {
            model.addAttribute("erro", e.getMessage());
        }
        return "login";
    }

    // ─── Dashboard (pós-login) ─────────────────────────────────
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        model.addAttribute("usuario", usuario);
        return "dashboard";
    }

    // ─── Logout ───────────────────────────────────────────────
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // ─── Cadastro ─────────────────────────────────────────────
    @GetMapping("/cadastro")
    public String paginaCadastro() {
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String realizarCadastro(@RequestParam String nome,
                                   @RequestParam String login,
                                   @RequestParam String senha,
                                   @RequestParam String email,
                                   @RequestParam(defaultValue = "CLIENTE") String nivel,
                                   Model model) {
        try {
            NivelUsuario nivelUsuario = NivelUsuario.valueOf(nivel.toUpperCase());
            autenticacaoService.cadastrar(nome, login, senha, email, nivelUsuario);
            model.addAttribute("sucesso", "Usuário cadastrado com sucesso! Faça o login.");
            return "login";

        } catch (SenhaFracaException e) {
            model.addAttribute("erro", "Senha fraca: use 10-12 caracteres com letras, números e símbolos (!@#$%&*()).");
        } catch (UsuarioDuplicadoException e) {
            model.addAttribute("erro", e.getMessage());
        } catch (CampoObrigatorioException e) {
            model.addAttribute("erro", e.getMessage());
        }
        return "cadastro";
    }

    // ─── Alteração de Senha ───────────────────────────────────
    @GetMapping("/alterar-senha")
    public String paginaAlterarSenha(HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null) return "redirect:/login";
        return "alterar-senha";
    }

    @PostMapping("/alterar-senha")
    public String realizarAlteracaoSenha(@RequestParam String login,
                                         @RequestParam String senhaAtual,
                                         @RequestParam String novaSenha,
                                         Model model) {
        try {
            autenticacaoService.alterarSenha(login, senhaAtual, novaSenha);
            model.addAttribute("sucesso", "Senha alterada com sucesso!");
        } catch (SenhaFracaException e) {
            model.addAttribute("erro", "Nova senha fraca: use 10-12 caracteres com letras, números e símbolos.");
        } catch (SenhaInvalidaException e) {
            model.addAttribute("erro", "Senha atual incorreta.");
        } catch (Exception e) {
            model.addAttribute("erro", e.getMessage());
        }
        return "alterar-senha";
    }
}