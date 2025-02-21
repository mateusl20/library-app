# Biblioteca

Este projeto consiste em um backend Spring Boot e um frontend Angular para um Sistema de Gerenciamento de Biblioteca.

## Pré-requisitos

- Docker instalado no seu sistema

## Executando a Aplicação

Para executar o backend e o frontend com um único comando:

1. Clone este repositório:
   
`git clone https://github.com/mateusl20/library-app.git`

2. Construa e execute a aplicação:
   
`docker build -t library-app . && docker run -p 8080:8080 -p 4200:4200 library-app`

Este comando irá:
- Construir a imagem Docker para o backend e o frontend
- Iniciar o container
- Disponibilizar o backend em `http://localhost:8080`
- Disponibilizar o frontend em `http://localhost:4200`

3. Para parar a aplicação, pressione `CTRL+C` no terminal onde o container está em execução.

## Acessando a Aplicação

- Frontend: Abra seu navegador web e acesse `http://localhost:4200`
- API Backend: A API está acessível em `http://localhost:8080`

## Informações Adicionais

- Ambos os serviços estão containerizados usando Docker
- Backend: Aplicação Spring Boot executando em Java 21
- Frontend: Aplicação Angular
- A aplicação usa base de dados em memória utilizando H2 e está documentada utilizando Swagger.
_________________________________________________
- A base de dados pode ser acessada utilizando acessando: http://localhost:8080/h2-console
  #### H2 Console Login Details:
- **JDBC URL:** `jdbc:h2:mem:librarydb`
- **Username:** `sa`
- **Password:** (leave blank)
_________________________________________________
- A documentação para cada endpoint pode ser acessada utilizando o link: http://localhost:8080/swagger-ui.html
_________________________________________________
- Para facilitar os testes, dois usuarios são criados automaticamente com as credenciais:
  #### Automatic Created Users Details:
- **RIGHTS:** `ADMIN`
- **Username:** `admin@test.com`
- **Password:** `password`
_________________________________________________
- **RIGHTS:** `USER`
- **Username:** `user@test.com`
- **Password:** `password`
_________________________________________________

- A sequência final de commits está fora da orderm de desenvolvimento correto, devido ao processo de colocar toda a aplicação no mesmo repositório.
Caso surja alguma dúvida, ficarei feliz em esclarecer.
  



