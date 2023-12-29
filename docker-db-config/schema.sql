CREATE DATABASE cliente;
USE cliente;

CREATE TABLE cliente.cadastro_cliente
(
    id serial NOT NULL DEFAULT,
    nome character varying(100) COLLATE pg_catalog."default" NOT NULL,
    email character varying(100) COLLATE pg_catalog."default" NOT NULL,
    cpf character varying(14) COLLATE pg_catalog."default" NOT NULL,
    renda numeric NOT NULL,
    telefone character varying(20) COLLATE pg_catalog."default",
    data_criacao timestamp without time zone NOT NULL,
    senha character varying(255) COLLATE pg_catalog."default" NOT NULL
);