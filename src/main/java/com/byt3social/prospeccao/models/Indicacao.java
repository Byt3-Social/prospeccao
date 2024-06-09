package com.byt3social.prospeccao.models;

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
    private Responsavel responsavel;
    @Column(name = "tipo_acao")
    @Enumerated(EnumType.STRING)
    private TipoAcao tipoAcao;
    @Enumerated(EnumType.STRING)
    private Abrangencia abrangencia;
    @Enumerated(EnumType.STRING)
    private StatusIndicacao status;
    @Column(name = "texto_indicado")
    private String textoIndicacao;
    @Column(name = "colaborador_id")
    private Integer colaboradorId;
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
}