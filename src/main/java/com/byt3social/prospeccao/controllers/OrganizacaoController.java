package com.byt3social.prospeccao.controllers;

import com.byt3social.prospeccao.dto.IndicacaoDTO;
import com.byt3social.prospeccao.dto.OrganizacaoDTO;
import com.byt3social.prospeccao.models.Organizacao;
import com.byt3social.prospeccao.services.OrganizacaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Organizacao Controller", description = "API para operações relacionadas a organizações")
public class OrganizacaoController {
    @Autowired
    private OrganizacaoService organizacaoService;

    @Operation(summary = "Consultar todas as organizações cadastradas")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso!", content = @Content(schema = @Schema(implementation = Organizacao.class)))
    @ApiResponse(responseCode = "401", description = "Consulta mal-sucedida")
    @GetMapping(value = "/organizacoes")
    public ResponseEntity consultarOrganizacoesCadastradas() {
        List<Organizacao> organizacaos = organizacaoService.consultarOrganizacoesCadastradas();

        return new ResponseEntity(organizacaos, HttpStatus.OK);
    }

    @Operation(summary = "Consultar uma organização específica cadastrada")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso!", content = @Content(schema = @Schema(implementation = Organizacao.class)))
    @ApiResponse(responseCode = "401", description = "Consulta mal-sucedida")
    @GetMapping(value = "/organizacoes/{id}")
    public ResponseEntity consultaOrganizacao(@PathVariable Integer id) {
        Organizacao organizacao = organizacaoService.consultarOrganizacaoCadastrada(id);

        return new ResponseEntity(organizacao, HttpStatus.OK);
    }

    @Operation(summary = "Cadastrar uma organização")
    @ApiResponse(responseCode = "201", description = "Organização cadasatrada com sucesso!")
    @ApiResponse(responseCode = "409", description = "Cadastro mal-sucedido, organização já existe")
    @PostMapping("/organizacoes")
    public ResponseEntity cadastrarOrganizacao(@Valid @RequestBody OrganizacaoDTO dadosOrganizacao) {
        if(!organizacaoService.cadastrarOrganizacao(dadosOrganizacao)) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Operation(summary = "Indicar uma organização")
    @ApiResponse(responseCode = "201", description = "Organização indicada com sucesso!")
    @ApiResponse(responseCode = "401", description = "Indicação mal-sucedida")
    @PostMapping("/indicacoes")
    public ResponseEntity indicarOrganizacao(@RequestBody IndicacaoDTO dadosOrganizacao) {
        organizacaoService.indicarOrganizacao(dadosOrganizacao);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Operation(summary = "Converter uma indicação")
    @ApiResponse(responseCode = "201", description = "Indicação convertida com sucesso!")
    @ApiResponse(responseCode = "404", description = "Organização não encontrada")
    @GetMapping("/indicacoes/{id}")
    public ResponseEntity converterIndicacao(@PathVariable("id") Integer organizacaoID) {
        organizacaoService.converterIndicacao(organizacaoID);

        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "Atualizar uma organização cadastrada")
    @ApiResponse(responseCode = "204", description = "Organização atualizada com sucesso!")
    @ApiResponse(responseCode = "404", description = "Organização não encontrada")
    @PutMapping("/organizacoes/{id}")
    public ResponseEntity atualizarOrganizacaoCadastrada(@PathVariable("id") Integer organizacaoID, @Valid @RequestBody OrganizacaoDTO dadosOrganizacao) {
        organizacaoService.atualizarOrganizacaoCadastrada(organizacaoID, dadosOrganizacao);

        if (organizacaoID == null || organizacaoID<0) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}