package com.byt3social.prospeccao.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.byt3social.prospeccao.dto.OrganizacaoDTO;
import com.byt3social.prospeccao.dto.ResponsavelDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import com.byt3social.prospeccao.enums.Status;
import com.byt3social.prospeccao.models.Organizacao;
import com.byt3social.prospeccao.services.OrganizacaoService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;


@SpringBootTest
@AutoConfigureMockMvc
class OrganizacaoControllerTest {

    private String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrganizacaoService organizacaoService;
    
    @Test
    @DisplayName("Deve retornar status 200 para GET /organizacoes")
    void getOrganizacoes() throws Exception {
        ResultActions resultActions = mvc.perform(get("/organizacoes")
            .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());
        String contentType = resultActions.andReturn().getResponse().getContentType();
        assertTrue(contentType != null && contentType.contains("application/json"));
    }

   @Test
    @DisplayName("Deve retornar status 200 e dados específicos para GET /organizacoes/{id}")
    void getOrganizacaoById() throws Exception {
        int idOrganizacao = 1;
        
        Organizacao expectedOrganizacao = new Organizacao();
        expectedOrganizacao.setId(idOrganizacao);
        expectedOrganizacao.setCnpj("12345678901234");
        expectedOrganizacao.setNome("Nome da Organização");

        Mockito.when(organizacaoService.consultarOrganizacaoCadastrada(idOrganizacao))
            .thenReturn(expectedOrganizacao);

        ResultActions resultActions = mvc.perform(get("/organizacoes/{id}", idOrganizacao)
            .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(idOrganizacao))
            .andExpect(jsonPath("$.nome").value("Nome da Organização"))
            .andExpect(jsonPath("$.cnpj").value("12345678901234"));
    }

    @Test
    @DisplayName("Deve retornar status 200 para indicações com ID válido")
    void getConverterIndicacao() throws Exception {
        int validId = 1;
    
        ResultActions resultActions = mvc.perform(get("/indicacoes/{id}", validId)
            .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());      
    }

  
    
    @Test
    @DisplayName("Deve retornar status 400, já que os valores são nulos")
    void postOrganizacoes1() throws Exception {
        var response = mvc.perform(post("/organizacoes")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

   /* @Test
    @DisplayName("Deve retornar status 201, já que os valores são válidos")
    void postOrganizacoes2() throws Exception {
        ResponsavelDTO responsavelDTO = new ResponsavelDTO("Marian", "17717717777","mariaz@gmail.com", "6799999999");
        OrganizacaoDTO dadosOrganizacao = new OrganizacaoDTO(
            12345,
            "12345678901234", 
            "Nome da Organização",
            "email@example.com", 
            "11111111111", 
            responsavelDTO, 
            Status.INDICADO 
        );

        var response = mvc.perform(post("/organizacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dadosOrganizacao)))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    } */

    @Test
    @DisplayName("Deve retornar status 400, já que o cnpj é inválido")
    void postOrganizacoes3() throws Exception {

        ResponsavelDTO responsavelDTO = new ResponsavelDTO("Marian", "17717717777","maria@gmail.com", "6799999999");
        OrganizacaoDTO dadosOrganizacao = new OrganizacaoDTO(
            null,
            "123456789012344", 
            "Nome da Organização",
            "email@example.com", 
            "1234567890", 
            responsavelDTO, 
            Status.CADASTRADO 
        );
        var response = mvc.perform(post("/organizacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dadosOrganizacao))) 
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deve retornar status 400, já que o número de telefone é inválido")
    void postOrganizacoes4() throws Exception {

        ResponsavelDTO responsavelDTO = new ResponsavelDTO("Marian", "17717717777","maria@gmail.com", "6799999999");
        OrganizacaoDTO dadosOrganizacao = new OrganizacaoDTO(
            null,
            "12345678901234", 
            "Nome da Organização",
            "email@example.com", 
            "123456789012", 
            responsavelDTO, 
            Status.CADASTRADO 
        );
        var response = mvc.perform(post("/organizacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dadosOrganizacao))) 
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deve retornar status 400, já que os valores são nulos")
    void postIndicacoes1() throws Exception {
        var response = mvc.perform(post("/indicacoes")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deve retornar status 201, já que os valores são válidos")
    void postIndicacoes2() throws Exception {

        ResponsavelDTO responsavelDTO = new ResponsavelDTO("Marian", "17717717777","maria@gmail.com", "6799999999");
        OrganizacaoDTO dadosOrganizacao = new OrganizacaoDTO(
            null,
            "12345678901234", 
            "Nome da Organização",
            "email@example.com", 
            "1234567890", 
            responsavelDTO, 
            Status.CADASTRADO 
        );
        var response = mvc.perform(post("/indicacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dadosOrganizacao))) 
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("Deve retornar status 204 para PUT /organizacoes/{id} com dados válidos")
    void putOrganizacoes1() throws Exception {
        int organizacaoId = 1;

        ResponsavelDTO responsavelDTO = new ResponsavelDTO("Marian", "17717717777","maria@gmail.com", "6799999999");
                OrganizacaoDTO updatedOrganizacao = new OrganizacaoDTO(
                    null,
                    "12345678901234", 
                    "Novo Nome da Organização",
                    "novoemail@example.com", 
                    "2234567890", 
                    responsavelDTO, 
                    Status.APROVADO 
                );       

        Mockito.doNothing().when(organizacaoService).atualizarOrganizacaoCadastrada(organizacaoId, updatedOrganizacao);

        String updatedOrganizacaoJson = asJsonString(updatedOrganizacao);

        ResultActions resultActions = mvc.perform(put("/organizacoes/{id}", organizacaoId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(updatedOrganizacaoJson));

        resultActions.andExpect(status().isNoContent());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -100}) 
    @NullSource 
    @DisplayName("Deve retornar status 404 para PUT /organizacoes/{id} com ID inválido")
    void putOrganizacoesInvalidId(Integer invalidOrganizacaoId) throws Exception {
        ResponsavelDTO responsavelDTO = new ResponsavelDTO("Marian", "17717717777","maria@gmail.com", "6799999999");
        OrganizacaoDTO updatedOrganizacao = new OrganizacaoDTO(
                null,
                "12345678901234", 
                "Novo Nome da Organização",
                "novoemail@example.com", 
                "2234567890", 
                responsavelDTO, 
                Status.APROVADO 
        );

        Mockito.doNothing().when(organizacaoService).atualizarOrganizacaoCadastrada(invalidOrganizacaoId, updatedOrganizacao);

        String updatedOrganizacaoJson = asJsonString(updatedOrganizacao);

        ResultActions resultActions = mvc.perform(put("/organizacoes/{id}", invalidOrganizacaoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedOrganizacaoJson));

        resultActions.andExpect(status().isNotFound());
    }
    
}