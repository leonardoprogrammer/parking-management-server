# Use a imagem base do Maven para compilar o projeto
FROM maven:3.8.8-eclipse-temurin-17 AS build

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo pom.xml e baixa as dependências do Maven
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código-fonte do projeto
COPY src ./src

# Compila o projeto
RUN mvn clean package -DskipTests

# Usa a imagem base do OpenJDK para rodar a aplicação
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho
WORKDIR /app

# Copia o jar gerado pelo Maven para o diretório de trabalho
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta que a aplicação irá rodar
EXPOSE 8081

# Define o comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]