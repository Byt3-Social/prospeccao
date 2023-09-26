package com.byt3social.prospeccao.models;

import com.byt3social.prospeccao.dto.OrganizacaoDTO;
import com.byt3social.prospeccao.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    @Column(updatable = false)
    private String cnpj;
    private String nome;
    private String email;
    private String telefone;
    @Embedded
    private Responsavel responsavel;
    @Column(name = "status_cadastro")
    @JsonProperty("status_cadastro")
    @Enumerated(EnumType.STRING)
    private Status statusCadastro;
    @CreationTimestamp
    @Column(name = "created_at")
    @JsonProperty("created_at")
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonProperty("update_at")
    private Date updatedAt;

    public Organizacao(OrganizacaoDTO dados) {
        this.cnpj = dados.cnpj();
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.responsavel = new Responsavel(dados.responsavel());
        this.statusCadastro = Status.CADASTRADO;
        this.createdAt = Date.from(Instant.now());
    }

    public void atualizar(OrganizacaoDTO dadosOrganizacao) {
        if(dadosOrganizacao.cnpj() != null) {
            this.cnpj = dadosOrganizacao.cnpj();
        }

        if(dadosOrganizacao.nome() != null) {
            this.nome = dadosOrganizacao.nome();
        }

        if(dadosOrganizacao.email() != null) {
            this.email = dadosOrganizacao.email();
        }

        if(dadosOrganizacao.telefone() != null) {
            this.telefone = dadosOrganizacao.telefone();
        }

        if(dadosOrganizacao.responsavel() != null) {
            this.responsavel.atualizar(dadosOrganizacao.responsavel());
        }
    }

    public void atualizarStatus(OrganizacaoDTO organizacaoDTO) {
        this.statusCadastro = organizacaoDTO.statusCadastro();
    }
}
