CREATE TABLE `organizacoes`(
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `cnpj` VARCHAR(255) NOT NULL UNIQUE,
    `nome` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `telefone` VARCHAR(255) NOT NULL,
    `nome_responsavel` VARCHAR(255) NOT NULL,
    `email_responsavel` VARCHAR(255) NOT NULL,
    `telefone_responsavel` VARCHAR(255) NOT NULL,
    `status_cadastro` VARCHAR(255) NOT NULL,
    `data_cadastro` TIMESTAMP NOT NULL
);