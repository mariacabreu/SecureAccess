# 🔐 SecureAccess

> Sistema de autenticação corporativa com cadastro de usuários, controle de acesso por níveis e bloqueio automático de conta.

---

## 📋 Sobre o Projeto

O **SecureAccess** é um módulo de autenticação desenvolvido para a empresa fictícia *SecureAccess Systems* como parte de uma atividade acadêmica de Testes Automatizados em Java Web.

O sistema permite cadastrar usuários, realizar login com validações de segurança, bloquear contas após tentativas inválidas e controlar o nível de acesso de cada usuário (Admin, Gerente ou Cliente).

---

## 🚀 Tecnologias Utilizadas

| Tecnologia | Versão | Função |
|---|---|---|
| Java | 17+ | Linguagem principal |
| Spring Boot | 3.2.0 | Framework web |
| Spring Data JPA | — | Acesso ao banco de dados |
| Thymeleaf | — | Templates HTML |
| MySQL | 8+ | Banco de dados |
| JUnit 5 | — | Testes automatizados |
| Maven | — | Gerenciador de dependências |

---

## ⚙️ Pré-requisitos

- Java 17 ou superior instalado
- MySQL 8+ instalado e rodando
- IntelliJ IDEA (ou outra IDE Java)
- Maven (já incluso via `mvnw`)

---

## 🛠️ Como Rodar

### 1. Clone ou baixe o projeto

```bash
git clone https://github.com/seu-usuario/secureaccess.git
cd secureaccess
```

### 2. Crie o banco de dados no MySQL

```sql
CREATE DATABASE caixa_cinza;
```

### 3. Configure o `application.properties`

Edite o arquivo `src/main/resources/application.properties` com suas credenciais:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/caixa_cinza
spring.datasource.username=root
spring.datasource.password=SUA_SENHA_AQUI
```

### 4. Rode a aplicação

**Pelo IntelliJ:** Clique no botão ▶ ao lado de `CadastroApplication.java`

**Pelo terminal:**
```bash
./mvnw spring-boot:run
```

### 5. Acesse no navegador

```
http://localhost:8080/cadastro   → Criar novo usuário
http://localhost:8080/login      → Entrar no sistema
http://localhost:8080/dashboard  → Painel do usuário (requer login)
```

---

## 📁 Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/example/cadastro/
│   │   ├── entity/          → Usuario.java, NivelUsuario.java
│   │   ├── repository/      → UsuarioRepository.java
│   │   ├── service/         → AutenticacaoService.java, ValidacaoSenhaService.java
│   │   ├── exception/       → 6 exceções customizadas
│   │   ├── controller/      → AutenticacaoController.java
│   │   └── CadastroApplication.java
│   └── resources/
│       ├── application.properties
│       └── templates/       → login.html, cadastro.html, dashboard.html, alterar-senha.html
└── test/
    └── java/com/example/cadastro/
        └── ValidacaoSenhaServiceTest.java
```

---

## ✅ Requisitos Funcionais Implementados

| RF | Descrição | Status |
|---|---|---|
| RF01 | Cadastro de usuário (nome, login, senha, email) | ✅ |
| RF02 | Regras de senha (letras + números + especiais, 10-12 chars) | ✅ |
| RF03 | Campos obrigatórios não podem ser nulos ou vazios | ✅ |
| RF04 | Login com validação de credenciais | ✅ |
| RF05 | Tratamento de exceções (usuário inexistente, senha inválida, etc.) | ✅ |
| RF06 | Bloqueio de conta após 3 tentativas inválidas | ✅ |
| RF07 | Níveis de usuário: ADMIN, GERENTE, CLIENTE | ✅ |

---

## 🧪 Testes Automatizados

Os testes utilizam **JUnit 5** com abordagem de **caixa cinza** — o conhecimento interno dos services é usado para definir os cenários, mas apenas entradas e saídas são validadas.

### Rodar os testes

```bash
./mvnw test
```

### Cenários cobertos em `ValidacaoSenhaServiceTest`

- ✅ Senha válida com 10 caracteres
- ✅ Senha válida com 12 caracteres
- ✅ Rejeitar senha nula
- ✅ Rejeitar senha vazia
- ✅ Rejeitar senha com apenas espaços
- ✅ Rejeitar senha com menos de 10 caracteres
- ✅ Rejeitar senha com mais de 12 caracteres
- ✅ Rejeitar senha sem números
- ✅ Rejeitar senha sem letras
- ✅ Rejeitar senha sem caractere especial
- ✅ Rejeitar senha só com números
- ✅ Rejeitar senha só com letras

---

## 🔒 Regras de Senha

A senha deve atender **todos** os critérios abaixo:

- Mínimo de **10** caracteres
- Máximo de **12** caracteres
- Pelo menos **1 letra** (a-z ou A-Z)
- Pelo menos **1 número** (0-9)
- Pelo menos **1 caractere especial**: `! @ # $ % & * ( )`

**Exemplo válido:** `Java@12345`

---

## 👥 Níveis de Acesso

| Nível | Descrição |
|---|---|
| `ADMIN` | Acesso total ao sistema |
| `GERENTE` | Acesso intermediário |
| `CLIENTE` | Acesso básico (padrão no cadastro) |

---

## 📌 Observações

- A tabela `usuarios` é criada automaticamente pelo Hibernate na primeira execução (`ddl-auto=update`)
- Não é necessário criar tabelas manualmente no MySQL
- O console H2 foi removido — o banco utilizado é o MySQL

---

## 👨‍💻 Autor

Desenvolvido como atividade acadêmica de Testes Automatizados em Java Web com Spring Boot e JUnit 5.