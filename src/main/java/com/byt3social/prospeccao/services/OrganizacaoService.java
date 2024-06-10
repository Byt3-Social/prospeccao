package com.byt3social.prospeccao.services;

import com.byt3social.prospeccao.dto.*;
import com.byt3social.prospeccao.enums.Status;
import com.byt3social.prospeccao.enums.StatusIndicacao;
import com.byt3social.prospeccao.models.Cadastro;
import com.byt3social.prospeccao.models.Categoria;
import com.byt3social.prospeccao.models.Indicacao;
import com.byt3social.prospeccao.models.Organizacao;
import com.byt3social.prospeccao.repositories.CadastroRepository;
import com.byt3social.prospeccao.repositories.CategoriaRepository;
import com.byt3social.prospeccao.repositories.IndicacaoRepository;
import com.byt3social.prospeccao.repositories.OrganizacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Autowired
    private CadastroRepository cadastroRepository;
    @Autowired
    private EmailService emailService;
    @Value("${com.byt3social.app.domain}")
    private String urlBaseIndicacao;

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

    public List<Indicacao> buscarIndicacoes() {
        return indicacaoRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public Indicacao buscarIndicacao(Integer indicacaoid) {
        return indicacaoRepository.findById(indicacaoid).get();
    }

    @Transactional
    public void salvarFormularioIndicacao(CadastroDTO cadastroDTO) {
        Indicacao indicacao = indicacaoRepository.getReferenceById(cadastroDTO.indicacaoId());
        indicacao.atualizarStatus(StatusIndicacao.FORM_PREENCHIDO);
        Cadastro cadastro = new Cadastro(cadastroDTO, indicacao);
        cadastroRepository.save(cadastro);
        indicacao.registrarCadastro(cadastro);
    }

    @Transactional
    public void atualizarStatusIndicacao(Integer indicaoId, IndicacaoStatusDTO indicacaoStatusDTO) {
        Indicacao indicacao = indicacaoRepository.findById(indicaoId).get();

        if(indicacaoStatusDTO.status() == StatusIndicacao.CONVIDADA) {
            String linkIndicacao = urlBaseIndicacao + "/organizacao/indicacoes/" + indicacao.getId() + "/concluir";
            emailService.notificarIndicacao(indicacao, linkIndicacao);
            indicacao.atualizarDataConvite();
        } else if (indicacaoStatusDTO.status() == StatusIndicacao.CONCLUIDA) {
            Cadastro cadastro = indicacao.getCadastro();

            OrganizacaoDTO organizacaoDTO = new OrganizacaoDTO(
                null,
                    cadastro.getCnpj(),
                    cadastro.getNomeOrganizacao(),
                    cadastro.getEmailOrganizacao(),
                    cadastro.getTelefoneOrganizacao(),
                    new ResponsavelDTO(
                            cadastro.getResponsavel().getNome(),
                            cadastro.getResponsavel().getCpf(),
                            cadastro.getResponsavel().getEmail(),
                            cadastro.getResponsavel().getTelefone()
                    ),
                    null
            );

            if(!this.cadastrarOrganizacao(organizacaoDTO)) {
                throw new RuntimeException("Não foi possível converter a indicação!");
            }

            Organizacao organizacao = organizacaoRepository.findByCnpj(organizacaoDTO.cnpj());
            organizacao.definirIndicacao(indicacao);
        }

        indicacao.atualizarStatus(indicacaoStatusDTO.status());
    }

    public List<Categoria> buscarCategoriasIndicacao() {
        return categoriaRepository.findAll();
    }
}
