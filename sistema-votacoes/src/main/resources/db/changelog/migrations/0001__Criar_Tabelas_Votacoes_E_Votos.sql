--liquibase formatted sql
--changeset humberto.streb:0001 splitStatements:true endDelimiter:;

CREATE TABLE votacoes (
	id varchar(36) NOT NULL,
	pauta varchar(1024) NOT NULL,
	estado varchar(20) NOT NULL,
	duracao bigint NOT NULL,
	total_sim bigint NOT NULL,
	total_nao bigint NOT NULL,
	horario_criacao date NOT NULL,
	horario_inicio date NOT NULL,
	horario_fim date NOT NULL,
	CONSTRAINT votacoes_pk PRIMARY KEY (id)
);

CREATE TABLE votos (
	id varchar(36) NOT NULL,
	votacao varchar(36) NOT NULL,
	associado varchar(100) NOT NULL,
	escolha varchar(10) NOT NULL,
	horario_criacao date NOT NULL,
	CONSTRAINT votos_pk PRIMARY KEY (id),
	CONSTRAINT votos_un UNIQUE (votacao, associado)
);
