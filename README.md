# API SISTEMA A3
API Rest utilizando o ecossistema Spring Boot, Spring MVC, Spring WEB, 
Spring Data, Spring Security, JWT, Lombok, DevTools, ModelMapper, Flyway, 
Bean Validation, Hibernate ORM, MySQL DataBase, Swagger2.

# IDE
Utilizado o STS - Spring Tools Suite 4

# PLUGINS PARA IDE
Necessário instalar o Lombok.

https://projectlombok.org/downloads/lombok.jar
Para ajudar na instalação https://projectlombok.org/setup/eclipse


# INSTALAÇÃO

Clonar o repositório e configurar a pasta princippal do STS a mesma que recebeu o projeto.
Ao iniciar o projeto com o STS o mesmo atualizarar as dependencias de acordo com o POM.XML, caso não faça vá no pasta 
principal e clica com o botão direito, procure Maven, e depois em Update Project, só aguardar.
Necessário ter instalado um SGBD MySQL e configurar o Usuário, Senha e URL no src/main/resources/aplication.properties
Obs. Não á necessidade de criar o Banco de Dados, o Sprig Data JPA já faz isso usando o; delivery?createDatabaseIfNotExist=true.

# EXEMPLO POM.XML
spring.datasource.url=jdbc:mysql://SEU_URL_DO_BANCO_DE_DADOS:3306/delivery?createDatabaseIfNotExist=true&serverTimezone=UTC
spring.datasource.username=USUARIO BANCO
spring.datasource.password=SENHA DO BANCO

spring.jpa.show-sql=true HABILITANDO MOSTRARÁ AS CONSULTAS DO BANCO NO CONSOLE

spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect

#Configurações JWT
jwt.secret=#qweernadnamdn19820918209!#ajhad SENHA SECRETA
jwt.expiration=600000 TEMPO DE EXPIRAÇÃO DO TOKEN JWT
 
#Configuração API
spring.profiles.active=dev PERFIL DE INICIALIZAÇÃO
prop.swagger.enabled=true HABILITANDO A DOCUMENTAÇÃO AUTOMÁTICA DA API 
spring.mvc.pathmatch.matching-strategy=ant-path-matcher 

#Configuração FRONTEND
front.baseurl=http://localhost:3000 BASE URL DO FRONT-END CONFIGURAÇÃO DO CORS


# ENDPOINTS

ROTAS COM * SERÃO ROTAS PROTEGIDAS;
#
/*ROTAS DE AUTENTICAÇÃO*/
#
'API/V1/AUTH'; GET - PASSA UM USUARIO PARA OBTER UM TOKEN DE ACESSO E DADOS DO USUARIO
#
#
Exemplo de requisição:
#
#
{
  "email": "string",
  "password": "string"
}
#
Como resposta a API devolve:
#
#
{
  "data": {
    "email": "string",
    "id": 0,
    "nome": "string",
    "password": "string",
    "sobrenome": "string",
    "token": "string",
    "userperfil": "ADMIN"
  }
}
#
#
/*ROTAS DE USUÁRIOS*/
#
*'API/V1/USERS' GET - OBTER LISTA DE USUARIOS (controlado pelo perfil do usuário logado, retornando apenas o seu usuário, caso seja admin retorna full)
#
Exemplo de requisição:
#
#
{
  "data": [
    {
      "email": "string",
      "id": 0,
      "nome": "string",
      "password": "string",
      "sobrenome": "string",
      "token": "string",
      "userperfil": "ADMIN"
    }
  ]
}
# 
#
*'API/V1/USER/{IDUSUARIO}' GET - OBTEM UM USUÁRIO ESPECIFICO PASSANDO ID
#
#
Exemplo do retorno:
#
#
{
  "data": {
    "email": "string",
    "id": 0,
    "nome": "string",
    "password": "string",
    "sobrenome": "string",
    "token": "string",
    "userperfil": "ADMIN"
  }
}
#
#
 'API/V1/USER/{OBJ.USER}' POST - CADASTRAR NOVO USUÁRIO PASSANDO OBJETO JSON
