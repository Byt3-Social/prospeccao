package com.byt3social.prospeccao.models;

import com.byt3social.prospeccao.enums.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity(name = "Cadastro")
@Table(name = "cadastros")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class Cadastro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String cnpj;
    @Column(name = "nome_organizacao")
    private String nomeOrganizacao;
    @Column(name = "email_organizacao")
    private String emailOrganizacao;
    @Column(name = "telefone_organizacao")
    private String telefoneOrganizacao;
    @Embedded
    private Responsavel responsavel;
    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
    @OneToOne(mappedBy = "cadastro")
    @JsonBackReference
    private Indicacao indicacao;
}
