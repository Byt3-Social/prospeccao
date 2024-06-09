package com.byt3social.prospeccao.services;

import com.byt3social.prospeccao.dto.IndicacaoDTO;
import com.byt3social.prospeccao.dto.OrganizacaoDTO;
import com.byt3social.prospeccao.enums.Status;
import com.byt3social.prospeccao.models.Categoria;
import com.byt3social.prospeccao.models.Indicacao;
import com.byt3social.prospeccao.models.Organizacao;
import com.byt3social.prospeccao.repositories.CategoriaRepository;
import com.byt3social.prospeccao.repositories.IndicacaoRepository;
import com.byt3social.prospeccao.repositories.OrganizacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizacaoService {
    @Autowired
    private OrganizacaoRepository organizacaoRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private IndicacaoRepository indicacaoRepository;

    @Transactional
    public boolean cadastrarOrganizacao(OrganizacaoDTO dadosOrganizacao) {
        if(organizacaoRepository.findByCnpj(dadosOrganizacao.cnpj()) != null) {
            return false;
        }

        Organizacao organizacao = new Organizacao(dadosOrganizacao);
        organizacao = organizacaoRepository.save(organizacao);

        rabbitTemplate.convertAndSend("prospeccao.ex","", organizacao);

        return true;
    }

    @Transactional
    public void atualizarOrganizacaoCadastrada(Integer organizacaoID, OrganizacaoDTO dadosOrganizacao) {
        Organizacao organizacao = organizacaoRepository.findById(organizacaoID).get();
        organizacao.atualizar(dadosOrganizacao);
    }

    public List<Organizacao> consultarOrganizacoesCadastradas() {
        return organizacaoRepository.findAllByStatusCadastroNot(Status.INDICADO, Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public Organizacao consultarOrganizacaoCadastrada(Integer organizacaoID) {
        return organizacaoRepository.findById(organizacaoID).get();
    }

    @Transactional
    public void atualizarStatusOrganizacao(OrganizacaoDTO organizacaoDTO) {
        Organizacao organizacao = organizacaoRepository.findById(organizacaoDTO.id()).get();
        organizacao.atualizarStatus(organizacaoDTO);
    }

    @Transactional
    public void indicarOrganizacao(IndicacaoDTO indicacaoDTO) {
        Categoria categoria = categoriaRepository.getReferenceById(indicacaoDTO.categoriaId());
        Indicacao indicacao = new Indicacao(indicacaoDTO, categoria);
        indicacaoRepository.save(indicacao);
    }
}
