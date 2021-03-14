# Loja virtual
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/kazuyabr/lojavirtual/blob/master/LICENSE) 

# Sobre o projeto

Esta Loja Virtual é uma aplicação web construida para dar suporte as vendas online de uma empresa fictícia, mas que atende as necessidades de venda do mundo real. 

A aplicação consiste na exibição de produtos por categoria, bem como na adição desse produto ao carrinho de compras, no cadastro de usuário e na realização de login utilizando Token JWT, no registro e entrega de pedido e no envio de email com os dados e a confirmação do pedido.

## Back end
- Java
- Spring Boot
- JPA / Hibernate
- Maven
- MockMailService
- Spring Security
- Spring Data

## Front end
- HTML / CSS / JS / TypeScript
- Angular 9
- Material Design

## Implantação em produção
- Back end: Heroku
- Front end web: Netlify
- Banco de dados: PostgreSQL

# Como executar o projeto

## Back end
Pré-requisitos: Java 11

```bash
# clonar repositório
https://github.com/kazuyabr/lojavirtual.git

# entrar na pasta do projeto back end
cd backend

# executar o projeto
./mvnw spring-boot:run
```

## Front end web
Pré-requisitos: npm / yarn / Angular-CLI 9 ou superior

```bash
# clonar repositório
https://github.com/kazuyabr/lojavirtual.git

# entrar na pasta do projeto front end web
cd web

# instalar dependências
npm install

# executar o projeto
ng serve
```

