package com.byt3social.prospeccao.repositories;
import com.byt3social.prospeccao.models.Organizacao;
import com.byt3social.prospeccao.models.Responsavel;
import com.byt3social.prospeccao.dto.OrganizacaoDTO;
import com.byt3social.prospeccao.dto.ResponsavelDTO;
import com.byt3social.prospeccao.enums.Status;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class OrganizacaoRepositoryTest {

    @Autowired
    private OrganizacaoRepository organizacaoRepository;
    
    @Test
    public void testCriarResponsavel() {
        ResponsavelDTO responsavelDTO = new ResponsavelDTO("Maria", "12345678911", "maria@gmail.com", "6799999999");
        Responsavel responsavel = new Responsavel(responsavelDTO);

        assertEquals("Maria", responsavel.getNome());
        assertEquals("maria@gmail.com", responsavel.getEmail());
        assertEquals("6799999999", responsavel.getTelefone());
    }

    @Test
    public void testAtualizarResponsavel() {
        ResponsavelDTO responsavelDTO = new ResponsavelDTO("Maria", "12345678911","maria@gmail.com", "6799999999");
        Responsavel responsavel = new Responsavel(responsavelDTO);

        ResponsavelDTO updatedResponsavelDTO = new ResponsavelDTO("João", "9876543210", "joao@gmail.com", "6798888888");
        responsavel.atualizar(updatedResponsavelDTO);

        assertEquals("João", responsavel.getNome());
        assertEquals("9876543210", responsavel.getCpf());
        assertEquals("joao@gmail.com", responsavel.getEmail());
        assertEquals("6798888888", responsavel.getTelefone());
    }
    
    @Test
    public void testInserirOrganizacao() {
        ResponsavelDTO responsavelDTO = new ResponsavelDTO("Alberto", "12345678911", "alberto@gmail.com", "67992455588");
        OrganizacaoDTO organizacaoDTO = new OrganizacaoDTO(
                null, 
                "12345678901234",
                "Nome da Organização",
                "email@exemplo.com",
                "1234567890", 
                responsavelDTO,
                Status.CADASTRADO
        );

        Organizacao organizacao = new Organizacao(organizacaoDTO);
        Organizacao organizacaoSalva = organizacaoRepository.save(organizacao);

        Organizacao organizacaoConsultada = organizacaoRepository.findById(organizacaoSalva.getId()).orElse(null);
        assertNotNull(organizacaoConsultada);
    }

    @Test
    public void testAtualizarOrganizacao() {
        ResponsavelDTO responsavelDTO = new ResponsavelDTO("Maria", "12345678911", "maria@gmail.com", "6799999999");
        OrganizacaoDTO organizacaoDTO = new OrganizacaoDTO(
                null, 
                "98765432109876",
                "Outra Organização",
                "outra@exemplo.com",
                "9876543210", 
                responsavelDTO,
                Status.INDICADO
        );

        Organizacao organizacao = new Organizacao(organizacaoDTO);
        Organizacao organizacaoSalva = organizacaoRepository.save(organizacao);
    
        OrganizacaoDTO updatedOrganizacaoDTO = new OrganizacaoDTO(
                organizacaoSalva.getId(),
                organizacaoSalva.getCnpj(),
                "Nova Organização",
                organizacaoSalva.getEmail(),
                organizacaoSalva.getTelefone(),
                responsavelDTO,
                Status.INDICADO
        );
    
        organizacao.atualizar(updatedOrganizacaoDTO);
        organizacaoRepository.save(organizacao);
    
        Organizacao organizacaoConsultada = organizacaoRepository.findById(organizacaoSalva.getId()).orElse(null);
        assertNotNull(organizacaoConsultada);
        assertEquals("Nova Organização", organizacaoConsultada.getNome());
    }

    @Test
    public void testAtualizarStatusOrganizacao() {
        ResponsavelDTO responsavelDTO = new ResponsavelDTO("Maria", "12345678911", "maria@gmail.com", "6799999999");
        OrganizacaoDTO organizacaoDTO = new OrganizacaoDTO(
                null, 
                "98765432109876",
                "Organização",
                "org@exemplo.com",
                "9876543210", 
                responsavelDTO,
                Status.CADASTRADO
        );

        Organizacao organizacao = new Organizacao(organizacaoDTO);
        Organizacao organizacaoSalva = organizacaoRepository.save(organizacao);

        OrganizacaoDTO updatedOrganizacaoDTO = new OrganizacaoDTO(
                organizacaoSalva.getId(),
                organizacaoSalva.getCnpj(),
                organizacaoSalva.getNome(),
                organizacaoSalva.getEmail(),
                organizacaoSalva.getTelefone(),
                responsavelDTO,
                Status.APROVADO 
        );

        organizacao.atualizarStatus(updatedOrganizacaoDTO);
        organizacaoRepository.save(organizacao);

        Organizacao organizacaoConsultada = organizacaoRepository.findById(organizacaoSalva.getId()).orElse(null);
        assertNotNull(organizacaoConsultada);
        assertEquals(Status.APROVADO, organizacaoConsultada.getStatusCadastro());
    }

    @Test
    public void testSetGetResponsavel() {
        Responsavel responsavel = new Responsavel(null,null,null,null);

        responsavel.setTelefone("6799999999");
        responsavel.setNome("Maria");
        responsavel.setEmail("maria@gmail.com");
        responsavel.setCpf("12345678911");

        assertEquals("6799999999", responsavel.getTelefone());
        assertEquals("Maria", responsavel.getNome());
        assertEquals("maria@gmail.com", responsavel.getEmail());
        assertEquals("12345678911", responsavel.getCpf());

    }

    @Test
    void testSetCreatedAtAndUpdatedAt() {
        Organizacao organizacao = createOrganizacao("123456789", "Empresa ABC", "123456@mail.com", 1);
        Date createdAt = new Date();
        Date updatedAt = new Date();
        organizacao.setCreatedAt(createdAt);
        organizacao.setUpdatedAt(updatedAt);

        assertEquals(createdAt, organizacao.getCreatedAt());
        assertEquals(updatedAt, organizacao.getUpdatedAt());

    }


    private Organizacao createOrganizacao(String cnpj, String nomeEmpresarial, String email, Integer organizacaoId) {
        ResponsavelDTO responsavelDTO = new ResponsavelDTO("Maria", "12345678911", "maria@gmail.com", "6799999999");

        OrganizacaoDTO organizacaoDTO = new OrganizacaoDTO(null, 
                cnpj,
                nomeEmpresarial,
                "email@exemplo.com",
                "1234567890", 
                responsavelDTO,
                Status.CADASTRADO);
        return new Organizacao(organizacaoDTO);
    }

}
