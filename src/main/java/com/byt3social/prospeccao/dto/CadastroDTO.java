package com.byt3social.prospeccao.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CadastroDTO(
        @NotNull(message = "Solicitação inválida")
        Integer indicacaoId,
        @NotBlank(message = "Campo obrigatório")
        @Pattern(regexp = "\\d{14}", message = "Campo inválido")
        String cnpj,
        @NotBlank(message = "Campo obrigatório")
        String nomeOrganizacao,
        @NotBlank(message = "Campo obrigatório")
        @Email(message = "Campo inválido")
        String emailOrganizacao,
        @NotBlank(message = "Campo obrigatório")
        @Pattern(regexp = "\\d{10,11}", message = "Campo inválido")
        String telefoneOrganizacao,
        @NotNull
                @Valid
        ResponsavelDTO responsavel
) {
}
