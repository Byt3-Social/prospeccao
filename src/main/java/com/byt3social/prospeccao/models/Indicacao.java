package com.byt3social.prospeccao.models;

import com.byt3social.prospeccao.dto.IndicacaoDTO;
import com.byt3social.prospeccao.enums.Abrangencia;
import com.byt3social.prospeccao.enums.StatusIndicacao;
import com.byt3social.prospeccao.enums.TipoAcao;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity(name = "Indicacao")
@Table(name = "indicacoes")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class Indicacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nome_organizacao")
    private String nomeOrganizacao;
    @Embedded
    private ResponsavelIndicacao responsavel;
    @Column(name = "tipo_acao")
    @Enumerated(EnumType.STRING)
    private TipoAcao tipoAcao;
    @Enumerated(EnumType.STRING)
    private Abrangencia abrangencia;
    @Enumerated(EnumType.STRING)
    private StatusIndicacao status;
    @Column(name = "texto_indicacao")
    private String textoIndicacao;
    private String colaborador;
    @Column(name = "invited_at")
    private Date invitedAt;
    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    @JsonManagedReference
    private Categoria categoria;
    @OneToOne
    @JoinColumn(name = "cadastro_id")
    @JsonManagedReference
    private Cadastro cadastro;
    @OneToOne(mappedBy = "indicacao")
    @JsonBackReference
    private Organizacao organizacao;

    public Indicacao(IndicacaoDTO indicacaoDTO, Categoria categoria) {
        this.nomeOrganizacao = indicacaoDTO.nomeOrganizacao();
        this.responsavel = new ResponsavelIndicacao(indicacaoDTO.responsavel());
        this.tipoAcao = indicacaoDTO.tipoAcao();
        this.abrangencia = indicacaoDTO.abrangencia();
        this.status = StatusIndicacao.INDICADA;
        this.textoIndicacao = indicacaoDTO.textoIndicacao();
        this.colaborador = indicacaoDTO.colaborador();
        this.categoria = categoria;
    }

    public void atualizarStatus(StatusIndicacao status) {
        this.status = status;
    }

    public void atualizarDataConvite() {
        this.invitedAt = new Date();
    }

    public void registrarCadastro(Cadastro cadastro) {
        this.cadastro = cadastro;
    }
}
