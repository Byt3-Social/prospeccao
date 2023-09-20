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

    @Transactional
    public void atualizarOrganizacao(OrganizacaoDTO dados) {
        Organizacao organizacao = organizacaoRepository.findById(dados.id()).get();
        organizacao.atualizaDados(dados);
    }

    public List<Organizacao> consultaPotenciaisOrganizacoes() {
        return organizacaoRepository.findAll();
    }

    public Organizacao consultaPotencialOrganizacao(Integer id) {
        return organizacaoRepository.findById(id).get();
    }
}
