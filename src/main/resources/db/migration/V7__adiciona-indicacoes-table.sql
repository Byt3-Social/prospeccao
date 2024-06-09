CREATE TABLE `indicacoes`(
    `id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `nome_organizacao` VARCHAR(255) NOT NULL,
    `nome_responsavel` VARCHAR(255) NOT NULL,
    `email_responsavel` VARCHAR(255) NOT NULL,
    `telefone_responsavel` VARCHAR(255) NOT NULL,
    `tipo_acao` VARCHAR(255) NOT NULL,
    `abrangencia` VARCHAR(255) NOT NULL,
    `status` VARCHAR(255) NOT NULL,
    `texto_indicacao` TEXT NOT NULL,
    `colaborador_id` INT UNSIGNED NOT NULL,
    `categoria_id` INT UNSIGNED NOT NULL,
    `cadastro_id` INT UNSIGNED NOT NULL,
    `invited_at` TIMESTAMP NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_id` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY(categoria_id) REFERENCES categorias(id),
    FOREIGN KEY(cadastro_id) REFERENCES cadastros(id)
);