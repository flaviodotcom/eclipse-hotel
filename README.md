<h1 align="center">Eclipse Hotel</h1>

## Descrição

Projeto desenvolvido com Spring Boot e PostgreSQL para o desafio técnico.

## Requisitos

- Java 17
- PostgreSQL
- Maven 3.8 ou superior

## Estrutura do Projeto

- **Config**: Camada para configuração do projeto. Configura a conexão com o banco de dados.
- **Controller**: Camada responsável pela comunicação com o cliente (API REST).
- **Model**: Representações das entidades do banco de dados.
- **Repository**: Camada para acesso e manipulação de dados no banco de dados.
- **Service**: Camada que contém a lógica de negócio.
- **Migration**: Utiliza Flyway para gerenciar as migrações de banco de dados.
