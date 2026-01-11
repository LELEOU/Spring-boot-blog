# 📱 Blog API - Mini Rede Social

Um projeto de API REST para um blog/mini rede social feito com Spring Boot. Os usuários podem se cadastrar, fazer login, criar posts e comentar nas publicações dos outros!

## 🎯 O que esse projeto faz?

É uma API completa de blog com autenticação. Você pode:
- ✅ Criar sua conta
- ✅ Fazer login e receber um token JWT
- ✅ Criar posts (só quem está logado!)
- ✅ Comentar nos posts de outras pessoas
- ✅ Editar e deletar seus próprios posts
- ✅ Ver todos os posts e comentários

## 🔒 Segurança

O projeto usa **Spring Security** com autenticação JWT. Rotas protegidas exigem que você esteja logado e envie o token no header da requisição.

### Rotas públicas (não precisa de login):
- Ver todos os posts
- Ver comentários de um post
- Cadastrar nova conta
- Fazer login

### Rotas protegidas (precisa estar logado):
- Criar post
- Editar post (só o autor)
- Deletar post (só o autor)
- Comentar em posts
- Deletar comentário (só o autor)

## 🛠️ Tecnologias

- **Java 17**
- **Spring Boot 3.2**
- **Spring Security** - Autenticação e autorização
- **JWT (JSON Web Token)** - Para gerar os tokens
- **Spring Data JPA** - ORM para banco de dados
- **H2 Database** - Banco em memória
- **Lombok** - Reduz código boilerplate
- **Maven** - Gerenciador de dependências

## 📊 Relacionamentos entre tabelas

- Um **Usuário** pode ter vários **Posts** (1:N)
- Um **Post** pertence a um **Usuário** (N:1)
- Um **Post** pode ter vários **Comentários** (1:N)
- Um **Comentário** pertence a um **Post** e a um **Usuário** (N:1)

## 🚀 Como rodar

### Pré-requisitos
- Java 17+
- Maven

### Executando

```bash
mvn spring-boot:run
```

A API vai rodar em `http://localhost:8080`

## 📝 Como usar

### 1. Cadastrar um usuário

```http
POST /api/auth/cadastro
Content-Type: application/json

{
  "nome": "João Silva",
  "email": "joao@email.com",
  "senha": "senha123"
}
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tipo": "Bearer",
  "usuarioId": 1,
  "nome": "João Silva",
  "email": "joao@email.com"
}
```

### 2. Fazer login

```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "joao@email.com",
  "senha": "senha123"
}
```

### 3. Criar um post (precisa estar logado!)

```http
POST /api/posts
Authorization: Bearer SEU_TOKEN_AQUI
Content-Type: application/json

{
  "titulo": "Meu primeiro post!",
  "conteudo": "Este é o conteúdo do meu post. Muito legal!"
}
```

### 4. Listar todos os posts

```http
GET /api/posts
```

### 5. Ver posts de um usuário específico

```http
GET /api/posts?autorId=1
```

### 6. Buscar posts por título

```http
GET /api/posts?titulo=primeiro
```

### 7. Comentar em um post (precisa estar logado!)

```http
POST /api/posts/1/comentarios
Authorization: Bearer SEU_TOKEN_AQUI
Content-Type: application/json

{
  "conteudo": "Adorei seu post! Muito interessante."
}
```

### 8. Ver comentários de um post

```http
GET /api/posts/1/comentarios
```

### 9. Atualizar seu post

```http
PUT /api/posts/1
Authorization: Bearer SEU_TOKEN_AQUI
Content-Type: application/json

{
  "titulo": "Meu primeiro post ATUALIZADO!",
  "conteudo": "Conteúdo atualizado aqui."
}
```

### 10. Deletar seu post

```http
DELETE /api/posts/1
Authorization: Bearer SEU_TOKEN_AQUI
```

### 11. Deletar seu comentário

```http
DELETE /api/comentarios/1
Authorization: Bearer SEU_TOKEN_AQUI
```

## 💡 Dicas importantes

- Guarde o token que você recebe no cadastro/login
- Use o token no header `Authorization: Bearer SEU_TOKEN`
- O token expira em 24 horas (configurável)
- Você só pode editar/deletar seus próprios posts e comentários
- Posts e comentários mostram o nome do autor automaticamente

## 🗄️ Banco de dados

O projeto usa H2, um banco em memória. Acesse o console:
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:blogdb`
- Username: `sa`
- Password: (vazio)

## 📁 Estrutura do projeto

```
src/main/java/com/alura/blog/
├── controller/      # Endpoints da API (Auth, Post, Comentario)
├── dto/            # Objetos de transferência de dados
├── model/          # Entidades (Usuario, Post, Comentario)
├── repository/     # Repositórios JPA
├── security/       # Configurações de segurança e JWT
└── service/        # Lógica de negócio
```

## 🎨 Possíveis melhorias

- Adicionar curtidas nos posts
- Sistema de seguir outros usuários
- Feed personalizado
- Upload de imagens
- Paginação nos posts
- Categorias/Tags
- Notificações
- Usar PostgreSQL/MySQL em produção
- Deploy na nuvem (Heroku, AWS, Azure)

## 📄 Licença

Projeto livre para estudos. Divirta-se! 🚀

---

Feito com ☕ Spring Boot + Spring Security
