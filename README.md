# TJW Project – Sistema Acadêmico

Aplicação completa em Spring Boot 3 (Java 17) com Spring MVC, Spring Data JPA, Spring Security e Thymeleaf para gerenciar **Alunos, Disciplinas e Matrículas**. O sistema segue o enunciado enviado, incluindo autenticação com dois perfis (ADMIN e SECRETARIA), auditoria, validações e telas com Thymeleaf.

## Requisitos

- JDK 17+
- Maven 3.9+
- MySQL 8 (usuário com permissão de criação de schema)

## Configuração

1. Ajuste as credenciais em `src/main/resources/application.properties` se necessário. O arquivo atual usa:
   ```
   spring.datasource.url=jdbc:mysql://localhost:3306/tjw_academico?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
   spring.datasource.username= seu root
   spring.datasource.password= sua senha 
   ```
2. Certifique-se de que o usuário possui permissão `CREATE`/`ALTER` para que o Hibernate possa gerar e atualizar as tabelas (`spring.jpa.hibernate.ddl-auto=update`).

## Execução

```bash
mvn clean package          # Baixa dependências e gera o artefato (tests skip opcionais)
mvn spring-boot:run        # Sobe a aplicação em http://localhost:8080
```



## Usuários Padrão

Dois usuários são criados automaticamente via `DataLoader`:

| Login       | Senha      | Perfil            | Permissões                                   |
|-------------|------------|-------------------|----------------------------------------------|
| `admin`     | `admin123` | `ROLE_ADMIN`      | CRUD completo (alunos, disciplinas, matrículas) |
| `secretaria`| `secret123`| `ROLE_SECRETARIA` | CRUD de alunos e matrículas                   |

As senhas são armazenadas com BCrypt

## Funcionalidades

- **Autenticação**: login customizado `/login`, armazenamento BCrypt, logout com `/logout`.
- **Autorização**: filtros por URL (admin obrigatório em `/disciplinas/**`, secretária com acesso parcial).
- **Domínio Acadêmico**:
  - Alunos (nome, matrícula única, e-mail, data de nascimento e status ATIVO/INATIVO).
  - Disciplinas (código único, carga horária, semestre).
  - Matrículas (relacionamentos ManyToOne, status CURSANDO/APROVADO/REPROVADO/TRANCADO, nota opcional, bloqueio de duplicidade).
- **Auditoria**: todas as entidades principais registram `criado_por` e `criado_em` via Spring Data Auditing.
- **Frontend (Thymeleaf)**: telas para login, dashboard inicial e CRUD completo com mensagens de validação.

## Estrutura

- `src/main/java/com/example/tjwproject/model`: entidades e enums.
- `src/main/java/com/example/tjwproject/repository`: repositórios JPA.
- `src/main/java/com/example/tjwproject/service`: regras de negócio e segurança.
- `src/main/java/com/example/tjwproject/controller`: controladores MVC.
- `src/main/resources/templates`: páginas Thymeleaf (com fragmento `fragments.html`).
- `src/main/resources/static`: estilos e assets.

## Teste Rápido

Após iniciar a aplicação, acesse:

1. `http://localhost:8080/login`
2. Autentique com `admin/admin123` ou `secretaria/secret123`.
3. Utilize o menu principal para navegar por alunos, disciplinas e matrículas.


