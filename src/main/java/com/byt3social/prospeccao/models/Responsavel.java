package com.byt3social.prospeccao.models;

import com.byt3social.prospeccao.dto.ResponsavelDTO;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Responsavel {
    @Column(name = "nome_responsavel")
    private String nome;
    @Column(name = "email_responsavel")
    private String email;
    @Column(name = "telefone_responsavel")
    private String telefone;

    public Responsavel(ResponsavelDTO dados) {
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
    }

    public void atualizaDados(ResponsavelDTO dados) {
        if(dados.nome() != null) {
            this.nome = dados.nome();
        }

        if(dados.email() != null) {
            this.email = dados.email();
        }

        if(dados.telefone() != null) {
            this.telefone = dados.telefone();
        }
    }
}
