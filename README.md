# Triagem Inteligente SUS com IA

## Descrição do Projeto

Este projeto consiste em um sistema de triagem inicial inteligente para o Sistema Único de Saúde (SUS), desenvolvido com o framework Spring Boot e utilizando MongoDB como banco de dados. O core da aplicação reside na integração com uma inteligência artificial (atualmente Gemini API) para realizar a triagem humanizada de pacientes via canais de comunicação como o Telegram (já implementado) e, futuramente, o WhatsApp.

O principal objetivo do sistema é otimizar o fluxo de atendimento nas unidades de saúde, permitindo que pacientes iniciem o processo de triagem remotamente. A IA coleta dados essenciais como sintomas, histórico e informações pessoais, adianta a ficha de atendimento e atribui uma classificação de risco inteligente. Isso visa reduzir o tempo de espera, diminuir a carga de trabalho dos profissionais de saúde e garantir um atendimento mais eficiente e focado nos casos de maior urgência.

## Tecnologias Utilizadas

* **Linguagem de Programação:** Java 21
* **Framework Web:** Spring Boot 3.5.3
* **Banco de Dados:** MongoDB
* **ORM/ODM:** Spring Data MongoDB
* **Segurança:** Spring Security, JWT (JSON Web Tokens) com `java-jwt`
* **Documentação API:** Springdoc OpenAPI (Swagger UI)
* **Inteligência Artificial:** Gemini API (via chamadas HTTP diretas)
* **Gerenciamento de Dependências:** Maven
* **Conteinerização:** Docker, Docker Compose
* **Outros:** Lombok (para redução de boilerplate code), Spring DevTools (para desenvolvimento)

## Pré-requisitos

Para rodar este projeto localmente, você precisará ter instalado:

* **Java Development Kit (JDK):** Versão 21 ou superior.
* **Apache Maven:** Versão 3.9.10 ou superior (o projeto inclui o Maven Wrapper, que pode gerenciar isso automaticamente).
* **Docker e Docker Compose:** Para conteinerizar o ambiente de desenvolvimento (aplicação e MongoDB).

## Configuração do Projeto

### 1. Clonar o Repositório

```bash
git clone [https://github.com/joao-v170r/tech-challenge-05-triagem-ia.git](https://github.com/joao-v170r/tech-challenge-05-triagem-ia.git)
cd tech-challenge-05-triagem-ia
````

### 2\. Configuração do MongoDB

O projeto está configurado para se conectar a um banco de dados MongoDB. Você pode configurá-lo de duas maneiras:

#### a) Usando Docker Compose (Recomendado)

O arquivo `docker-compose.yml` já contém a configuração para iniciar um serviço MongoDB e a aplicação Spring Boot. Este é o método recomendado, pois configura o banco de dados automaticamente.

#### b) MongoDB Local

Se preferir usar uma instância local do MongoDB (fora do Docker Compose), certifique-se de que ele esteja rodando na porta `27017` e o banco de dados seja `db_triagem_ia`. As credenciais de administrador (`admin:admin123`) são configuradas no `docker-compose.yml` para a imagem do Mongo.

As configurações de conexão com o MongoDB podem ser encontradas em `src/main/resources/application.properties`:

```properties
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=db_triagem_ia
# Para Docker Compose, a URI completa está no docker-compose.yml:
# - SPRING_DATA_MONGODB_URI=mongodb://admin:admin123@mongodb-tech-challeger-05:27017/db_triagem_ia?authSource=admin
```

### 3\. Variáveis de Ambiente

A aplicação requer uma chave para o token JWT e de API para a integração com o modelo Gemini (IA). Estas chaves devem ser configurada como uma variável de ambiente ou no arquivo `application.properties`:

A chave do token JWT pode ser gerada em um site como [JWT Secret Key Generator](https://jwtsecretkeygenerator.com/pt/), e deve ser especificada em `api.secure.token.secret` em `application.properties`:
```properties
api.secure.token.secret=ZpTd0NhIBisSQY7LAZKD6SNBNxyJSGpa
```

A chave do Gemini e o modelo do Gemini podem ser obtidos em [Gemini Developer API Pricing](https://ai.google.dev/gemini-api/docs/pricing), e devem ser especicados em `gemini.api.url` e `gemini.api.key` em `application.properties`:
```properties
gemini.api.url=https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent
gemini.api.key=AIzaSyDlvzTMe2nRUkHME8vEaAcFCXLy49DPkSE
```
No caso, utilizamos o Gemini 2.0 Flash.

Essas chaves devem ser armazenadas no ambiente e não devem ser exposta publicamente em um ambiente de produção. Estão em `application.properties` apenas para fins de desenvolvimento e testes. 

A chave do Gemini no `application properties` está desativada, e uma nova chave nova deve ser gerada para utilizar a aplicação.

## Como Rodar o Projeto

### 1\. Usando Docker Compose (Recomendado)

Este método irá construir a imagem da aplicação, iniciar o MongoDB e a aplicação Spring Boot em contêineres:

```bash
docker-compose up --build
```

A aplicação estará acessível em `http://localhost:8080`.

