--liquibase formatted sql
--changeset humberto.streb:0010 splitStatements:true endDelimiter:;

ALTER TABLE votacoes ALTER COLUMN horario_fim TYPE timestamp(0) USING horario_fim::timestamp;
ALTER TABLE votacoes ALTER COLUMN horario_inicio TYPE timestamp(0) USING horario_inicio::timestamp;
ALTER TABLE votacoes ALTER COLUMN horario_criacao TYPE timestamp(0) USING horario_criacao::timestamp;

ALTER TABLE votos ALTER COLUMN horario_criacao TYPE timestamp(0) USING horario_criacao::timestamp;

ALTER TABLE votacoes ALTER horario_inicio DROP NOT NULL;
ALTER TABLE votacoes ALTER horario_fim DROP NOT NULL;

--ALTER TABLE votacoes ALTER COLUMN horario_fim TYPE date(6) USING horario_fim::date;
--ALTER TABLE votacoes ALTER COLUMN horario_inicio TYPE date(6) USING horario_inicio::date;
--ALTER TABLE votacoes ALTER COLUMN horario_criacao TYPE date(6) USING horario_criacao::date;

--ALTER TABLE votos ALTER COLUMN horario_criacao TYPE date(6) USING horario_criacao::date;

--rollback ALTER TABLE votacoes ALTER horario_inicio SET NOT NULL;
--rollback ALTER TABLE votacoes ALTER horario_fim SET NOT NULL;
