package com.byt3social.prospeccao.models;

import com.byt3social.prospeccao.dto.OrganizacaoDTO;
import com.byt3social.prospeccao.enums.Status;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity(name = "Organizacao")
@Table(name = "organizacoes")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
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
    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
    @OneToOne
    @JoinColumn(name = "indicador_id")
    @JsonManagedReference
    private Indicacao indicacao;

    public Organizacao(OrganizacaoDTO dados) {
        this.cnpj = dados.cnpj();
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.responsavel = new Responsavel(dados.responsavel());
        this.statusCadastro = Status.CADASTRADO;
    }

    public void atualizar(OrganizacaoDTO dadosOrganizacao) {
        if(dadosOrganizacao.cnpj() != null && this.statusCadastro == Status.INDICADO) {
            this.cnpj = dadosOrganizacao.cnpj();
        }

        this.nome = dadosOrganizacao.nome();
        this.email = dadosOrganizacao.email();
        this.telefone = dadosOrganizacao.telefone();

        if(dadosOrganizacao.responsavel() != null) {
            this.responsavel.atualizar(dadosOrganizacao.responsavel());
        }
    }

    public void atualizarStatus(OrganizacaoDTO organizacaoDTO) {
        this.statusCadastro = organizacaoDTO.statusCadastro();
    }
}