### 2\. Rodando a Aplicação Spring Boot Diretamente

Se você tiver o MongoDB rodando localmente (item 2b da configuração), pode iniciar a aplicação Spring Boot diretamente:

#### a) Construir o Projeto

```bash
./mvnw clean install
```

#### b) Rodar a Aplicação

```bash
java -jar target/triagem-ia-sus-0.0.1-SNAPSHOT.jar
```

A aplicação estará acessível em `http://localhost:8080`.

A aplicação pode ser utilizada com swagger em `http://localhost:8080/swagger-ui/index.html`.

## Estrutura do Projeto

O projeto segue a arquitetura de camadas comum em aplicações Spring Boot:

* **`br.com.triagem_ia_sus.triagem_ia_sus.controller`**: Contém os controladores REST que lidam com as requisições HTTP para gerenciar `Atendimento`, `Colaborador`, `Paciente` e autenticação.
* **`br.com.triagem_ia_sus.triagem_ia_sus.dto`**: Classes Data Transfer Object (DTO) para entrada (`Input...DTO`) e saída (`...DTO`) de dados.
* **`br.com.triagem_ia_sus.triagem_ia_sus.domain`**: Classes de domínio que representam as entidades do negócio (e.g., `Atendimento`, `Paciente`, `Colaborador`, `Mensagem`, enums de risco/status/canais).
* **`br.com.triagem_ia_sus.triagem_ia_sus.repository`**: Interfaces de repositório (Spring Data MongoDB) para acesso aos dados.
* **`br.com.triagem_ia_sus.triagem_ia_sus.useCase`**: Camada de serviço (casos de uso) que contém a lógica de negócio, incluindo a orquestração da triagem com IA.
* **`br.com.triagem_ia_sus.triagem_ia_sus.security`**: Classes relacionadas à segurança, incluindo filtros JWT, configuração de segurança e serviços de token/autenticação.
* **`br.com.triagem_ia_sus.triagem_ia_sus.exception`**: Tratamento global de exceções.
* **`src/main/resources/application.properties`**: Arquivo de configuração da aplicação.
* **`Dockerfile`**: Definição para construção da imagem Docker da aplicação.
* **`docker-compose.yml`**: Configuração dos serviços Docker (aplicação e MongoDB).

## Arquitetura do Projeto

<div align="center">
    <img src="src/main/resources/img/arquitetura.png" width="624px" height="255px">
</div>

O projeto deste repositório se trata apenas da aplicação spring boot, que se comunica com a api do Gemini para realizar a triagem dos pacientes, e de um banco de dados em mongoDB. 

O repositório do serviço em Python que se comunica diretamente com a API do Telegram pode ser acessado em `https://github.com/joao-v170r/client-bot-telegram-python-triagem-ai`.

## Endpoints da API

A documentação interativa da API estará disponível via Swagger UI após a inicialização da aplicação em:

`http://localhost:8080/swagger-ui.html`

Ou via JSON em:

`http://localhost:8080/v3/api-docs`

Alguns dos principais endpoints incluem:

* **Autenticação:**
    * `POST /auth/login`: Realiza o login e retorna um token JWT.
* **Gerenciamento de Colaboradores:**
    * `POST /create-colaborador`: Cria um novo colaborador.
    * `GET /colaborador/{id}`: Busca um colaborador por ID.
    * `GET /colaborador`: Lista todos os colaboradores (paginado).
    * `PUT /update-colaborador/{id}`: Atualiza um colaborador existente.
    * `DELETE /delete-colaborador/{id}`: Deleta um colaborador.
