package com.byt3social.prospeccao.controllers;

import com.byt3social.prospeccao.dto.IndicacaoDTO;
import com.byt3social.prospeccao.dto.OrganizacaoDTO;
import com.byt3social.prospeccao.models.Organizacao;
import com.byt3social.prospeccao.services.OrganizacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/organizacoes")
@Tag(name = "Organizacao Controller", description = "API para operações relacionadas a organizações")
public class OrganizacaoController {
    @Autowired
    private OrganizacaoService organizacaoService;

    @Operation(summary = "Consultar organizações cadastradas")
    @GetMapping
    public ResponseEntity<List<Organizacao>> consultarOrganizacoesCadastradas() {
        List<Organizacao> organizacoes = organizacaoService.consultarOrganizacoesCadastradas();

        return new ResponseEntity<>(organizacoes, HttpStatus.OK);
    }

    @Operation(summary = "Consultar uma organização por ID")
    @ApiResponse(responseCode = "200", description = "Organização encontrada", content = @Content(schema = @Schema(implementation = Organizacao.class)))
    @ApiResponse(responseCode = "404", description = "Organização não encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<Organizacao> consultaOrganizacao(@PathVariable Integer id) {
        Organizacao organizacao = organizacaoService.consultarOrganizacaoCadastrada(id);

        if (organizacao == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(organizacao, HttpStatus.OK);
    }

    @Operation(summary = "Cadastrar uma organização")
    @ApiResponse(responseCode = "201", description = "Organização cadastrada com sucesso")
    @ApiResponse(responseCode = "409", description = "Conflito - Organização já cadastrada")
    @PostMapping
    public ResponseEntity<Void> cadastrarOrganizacao(@Valid @RequestBody OrganizacaoDTO dadosOrganizacao) {
        if (!organizacaoService.cadastrarOrganizacao(dadosOrganizacao)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Indicar uma organização")
    @ApiResponse(responseCode = "201", description = "Indicação cadastrada com sucesso")
    @PostMapping("/indicacoes")
    public ResponseEntity<Void> indicarOrganizacao(@RequestBody IndicacaoDTO dadosOrganizacao) {
        organizacaoService.indicarOrganizacao(dadosOrganizacao);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Converter uma indicação")
    @ApiResponse(responseCode = "200", description = "Indicação convertida com sucesso")
    @GetMapping("/indicacoes/{id}")
    public ResponseEntity<Void> converterIndicacao(@PathVariable("id") Integer organizacaoID) {
        organizacaoService.converterIndicacao(organizacaoID);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Atualizar uma organização cadastrada")
    @ApiResponse(responseCode = "204", description = "Organização atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Organização não encontrada")
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarOrganizacaoCadastrada(@PathVariable("id") Integer organizacaoID, @Valid @RequestBody OrganizacaoDTO dadosOrganizacao) {
        if (organizacaoID == null || organizacaoID < 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        organizacaoService.atualizarOrganizacaoCadastrada(organizacaoID, dadosOrganizacao);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
