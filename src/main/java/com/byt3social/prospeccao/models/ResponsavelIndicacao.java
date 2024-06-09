package com.byt3social.prospeccao.models;

import com.byt3social.prospeccao.dto.ResponsavelDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponsavelIndicacao {
    @Column(name = "nome_responsavel")
    private String nome;
    @Column(name = "email_responsavel")
    private String email;
    @Column(name = "telefone_responsavel")
    private String telefone;

    public ResponsavelIndicacao(ResponsavelDTO responsavelDTO) {
        this.nome = responsavelDTO.nome();
        this.email = responsavelDTO.email();
        this.telefone = responsavelDTO.telefone();
    }
}
