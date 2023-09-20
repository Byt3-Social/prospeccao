package com.byt3social.prospeccao.models;

import com.byt3social.prospeccao.dto.OrganizacaoDTO;
import com.byt3social.prospeccao.enums.Status;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

@Entity(name = "Organizacao")
@Table(name = "organizacoes")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class Organizacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String cnpj;
    private String nome;
    private String email;
    private String telefone;
    @Embedded
    private Responsavel responsavel;
    @Column(name = "status_cadastro")
    @Enumerated(EnumType.STRING)
    private Status statusCadastro;
    @Column(name = "data_cadastro")
    private Date dataCadastro;

    public Organizacao(OrganizacaoDTO dados) {
        this.cnpj = dados.cnpj();
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.responsavel = new Responsavel(dados.responsavel());
        this.statusCadastro = Status.CADASTRADO;
        this.dataCadastro = Date.from(Instant.now());
    }
}
