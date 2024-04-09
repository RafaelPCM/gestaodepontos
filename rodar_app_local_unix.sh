#!/bin/bash

# Suba o contêiner do banco de dados
docker-compose up -d database

# Construir a aplicação Spring Boot (ajuste o comando conforme o build tool)
mvn clean install package -DskipTests

# Iniciar a aplicação Spring Boot
java -jar target/gestaodepontos-0.0.1-SNAPSHOT.jar