# Tech Challenge 4ª Fase - Cliente

Este projeto é um microsserviço para cadastro e gestão de clientes, desenvolvido como parte do Tech Challenge da FIAP (4ª fase). Ele expõe uma API RESTful para operações de CRUD de clientes, incluindo validação de CPF e CEP, e integração com banco de dados relacional.

---

## Sumário

- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Como Executar Localmente](#como-executar-localmente)
- [Endpoints Principais](#endpoints-principais)
- [Testes](#testes)
- [Migrações de Banco](#migrações-de-banco)
- [Variáveis de Ambiente](#variáveis-de-ambiente)
- [Observações](#observações)

---

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- Flyway (migração de banco)
- MySQL (produção) / H2 (testes)
- JUnit 5, MockMvc (testes)
- Docker e Docker Compose

---

## Estrutura do Projeto

```
tech-challenge-4a-fase_cliente/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── br/
│   │   │       └── com/
│   │   │           └── fiap/
│   │   │               └── cliente/
│   │   │                   ├── controller/
│   │   │                   ├── model/
│   │   │                   ├── repository/
│   │   │                   ├── service/
│   │   │                   └── ClienteApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/
│   │           └── migration/
│   └── test/
│       └── java/
│           └── br/
│               └── com/
│                   └── fiap/
│                       └── cliente/
│                           └── ...
├── docker-compose.yml
├── Dockerfile
├── pom.xml
└── README.md
```

---

## Como Executar Localmente

### Pré-requisitos

- Java 17+
- Maven 3.9+
- Docker (opcional, para rodar banco MySQL localmente)

### Subindo o banco de dados com Docker

```sh
docker-compose up -d
```

### Executando a aplicação

1. Clone o repositório:
    ```sh
    git clone <url-do-repositorio>
    cd tech-challenge-4a-fase_cliente
    ```

2. Configure as variáveis de ambiente (veja [Variáveis de Ambiente](#variáveis-de-ambiente)) ou edite o `application.properties`.

3. Execute as migrações do banco (opcional, Flyway executa automaticamente ao subir a aplicação).

4. Compile e rode a aplicação:
    ```sh
    mvn clean install
    mvn spring-boot:run
    ```

5. Acesse a API em: `http://localhost:8080`

---

## Endpoints Principais

| Método | Endpoint           | Descrição                  |
|--------|--------------------|----------------------------|
| GET    | /clientes          | Lista todos os clientes    |
| GET    | /clientes/{id}     | Busca cliente por ID       |
| POST   | /clientes          | Cria novo cliente          |
| PUT    | /clientes/{id}     | Atualiza cliente           |
| DELETE | /clientes/{id}     | Remove cliente             |

### Exemplo de payload para criação de cliente

```json
{
  "nome": "João da Silva",
  "cpf": "12345678909",
  "email": "joao@email.com",
  "cep": "01001000",
  "endereco": "Rua Exemplo, 123"
}
```

---

## Testes

Para rodar os testes automatizados:

```sh
mvn test
```

Os testes utilizam H2 em memória e MockMvc para simulação das requisições.

---

## Migrações de Banco

O projeto utiliza Flyway para versionamento e execução automática das migrações SQL. Os scripts estão em `src/main/resources/db/migration`.

Ao iniciar a aplicação, as migrações são aplicadas automaticamente no banco configurado.

---

## Variáveis de Ambiente

As principais variáveis/configurações são:

| Variável                | Descrição                        | Exemplo                      |
|-------------------------|----------------------------------|------------------------------|
| SPRING_DATASOURCE_URL   | URL do banco de dados            | jdbc:mysql://localhost:3306/clientes |
| SPRING_DATASOURCE_USERNAME | Usuário do banco              | root                         |
| SPRING_DATASOURCE_PASSWORD | Senha do banco                | senha123                     |
| SPRING_JPA_HIBERNATE_DDL_AUTO | Estratégia do Hibernate   | validate                     |

Você pode definir essas variáveis no ambiente ou no arquivo `application.properties`.

---

## Observações

- O projeto valida CPF e CEP antes de persistir os dados.
- O CEP pode ser validado via integração com API externa (ex: ViaCEP).
- O Docker Compose sobe apenas o banco de dados MySQL; a aplicação deve ser iniciada separadamente.
- Para ambiente de produção, revise as configurações de segurança e variáveis sensíveis.

---

## Contato

Dúvidas ou sugestões? Entre em contato com o time do projeto.

