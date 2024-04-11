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

Clonar o projeto escolhido, provavelmente será solicitado para que faça o login com a conta do Github/GitLab, ao logar, escolher a branch (git checkout {nome da branch}), por exemplo, a develop, mudar o profile no arquivo application.properties (para dev ou dev-infra), rodar aplicação via VSCode clicando em "Run" e "Start Debugging" ou apertando F5.

Usuário teste:

(Usuário Administrador)

Login: 00000000000

Senha: 123

ou

(Usuário Comum)

Login: 12312312312

Senha: 123


##### PARA ACESSAR A DOCUMENTAÇÃO DE CADA API (SWAGGER)

Swagger Gestao De Pontos:

http://localhost:8080/swagger-doc/gestaodepontos/swagger-ui/index.html


#### CONTRIBUIÇÕES

Segui o padrão GitFlow, mas isso pode variar dependendo da equipe e do projeto, converse com o líder da equipe para saber mais detalhes de como as tarefas e contribuições são gerenciadas.

##### GitFlow

https://medium.com/trainingcenter/utilizando-o-fluxo-git-flow-e63d5e0d5e04

https://www.atlassian.com/br/git/tutorials/comparing-workflows/gitflow-workflow




#### Requisitos atendidos:

- [X] O sistema deve ser protegido por login e senha, e somente após autenticado o usuário poderá visualizar os dados; 

- [X] Os dados de usuários e pontos devem ser persistidos em banco de dados;

- [X] Um ponto representa o registro de entrada ou saída de expediente durante a jornada de trabalho de um usuário (colaborador);
O sistema deve ter suporte a dois tipos de regimes de jornadas de trabalho, são eles: 

- [X] Regime de 6 horas contínuas de trabalho, sem previsão de pausas;

Esse ponto do regime de 8hrs com pausa minima prevista de 1h foi implementado parcialmente, ficou faltando corrigir a logica de calcular o horario da pausa, mas ele verifica se o regime é de 8hrs ou 6hrs.

- [ ] Regime de 8 horas de trabalho, com previsão de pausa mínima de 1 hora para almoço;


- [X] O sistema deve ter flexibilidade para aceitar entradas de registros de pontos em qualquer horário, dada a situações de imprevistos, atrasos ou cargas extras de trabalho;

Exemplos de registros de pontos de jornada de trabalho válida para um regime de 8 horas:
08:28 -> Horário de início de expediente,
12:15 -> Horário de saída para pausa para almoço,
13:15 -> Horário de retorno de pausa para almoço,
17:28 -> Horário de saída de expediente.

- [X] O sistema deve ter suporte para múltiplos registros de pontos em um dia, não se restringindo ao limite de quatro pontos. 
Por exemplo: 	
08: 28 (início de expediente) -> 12:15 (pausa para almoço) -> 13:15 (retorno da pausa para almoço) -> 15:30 (saída esporádica) -> 16:30 (retorno da saída esporádica) -> 18:28 (fim de expediente);

- [X] O sistema deve ter suporte para dois tipos de usuários: Usuário Administrador e Usuário Comum;	

- [X] O usuário administrador terá permissão para cadastro de novos usuários (usuário comum);	

- [X] Ao cadastrar um novo usuário comum, o administrador deverá informar o tipo de regime de jornada de trabalho daquele colaborador;	

- [X] O usuário comum não poderá cadastrar novos usuários;	

O usuário comum terá acesso as seguintes funcionalidades:

- [X] Registrar ponto: O usuário poderá informar a data/hora para registro de um novo ponto de trabalho na jornada;		

- [X] Resumo de jornada do dia atual: Com base nos pontos cadastrados para o dia, o usuário poderá verificar quais pontos foram contemplados, bem como se a jornada prevista para o dia está completa ou não (entende-se completa quando a duração do expediente com base nos pontos atende o regime de jornada de trabalho do colaborador);


- [X] Previsão para completar jornada: O sistema deverá apresentar, com base nos pontos, resumo de jornada e regime de jornada do colaborador, a quantidade de horas restantes para completar a jornada do dia;


- [X] Horas excedidas da jornada:  O sistemas deverá apresentar, com base nos pontos, resumo de jornada e regime de jornada do colaborador, a quantidade de horas excedidas (extras) na jornada do dia;


##### Observações

- [X] O projeto deverá vir documentado no repositório (arquivo README.md) contendo as versões das linguagens, principais bibliotecas/frameworks, como executar o projeto, funcionalidades implementadas e funcionalidades idealizadas, mesmo que não desenvolvidas. 

Feito parcialmente, creio que consegui cobrir uma boa quantidade do codigo, com mais tempo seria possivel cobrir tudo.

- [X] Implemente módulos de testes unitários, principalmente para validação de fluxos de processamento das datas dos pontos;

Infelizmente não tive tempo suficiente para subir o projeto para cloud, mas os scripts que facilitam isso já foram quase todos (se nao todos) feitos.

- [ ] Se possível, disponibilize o sistema desenvolvido em algum serviço cloud e que esteja acessível para avaliação prévia;

- [X] Junto às documentações, apresente diagrama de entidade/relacionamento DER que reflete a arquitetura de dados aplicada na solução;

- [X] Boas práticas de Git, testes unitários e qualidade de código serão considerados na avaliação.