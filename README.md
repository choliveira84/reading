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

### Projetos

Para cada módulo do projeto, deverá ser executado o comando:

```
$ mvn clean install
$ java -jar target/NOME_DO_PACOTE.jar
```

### Ordem de execução

* config-server
* ms-eureka-server
* api-gateway-zuul
* Quaisquer outros projetos (ms-cadastro, ms-leitura, ms-notificação)
