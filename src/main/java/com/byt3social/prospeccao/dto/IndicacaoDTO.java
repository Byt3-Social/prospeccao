package com.byt3social.prospeccao.dto;

import com.byt3social.prospeccao.enums.Abrangencia;
import com.byt3social.prospeccao.enums.StatusIndicacao;
import com.byt3social.prospeccao.enums.TipoAcao;
import com.fasterxml.jackson.annotation.JsonProperty;

public record IndicacaoDTO(
        String nomeOrganizacao,
        ResponsavelDTO responsavel,
        TipoAcao tipoAcao,
        Abrangencia abrangencia,
        String textoIndicacao,
        Integer colaboradorId,
        Integer categoriaId
) {
}
