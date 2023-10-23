package com.byt3social.prospeccao.repositories;

import com.byt3social.prospeccao.enums.Status;
import com.byt3social.prospeccao.models.Organizacao;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrganizacaoRepository extends JpaRepository<Organizacao, Integer> {
    Organizacao findByCnpj(String cnpj);
    List<Organizacao> findAllByStatusCadastroNot(Status status, Sort sort);
}
