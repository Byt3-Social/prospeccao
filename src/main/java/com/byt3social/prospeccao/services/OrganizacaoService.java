package com.byt3social.prospeccao.services;

import com.byt3social.prospeccao.dto.OrganizacaoDTO;
import com.byt3social.prospeccao.models.Organizacao;
import com.byt3social.prospeccao.repositories.OrganizacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizacaoService {
    @Autowired
    private OrganizacaoRepository organizacaoRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Transactional
    public void cadastrarOrganizacao(OrganizacaoDTO dadosOrganizacao) {
        Organizacao organizacao = new Organizacao(dadosOrganizacao);
        organizacao = organizacaoRepository.save(organizacao);

        rabbitTemplate.convertAndSend("prospeccao.ex","organizacao.cadastrada", organizacao);
    }

    @Transactional
    public void atualizarOrganizacaoCadastrada(Integer organizacaoID, OrganizacaoDTO dadosOrganizacao) {
        Organizacao organizacao = organizacaoRepository.findById(organizacaoID).get();
        organizacao.atualizar(dadosOrganizacao);
    }

    public List<Organizacao> consultarOrganizacoesCadastradas() {
        return organizacaoRepository.findAll();
    }

    public Organizacao consultarOrganizacaoCadastrada(Integer organizacaoID) {
        return organizacaoRepository.findById(organizacaoID).get();
    }

    @Transactional
    public void atualizarStatusOrganizacao(OrganizacaoDTO organizacaoDTO) {
        Organizacao organizacao = organizacaoRepository.findById(organizacaoDTO.id()).get();
        organizacao.atualizarStatus(organizacaoDTO);
    }
}