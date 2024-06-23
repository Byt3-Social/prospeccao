package com.byt3social.prospeccao.dto;

import com.byt3social.prospeccao.enums.Abrangencia;
import com.byt3social.prospeccao.enums.TipoAcao;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record IndicacaoDTO(
        @NotBlank(message = "Campo obrigatório")
        String nomeOrganizacao,
        @NotNull
        @Valid
        ResponsavelIndicacaoDTO responsavel,
        @NotNull(message = "Campo obrigatório")
        TipoAcao tipoAcao,
        @NotNull(message = "Campo obrigatório")
        Abrangencia abrangencia,
        @NotBlank(message = "Campo obrigatório")
        String textoIndicacao,
        String colaborador,
        @NotNull(message = "Campo obrigatório")
        Integer categoriaId
) {
}
