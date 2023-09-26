package com.byt3social.prospeccao.dto;

import com.byt3social.prospeccao.enums.Status;
import com.byt3social.prospeccao.models.Responsavel;
import com.byt3social.prospeccao.validation.OnCreateValidation;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import org.springframework.validation.annotation.Validated;

public record OrganizacaoDTO(
        Integer id,
        @NotBlank(message = "cnpj não pode ser vazio", groups = OnCreateValidation.class)
        @Pattern(regexp = "\\d{14}", message = "cnpj inválido", groups = OnCreateValidation.class)
        String cnpj,
        @NotBlank(message = "nome não pode ser vazio")
        String nome,
        @NotBlank(message = "email não pode ser vazio")
        @Email(message = "email inválido")
        String email,
        @NotBlank(message = "telefone não pode ser vazio")
        @Pattern(regexp = "\\d{10,11}", message = "telefone inválido")
        String telefone,
        @NotNull(message = "dados do responsável não pode ser vazio")
        @Valid
        ResponsavelDTO responsavel,
        @JsonProperty("status_cadastro")
        Status statusCadastro
) {}
