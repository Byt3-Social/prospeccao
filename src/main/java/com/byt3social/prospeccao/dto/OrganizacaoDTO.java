package com.byt3social.prospeccao.dto;

import com.byt3social.prospeccao.enums.Status;
import com.byt3social.prospeccao.models.Responsavel;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record OrganizacaoDTO(
        Integer id,
        @NotBlank
        @Pattern(regexp = "\\d{14}")
        String cnpj,
        @NotBlank
        String nome,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Pattern(regexp = "\\d{10,11}")
        String telefone,
        @NotNull
        @Valid
        ResponsavelDTO responsavel,
        @JsonProperty("status_cadastro")
        Status statusCadastro
) {}
