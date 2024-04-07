# Avaliação Desenvolvimento Full Stack Logique Sistemas Gestao de Pontos de Jornada de Trabalho (GPJT)

#### TECNOLOGIAS UTILIZADAS

Java 17

Spring Boot 3.2.4

PostgreSQL

Lombok

#### INSTALAÇÕES

##### Instalar o Java 17 (Recomendo o do RedHat x64 MSI)

https://developers.redhat.com/products/openjdk/download

Atentem para ativar TUDO na instalação, o PATH e etc.

##### Instalar o MAVEN

Escolher a versão Binary zip archive

https://maven.apache.org/download.cgi

Tutorial de como instalar o Maven e colocar as variáveis de ambiente

https://dicasdejava.com.br/como-instalar-o-maven-no-windows/

##### PostgreSQL e/ou DBeaver

https://www.postgresql.org/download/

https://dbeaver.io/download/

O DBeaver é um programa multiplataforma, que tem por objetivo conectar e manipular vários tipos de banco de dados. 

##### Postman ou Imnsonia

ps: O Postman facilita na hora de colocar autenticação.

https://www.postman.com/downloads/

https://insomnia.rest/download

##### IDE

Recomendo o VSCode

https://code.visualstudio.com/download

Recomendo instalar algumas extensões que irão ajudar:

Lombok Annotations, Git History e TODO Tree.

#### CONFIGURAÇÕES


##### PostgreSQL // DBeaver

Para adicionar novas conexões, clica em Database "New Database Connection", apertar em "PostgreSQL", Next, ir em "PostgreSQL" marcar a caixa "Show all databases", volta para "Main" e preenche os dados.
Em "Host" preencher o IP desejado (Local ou Remoto).

Segue os dados para cada conexão:

###### Conexão Local

`Host: localhost:5432`

###### Conexão Remota (Produção)

`Host: xx.x.xxx.xxx:5432`

`Database: postgres`

`Username: postgres`

`Password: admin`

###### Exportar e Importar dados do banco de dados

Export:

Conecta ao banco remoto `( Host: xx.x.xxx.xxx:5432 )` , escolhe a database que vai querer exportar e em seguida clica com o botão direito -> Tools -> Backup.
Ao abrir a tela de "Backup", em "Objects", seleciona a "public" e aperta em Next
Na tela de "Backup Settings", em "Format" selecionar "Tar", em "Enconding" selecionar "UTF-8", marcar a box "Add create database statement" e verificar em "Local Client" a versão do PostgreSQL requerida, ao final apertar em "Start", verifica se deu tudo certo e pode fechar a janela de Backup

Import:

Adiciona uma nova conexão, clica em Database "New Database Connection", apertar em "PostgreSQL", Next, ir em "PostgreSQL" marcar a caixa "Show all databases", volta para "Main" e preenche os dados (caso queira mudar algo). Em "Host" preencher o IP desejado, no caso, Local e em "Password" coloca a senha padrão (geralmente é 123).
Verificar em "Local Client" a versão do PostgreSQL requerida.

Em seguida, clica com o botão direito em "Databases", dentro da conexão localhost e cria uma nova database ("Create New Database"), coloca o nome e verifica se o "Encoding" tá em "UTF-8"

Em seguida, clica com o botão direito na nova database criada e em seguida clica com o botão direito -> Tools -> Restore.
Na janela "Restore Settings, verifica o "Format" se tá em "Tar" e marca a box "Create database" e escolhe o arquivo gerado pelo backup.
Verificar em "Local Client" a versão do PostgreSQL requerida.

#### PARA RODAR A APLICAÇÃO

Clonar o projeto escolhido, provavelmente será solicitado para que faça o login com a conta do GitLab, ao logar, escolher a branch (git checkout {nome da branch}), por exemplo, a develop, mudar o profile no arquivo application.properties (para dev ou dev-infra), rodar aplicação via VSCode clicando em "Run" e "Start Debugging" ou apertando F5.

Usuário teste:

(Usuário Administrador)

Nome: admin

Senha: 123456

ou

(Usuário Comum)

Nome: user

Senha: 123456


##### PARA ACESSAR A DOCUMENTAÇÃO DE CADA API (SWAGGER)

Swagger GPJT:

http://localhost:8080/swagger-doc/gpjt/swagger-ui/index.html


#### CONTRIBUIÇÕES

Segui o padrão GitFlow, mas isso pode variar dependendo da equipe e do projeto, converse com o líder da equipe para saber mais detalhes de como as tarefas e contribuições são gerenciadas.

##### GitFlow

https://medium.com/trainingcenter/utilizando-o-fluxo-git-flow-e63d5e0d5e04

https://www.atlassian.com/br/git/tutorials/comparing-workflows/gitflow-workflow











O sistema deve ter flexibilidade para aceitar entradas de registros de pontos 
em qualquer horário, dada a situações de imprevistos, atrasos ou cargas 
extras de trabalho;
Exemplos de registros de pontos de jornada de trabalho válida para um regime 
de 8 horas:
0828  Horário de início de expediente,
1215  Horário de saída para pausa para almoço,
1315  Horário de retorno de pausa para almoço,
1728  Horário de saída de expediente.


if(WorkdayType == "SIX_HOUR_CONTINUOUS") {
    pausa = pausa
} else if(WorkdayType == "EIGHT_HOUR_WITH_BREAK") {
    if(pausa.count == 0) {
        "pausa para o almoço"
    } elseif(pausa.count == 1){
        "volta do almoço"
    } else { #ps: se pausou uma vez, tem que voltar para poder finalizar o expediente
        pausa
    }
}