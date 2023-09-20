package com.byt3social.prospeccao.services;

import com.byt3social.prospeccao.dto.OrganizacaoDTO;
import com.byt3social.prospeccao.models.Organizacao;
import com.byt3social.prospeccao.repositories.OrganizacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProspeccaoService {
    @Autowired
    private OrganizacaoRepository organizacaoRepository;

    @Transactional
    public void cadastraOrganizacao(OrganizacaoDTO dados) {
        Organizacao organizacao = new Organizacao(dados);
        organizacaoRepository.save(organizacao);
    }
}
