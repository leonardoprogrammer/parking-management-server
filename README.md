# Parking Management Server

O **Parking Management Server** é o coração do projeto de gerenciamento de estacionamentos, implementado como um conjunto de **microsserviços**. Este repositório abrange a lógica de negócio e integrações essenciais para o funcionamento completo do sistema, como autenticação, gerenciamento de estacionamentos, controle de veículos, notificações e muito mais.

🔗 **Repositório da front-end:** [Parking Management Web App](https://github.com/leonardoprogrammer/parking-management-web-app)

---

## 🚀 Funcionalidades Principais

- **Autenticação e Gerenciamento de Usuários**  
  Realiza cadastro, login, atualização e controle de usuários através do Auth Service.

- **API Gateway Centralizado**  
  Roteia e gerencia as requisições para os microsserviços, garantindo a segurança e a escalabilidade via Gateway Service.

- **Notificações**  
  Envia alertas e e-mails para os usuários utilizando o Notification Service.

- **Controle de Veículos Estacionados**  
  Registra a entrada, saída e histórico de veículos através do Parked Vehicles Service.

- **Administração de Estacionamentos**  
  Permite o cadastro e gerenciamento de estacionamentos, bem como a configuração de tarifas, via Parking Management Service.

---

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.4.2**
- **Spring Cloud** (incluindo Eureka, Config Server e API Gateway)
- **Spring Security & JWT**
- **PostgreSQL**
- **Docker & Docker Compose**
- **Swagger OpenAPI**

---

## 📂 Estrutura do Projeto

A estrutura do repositório reflete a arquitetura de microsserviços, onde cada serviço é implementado como um projeto independente:

```
parking-management-server/
├── auth/               # Serviço de autenticação
├── gateway/            # API Gateway
├── notification/       # Serviço de notificações
├── parkedvehicles/     # Controle de veículos estacionados
├── parkingmanagement/  # Administração de estacionamentos
├── database/           # Scripts SQL para criação do banco
├── docker-compose.yml
├── LICENSE
└── README.md
```

## ⚙️ Configuração e Execução

### Pré-requisitos

- **Java 17**
- **Docker e Docker Compose**
- **PostgreSQL**

### Passos para Configuração

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/leonardoprogrammer/parking-management-server.git
   cd parking-management-server

2. **Configure as variáveis de ambiente**
Atualize os arquivos application.properties de cada serviço com as configurações necessárias (ex.: credenciais do banco de dados, porta, etc.).

3. **Execute os serviços com Docker Compose**:
```
docker-compose up -d
```

4. **Acesse a documentação das APIs**

Abra o navegador e acesse:

`Auth Service`
```
http://localhost:8081/api/swagger-ui.html
```
`Parking Management Service`
```
http://localhost:8082/api/swagger-ui.html
```
`Parked Vehicle Service`
```
http://localhost:8083/api/swagger-ui.html
```

## 🌐 Endpoints Principais

### Autenticação (Auth Service)

- `POST /api/auth/login` - Realiza login e retorna tokens JWT.
- `POST /api/auth/register` - Registra um novo usuário.
- `POST /api/auth/refresh-token` - Atualiza tokens JWT.

### Estacionamentos (Parking Management Service)

- `POST /api/parking` - Cria um novo estacionamento.
- `GET /api/parking/{id}` - Obtém detalhes de um estacionamento.
- `PUT /api/parking/{id}` - Atualiza um estacionamento.

### Veículos Estacionados (Parked Vehicles Service)

- `POST /api/vehicles/checkin` - Registra entrada de veículo.
- `POST /api/vehicles/checkout` - Registra saída de veículo e calcula a tarifa.
- `GET /api/vehicles/history` - Histórico de veículos estacionados.

## 🤝 Contribuição

Contribuições são bem-vindas! Siga os passos abaixo para contribuir:

1. Faça um fork do projeto.
2. Crie uma branch para sua feature:
   ```bash
   git checkout -b minha-feature
   ```
3. Commit suas alterações:
   ```bash
   git commit -m "Minha nova feature"
   ```
4. Envie para o repositório remoto:
   ```bash
   git push origin minha-feature
   ```
5. Abra um Pull Request.

---

## 📝 Licença

Este projeto está licenciado sob a [MIT License](LICENSE).

---