* **Gerenciamento de Pacientes:**
    * `POST /create-paciente`: Cria um novo paciente.
    * `GET /paciente/{id}`: Busca um paciente por ID.
    * `GET /paciente`: Lista todos os pacientes (paginado).
    * `PUT /update-paciente/{id}`: Atualiza um paciente existente.
    * `DELETE /delete-paciente/{id}`: Deleta um paciente.
* **Gerenciamento de Atendimentos:**
    * `POST /realizar-atendimento`: Inicia ou continua um atendimento de triagem via IA.
    * `POST /create-atendimento`: Cria um novo registro de atendimento (para uso manual/administrativo).
    * `GET /atendimento/{id}`: Busca um atendimento por ID.
    * `GET /atendimento`: Lista todos os atendimentos (paginado).
    * `PUT /update-atendimento/{id}`: Atualiza um atendimento existente.
    * `DELETE /delete-atendimento/{id}`: Deleta um atendimento.

## Fluxo Principal

Como paciente, você apenas precisa enviar uma requisição para `POST /realizar-atendimento` com o campo de `mensagem`:

Exemplo de envio:
```
 {
  "mensagem": "teste"
 }
```

Exemplo de resposta:
```
{
  "idAtendimento": "687ece8780400e82a06845b5",
  "nome": null,
  "cpf": null,
  "dataNascimento": null,
  "telefone": null,
  "endereco": null,
  "sintomasRecente": null,
  "classificacaoUrgencia": null,
  "mensagem": "Olá! Bem-vindo(a) à Clínica do SUS. Meu nome é Tina e sou sua assistente virtual de triagem inicial. Estou aqui para te ajudar a agilizar seu atendimento. Para começar, preciso coletar algumas informações. \n\nPrimeiramente, qual é o seu nome completo?",
  "atendimentoConcluido": "Não"
}
```

Na resposta você receberá uma mensagem inicial e o idAtendimento. Nos envios subsequentes, você deverá repassar o campo de `id` com o valor de `idAtendimento` para a IA receber todo o histórico do atendimento e continuar a triagem.

Exemplo de envio:
```
{
  "id": "687ece8780400e82a06845b5"
  "mensagem": "Nome Teste"
}
```

Exemplo de resposta:
```
{
  "idAtendimento": "687ece8780400e82a06845b5",
  "nome": "Nome Teste",
  "cpf": null,
  "dataNascimento": null,
  "telefone": null,
  "endereco": null,
  "sintomasRecente": null,
  "classificacaoUrgencia": null,
  "mensagem": "Obrigada, Nome Teste! Agora, por favor, poderia me informar o seu CPF?",
  "atendimentoConcluido": "Não"
}
```

A aplicação possui outros endpoints para listar, criar, atualizar e apagar colaboradores, pacientes e atendimentos. 

Para utilizar estes endpoints, você precisará de um token JWT válido, que pode ser obtido cadastrando um colaborador em `POST /create-colaborador`, com o campo `tipoColaborador` precisando ser `MEDICO` ou `ENFERMEIRO`.
Exemplo de envio:
```
{
  "nome": "Teste",
  "dataNascimento": "26/06/1992",
  "email": "email@email.com",
  "senha": "senha123",
  "tipoColaborador": "MEDICO"
}
```

Para realizar login com este email e senha no endpoint `POST /auth/login`, e então obter um token de acesso.

## Próximos Passos e Melhorias Futuras

* **Integração com WhatsApp:** Desenvolver e implementar a integração com a API do WhatsApp para expandir os canais de atendimento.
* **Aprimoramento do Modelo de IA:** Refinar a capacidade da IA de compreender e classificar sintomas, bem como de extrair informações de forma mais robusta e menos propensa a erros de parsing.
* **Interface Web/Mobile para Profissionais:** Criar um frontend dedicado para que os profissionais de saúde possam visualizar, gerenciar e interagir com as fichas de atendimento pré-triadas e o histórico dos pacientes.
* **Notificações em Tempo Real:** Implementar notificações em tempo real para as unidades de saúde sobre novos atendimentos ou casos urgentes identificados pela IA.
* **Análise de Dados e Dashboards:** Desenvolver ferramentas para coletar e analisar dados do processo de triagem, gerando insights para melhorias contínuas e relatórios operacionais.

## Licença

Este projeto está licenciado sob a [Nome da Licença, ex: MIT License]. Consulte o arquivo `LICENSE` para mais detalhes.