## Hugo Fonseca - Code Challenge

### Pré requisitos

```
Docker e docker-compose
```

Para instalar a ferramenta, execute apenas o comando abaixo:  

```
make install-dependencies
```

Para rodar o projeto, execute o comando abaixo: 

```
make run
```

Para verificar a documentação gerada pelo Swagger basta apenas acessar a url
abaixo depois que o projeto estiver rodando: 

```
http://localhost:8000/challenge/swagger-ui.html#/
```

Para verificar o health da aplicação:

```
http://localhost:8000/challenge/actuator/health
```

Durante o desenvolvimento acabei gerando uma collection no Postman. Caso queira
usar para testar a aplicação basta importar via link. 

```
https://www.getpostman.com/collections/85a9c9f75b73a3b0d977
```



