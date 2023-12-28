# Cadastro de Clientes

BACKEND
 Criar uma api rest, implementando um CRUD para o cadastro de Clientes (ver campos abaixo).
 
 A API deve ser feita preferencialmente em SpringBoot (versão maior ou igual a 3.0.0), java 17 e o banco de dados (preferencialmente Postgres).
 
 Tabela:
 
 CLIENTE
 
 id-> inteiro e sequencial
 
 nome-> String com 255 caracteres
 
 email-> String com 255 caracteres
 
 cpf-> String com 11 caracteres
 
 renda-> decimal com 7 na parte inteira e 2 na parte fracionária
 
 telefone-> string deve ser capaz de gravar o ddd + número do telefone
 
 data_criacao-> compo data em formato de data e hora, na api deve ser tratado como
 
 UTC eprecisa ser parte da api do cadastro- Não gerar automaticamente.
 
 ● Apenas o campo telefone não é obrigatório.
 
 Desafios:
 
 ● API com autenticação, dois usuários: admin e comum, apenas admin pode realizar
 exclusão - Fazer validação preferencialmente no código.
 
 ● Usar record como DTO.
 
 ● Usar campo sequencial no banco.
 
 ● Usar as devidas validações para os campos tanto na api e no banco de dados.
 
 ● Mensagens adequadas para exibir no frontend.
 
 ● Não aceitar nomes repetidos.
 
 ● Não aceitar cpf repetidos.
 
 ● Rodar o banco em docker.
 
 ● Configurar a API para ser distribuída dentro de um docker.
