@echo off

rem Suba o contêiner do banco de dados
docker-compose up -d database
if %errorlevel% neq 0 exit /b %errorlevel%

rem Build da aplicacao Spring Boot
mvn clean install package -DskipTests

rem Start da aplicacao Spring Boot
java -jar target\*.jar  
