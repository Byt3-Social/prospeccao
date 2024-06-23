ALTER TABLE indicacoes RENAME COLUMN colaborador_id TO colaborador;

ALTER TABLE indicacoes MODIFY COLUMN colaborador varchar(255) NOT NULL;