# Client API
API de Clientes

## Requisitos
- Maven 
- Java 11 
- Docker
- Docker Compose

## Variaveis de Ambiente da aplicação

| Nome | Descrição | Valor Padrão | Obrigatório |
| -- | -- | -- | -- |
| ENV | Qual Enviroment a Aplicação está | staging |Não |
| JWT_SECRET_KEY | Secret usado na validação do token JWT | stubJwt | Não |
| DATASOURCE_URL | Host de conexão postgresql || Sim |
| DATABASE_USERNAME | Usuário de conexão postgresql || Sim |
| DATABASE_PASSWORD | Senha de conexão postgresql || Sim |
| DATABASE_CONNECTION_MIN | mínimo de conexões ociosas do pool de conexão |10| Não |
| DATABASE_CONNECTION_MAX | maximo de conexões configuradas |20| Não |

## Buildando e executando os testes unitarios 

```sh
mvn clean install
```

## Executando os testes de integração com TestContainer
```sh
mvn verify -Pfailsafe
```

## Executando a aplicação dentro do container Docker 
### Realize o build da aplicação (sem testes)

```sh
mvn clean install -DskipTests
```

### Pare os containers dockers da aplicação, faça o build, e inicie novamente
```sh
docker-compose down
```

```sh
docker-compose build
```

```sh
docker-compose up
```

A aplicação fica disponivel na porta `8080`

## Chamando os endpoints
- A API possui autenticação JWT no seu endpoint, e usa a variavel ambiente `JWT_SECRET_KEY` para fazer a validação, caso a mesma esteja setada como `stubJwt` o token a ser utilizado na chamada é `Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.e30.29KNIUWCjzIfetCqXVcFL3Ok7OJxdQAY9qCYNGaS_KQ` caso a variavel tenha sido alterado basta acessar https://jwt.io/ gerar um hash (payload deve ser {}) usando o novo secret e setar no header como `Bearer {token}`.

### Coleção Postman
- https://www.getpostman.com/collections/5c03d0b1545b6ca3fe99





