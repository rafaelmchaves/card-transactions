# Rotina de transações

Cada portador de cartão (cliente) possui uma conta com seus dados. A cada operação
realizada pelo cliente uma transação é criada e associada à sua respectiva conta. Cada
transação possui um tipo (compra à vista, compra parcelada, saque ou pagamento), um
valor e uma data de criação. Transações de tipo compra e saque são registradas com
valor negativo, enquanto transações de pagamento são registradas com valor positivo.


# Tarefas

1 - ~~Fazer da forma mais simples,controller -> service -> portImpl -> repository~~
2 - ~~Subir H2~~
3 - ~~Testar os endpoints de account~~
4 - ~~Colocar no github~~
5 - ~~Fazer o endpoint de Transaction~~
6 - Melhorar retorno e código. Além disso, implementar exceções
7 - Criar testes automatizados
8 - Rever código
9 - Documentar com o swagger
10 - Dockerizar aplicação
11 - Pensar em algo para acrescentar na aplicação - Cache, postgres etc
12 - Escrever Readme
13 - Rever tudo o que foi feito

# Perguntas
1 - Documentação em inglês?
2 - Precisa colocar os id's como UUID?