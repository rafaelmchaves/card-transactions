# Rotina de transações

Cada portador de cartão (cliente) possui uma conta com seus dados. A cada operação
realizada pelo cliente uma transação é criada e associada à sua respectiva conta. Cada
transação possui um tipo (compra à vista, compra parcelada, saque ou pagamento), um
valor e uma data de criação. Transações de tipo compra e saque são registradas com
valor negativo, enquanto transações de pagamento são registradas com valor positivo.

# How to run the application

## Pre-requisites:
Install java 17 and docker in your machine

## Run
I included a run.sh file in the project root folder.
To run the application, just run this command in the terminal:

```
/.run.sh
```

Maybe you need to execute this command before run the run.sh file, in order to grant permission to this file: 
```
chmod +x run.sh
```

The run.sh file execute ```mvn clean package ``` command to create the jar file, then execute the docker file.
The "Dockerfile" is documented with some comments explaining everything.
The docker-compose.yml file has all services that are being executed. The first one is this spring boot application.
Other services are the Prometheus and Grafana.

# Endpoints

To access the documentation of the endpoints using the link of swagger:
http://localhost:8080/swagger-ui/index.html#/

# Decisions

## h2

## Caffeine

## Prometheus and Grafana

## Unit and integration tests

# Future

# Tarefas

1 - ~~Fazer da forma mais simples,controller -> service -> portImpl -> repository~~
2 - ~~Subir H2~~
3 - ~~Testar os endpoints de account~~
4 - ~~Colocar no github~~
5 - ~~Fazer o endpoint de Transaction~~
~~6 - Melhorar retorno e código. Além disso, implementar exceções~~
7 - ~~Criar testes automatizados~~
8 - Rever código
9 - ~~Documentar com o swagger~~
10 - ~~Dockerizar aplicação~~
11 - Pensar em algo para acrescentar na aplicação - Cache, postgres etc
12 - Escrever Readme
13 - Rever tudo o que foi feito