#
# 
Exemplo da requisição:
#
#
{
  "email": "string",
  "id": 0,
  "nome": "string",
  "password": "string",
  "sobrenome": "string",
  "token": "string",
  "userperfil": "ADMIN" ou "USUARIO"
}
#
# 
*'API/V1/USER/{OBJ.USER}' PUT - ATUALIZAR UM USUÁRIO PASSANDO OBJETO JSON
#
#
Exemplo da requisição;
#
#
{
  "email": "string",
  "id": 1, Obs. O ID tem de sero do usuario a ser atualizado.
  "nome": "string",
  "password": "string",
  "sobrenome": "string",
  "token": "string",
  "userperfil": "ADMIN"
}
#
#
*'API/V1/USER/{IDUSUARIO} DELETE - DELETAR USUÁRIO PASSANDO ID
#
#
Exemplo da requisição:
#
#
http://localhost/API/V1/USER/1, isso deletará o usuário do ID 1, se o usuário que solicitou for um ADMIN.
#
#
/*ROTAS DO CADASTRO DE CLIENTES*/
#
#
*'API/V1/CLIENTE/ GET - OBTER LISTA DE USUARIOS (o sistema devolve apenas os clientes cadastrados do usuário logado, caso seja o ADMIN traz todos)
#
#
Exemplo de resposta:
#
#
{
  "data": [
    {
      "cpf": "string",
      "email": "string",
      "end_cep": "string",
      "end_cidade": "string",
      "end_complemento": "string",
      "end_estado": "string",
      "end_numero": "string",
      "end_rua": "string",
      "id": 0,
      "nome": "string",
      "sobrenome": "string",
      "telefone": "string",
      "user": {
        "email": "string",
        "id": 0,
        "nome": "string",
        "password": "string",
        "sobrenome": "string",
        "token": "string",
        "userperfil": "ADMIN"
      }
    }
  ]
}
#
#
*'API/V1/CLIENTE/{IDCLIENTE} GET - OBTEM UM CLIENTE ESPECIFICO PASSANDO ID
#
#
Exemplo de resposta:
#
#
{
  "data": {
    "cpf": "string",
    "email": "string",
    "end_cep": "string",
    "end_cidade": "string",
    "end_complemento": "string",
    "end_estado": "string",
    "end_numero": "string",
    "end_rua": "string",
    "id": 0,
    "nome": "string",
    "sobrenome": "string",
    "telefone": "string",
    "user": {
      "email": "string",
      "id": 0,
      "nome": "string",
      "password": "string",
      "sobrenome": "string",
      "token": "string",
      "userperfil": "ADMIN"
    }
  },
  "errors": {}
}
#
#
*'API/V1/CLIENTE/{OBJ.CLIENTE} POST - CADASTRAR NOVO CLIENTE PASSANDO OBJETO JSON
#
#
Exemplo de requisição:
#
#
{
  "cpf": "string",
  "email": "string",
  "end_cep": "string",
  "end_cidade": "string",
  "end_complemento": "string",
  "end_estado": "string",
  "end_numero": "string",
  "end_rua": "string",
  "id": 0,
  "nome": "string",
  "sobrenome": "string",
  "telefone": "string",
  "user": {
    "id": 0,
  }
}
# 
#  
*'API/V1/CLIENTE/{OBJ.CLIENTE} PUT - ATUALIZAR UM CLIENTE PASSANDO OBJETO JSON
#
#
Exemplo de requisição:
#
#
{
  "cpf": "string",
  "email": "string",
  "end_cep": "string",
  "end_cidade": "string",
  "end_complemento": "string",
  "end_estado": "string",
  "end_numero": "string",
  "end_rua": "string",
  "id": 0, OBS. O ID tem que ser o do cliente a ser atualizado. 
  "nome": "string",
  "sobrenome": "string",
  "telefone": "string",
  "user": {
    "id": 0,
  }
}
#
#
*'API/V1/CLIENTE/{IDCLIENTE} DELETE - DELETAR CLIENTE PASSANDO ID
#
#
Exemplo de requisição:
#
#
http://localhost/API/V1/CLIENTE/1 a api irá excluir o cliente do id 1.



