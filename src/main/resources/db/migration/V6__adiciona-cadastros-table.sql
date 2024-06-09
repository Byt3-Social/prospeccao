CREATE TABLE `cadastros`(
    `id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `cnpj` VARCHAR(255) NOT NULL,
    `nome_organizacao` VARCHAR(255) NOT NULL,
    `email_organizacao` VARCHAR(255) NOT NULL,
    `telefone_organizacao` VARCHAR(255) NOT NULL,
    `nome_responsavel` VARCHAR(255) NOT NULL,
    `cpf_responsavel` VARCHAR(255) NOT NULL,
    `email_responsavel` VARCHAR(255) NOT NULL,
    `telefone_responsavel` VARCHAR(255) NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_id` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);