package com.byt3social.prospeccao.dto;

import com.byt3social.prospeccao.enums.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record OrganizacaoDTO(
        Integer id,
        @NotBlank(message = "Campo obrigatório")
        @Pattern(regexp = "\\d{14}", message = "Campo inválido")
        String cnpj,
        @NotBlank(message = "Campo obrigatório")
        String nome,
        @NotBlank(message = "Campo obrigatório")
        @Email(message = "Campo inválido")
        String email,
        @NotBlank(message = "Campo obrigatório")
        @Pattern(regexp = "\\d{10,11}", message = "Campo inválido")
        String telefone,
        @NotNull
        @Valid
        ResponsavelDTO responsavel,
        Status statusCadastro
) {}
