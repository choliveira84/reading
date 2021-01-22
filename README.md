# Reading

Projeto do desafio da Hivecloud. Foi construido com Sprint Boot, Java 11, MySQL e H2 para testes.
## Passo a passo

### Banco de dados

Foi utilizado o Docker para subir o banco. Comando para execução:

```
$ docker pull mysql:8.0.20
$ docker run -p 3306:3306 --name mscadastro --network reading -e MYSQL_USER=root -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -e MYSQL_DATABASE=mscadastro mysql:8.0.20
$ docker run -p 3307:3306 --name msnotificacao --network reading -e MYSQL_USER=root -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -e MYSQL_DATABASE=msnotificacao mysql:8.0.20
$ docker run -p 3308:3306 --name msleitura --network reading -e MYSQL_USER=root -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -e MYSQL_DATABASE=msleitura mysql:8.0.20
```

### Módulos

Para cada módulo do projeto, deverá ser executado o comando:

```
$ mvn clean install
$ java -jar target/NOME_DO_PACOTE.jar
```

### Ordem de execução dos projetos

* config-server
* ms-eureka-server
* api-gateway-zuul
* Quaisquer outros projetos (ms-cadastro, ms-leitura, ms-notificação)

## Aplicações

O projeto foi construido numa arquitetura de microserviços e todas as requisições são feitas a partir do gateway.
Todos os projetos são independentes entre si e tem um banco de dados próprio. 
Cada projeto tem um swagger configurado e pode ser utilizado para fazer as requisições pelo endereço `http://localhost:[PORTA]/swagger-ui.html`
Uma breve explicação dos serviços logo abaixo.

### Eureka Server

Service Discovery. Pode ser acessar a partir do endereço `http://localhost:8761`

### Config Server

Servidor de configuração.

### Api Gateway Zuul

Api Gateway responsável pelas requisições e load balancer.

### Microservice Cadastro

Serviço responsável por ter os cadastros de Usuários, Livros e Resenhas. Pode ser acesso a partir do endereço `http://localhost:8765/ms-cadastro/api/livros`, `http://localhost:8765/ms-cadastro/api/resenhas` e `http://localhost:8765/ms-cadastro/api/usuarios`.
Existe um motor de busca no endereço `http://localhost:8765/ms-cadastro/api/livros/consulta-externa/[termo pesquisado]` que retorna uma lista de até 5 livros. O termo utilizado pode ser o autor, livro, isbn, etc.

### Microservice Notificação

Serviço responsável para receber as notificações do cadastro e repassar para o usuário. Pode ser acesso a partir do endereço `http://localhost:8765/ms-notificacao/api/notificacoes`.

### Microservice Leitura

Serviço responsável para armazenar a atividade de leitura do usuário, tais como atualização do número de página, atualização da nota, inclusão de um livro na lista de leitura, finalização de leitura de um livro e início da leitura de um livro. Pode ser acesso a partir do endereço `http://localhost:8765/ms-leitura/api/leituras`.
