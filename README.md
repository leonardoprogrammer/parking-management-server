# Parking Management System - Backend






## Sobre o Projeto

Este repositório contém a implementação do backend de um **sistema de gerenciamento de estacionamentos** baseado em **microsserviços**. O sistema possibilita o cadastro de estacionamentos, controle de vagas, entrada e saída de veículos, além da cobrança e pagamento de tarifas.

🔗 **Repositório GitHub:** [Parking Management Server](https://github.com/leonardoprogrammer/parking-management-server)

## Arquitetura

O projeto segue a arquitetura de **microsserviços** e está dividido nos seguintes serviços:

- **Auth Service**: Responsável pela autenticação e gerenciamento de usuários.
- **Gateway Service**: Proxy reverso que gerencia as requisições para os microsserviços.
- **Notification Service**: Envio de notificações por e-mail e outros canais.
- **Parked Vehicles Service**: Registro e gerenciamento de veículos estacionados.
- **Parking Management Service**: Administração de estacionamentos e configurações.

Cada serviço é um projeto separado baseado no **Spring Boot 3.4.2** e usa **PostgreSQL** como banco de dados.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.4.2**
- **Spring Cloud** (Eureka, Config Server, API Gateway)
- **Spring Security & JWT**
- **PostgreSQL**
- **Docker & Docker Compose**
- **Swagger OpenAPI**

## Funcionalidades Principais

✅ Cadastro e autenticação de usuários com JWT
✅ Gerenciamento de estacionamentos e permissões de funcionários
✅ Controle de entrada e saída de veículos
✅ Cálculo automático de tarifas com suporte a pagamentos via QRCode Pix
✅ Suporte a múltiplos idiomas (Português, Inglês e Espanhol)
✅ Suporte a temas claro e escuro
✅ Microsserviço de notificações para envio de e-mails
✅ API Gateway para roteamento e autenticação centralizada

## Instalação e Execução

### Pré-requisitos

- **Java 17** instalado
- **Docker e Docker Compose**
- **PostgreSQL**

### Configuração

1. Clone o repositório:

   ```sh
   git clone https://github.com/leonardoprogrammer/parking-management-server.git
   cd parking-management-server
   ```

2. Configure as variáveis de ambiente nos arquivos `application.properties` de cada serviço.

3. Execute os serviços com Docker Compose:

   ```sh
   docker-compose up -d
   ```

4. Acesse a documentação da API (Swagger UI) em:

   ```sh
   http://localhost:8080/swagger-ui.html
   ```

## Estrutura do Projeto

```
parking-management-server/
├── auth/             # Serviço de autenticação
├── gateway/          # API Gateway
├── notification/     # Serviço de notificações
├── parkedvehicles/   # Controle de veículos estacionados
├── parkingmanagement/# Administração de estacionamentos
├── database/         # Scripts SQL para criação do banco
├── docker-compose.yml
└── README.md
```

## Endpoints Principais

### Autenticação

- `POST /auth/login` - Realiza login e retorna tokens JWT.
- `POST /auth/register` - Registra um novo usuário.
- `POST /auth/refresh-token` - Atualiza tokens JWT.

### Estacionamentos

- `POST /parking` - Cria um novo estacionamento.
- `GET /parking/{id}` - Obtém detalhes de um estacionamento.
- `PUT /parking/{id}` - Atualiza um estacionamento.

### Veículos Estacionados

- `POST /vehicles/checkin` - Registra entrada de veículo.
- `POST /vehicles/checkout` - Registra saída de veículo e calcula a tarifa.
- `GET /vehicles/history` - Histórico de veículos estacionados.

## Contribuição

Contribuições são bem-vindas! Siga os passos:

1. Fork o projeto
2. Crie uma branch (`feature/nova-funcionalidade`)
3. Commit suas mudanças (`git commit -m 'Add nova funcionalidade'`)
4. Envie um pull request

## Licença

Este projeto está licenciado sob a **MIT License**. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

