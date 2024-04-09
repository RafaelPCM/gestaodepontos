# Use a imagem base do OpenJDK para Java 17
FROM openjdk:17-jdk-slim AS build

# Defina o diretório de trabalho
WORKDIR /usr/src/app

# Copie o arquivo pom.xml separadamente para otimizar o cache do Docker
COPY pom.xml .

# Copie todos os arquivos do projeto
COPY . .

# Compile o projeto usando Maven
RUN mvn -B clean package -DskipTests

# Segunda etapa para criar a imagem final
FROM openjdk:17-jdk-slim

# Defina o diretório de trabalho
WORKDIR /usr/src/app

# Copie o artefato JAR gerado na etapa anterior
COPY --from=build /usr/src/app/target/*.jar app.jar

# Exponha a porta 8080
EXPOSE 8080

# Comando para executar a aplicação Spring Boot
CMD ["java", "-jar", "app.jar"]
