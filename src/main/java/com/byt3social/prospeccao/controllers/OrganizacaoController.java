package com.byt3social.prospeccao.controllers;

import com.byt3social.prospeccao.dto.IndicacaoDTO;
import com.byt3social.prospeccao.dto.OrganizacaoDTO;
import com.byt3social.prospeccao.models.Organizacao;
import com.byt3social.prospeccao.services.OrganizacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrganizacaoController {
    @Autowired
    private OrganizacaoService organizacaoService;

    @GetMapping(value = "/organizacoes")
    public ResponseEntity consultarOrganizacoesCadastradas() {
        List<Organizacao> organizacaos = organizacaoService.consultarOrganizacoesCadastradas();

        return new ResponseEntity(organizacaos, HttpStatus.OK);
    }

    @GetMapping(value = "/organizacoes/{id}")
    public ResponseEntity consultaOrganizacao(@PathVariable Integer id) {
        Organizacao organizacao = organizacaoService.consultarOrganizacaoCadastrada(id);

        return new ResponseEntity(organizacao, HttpStatus.OK);
    }

    @PostMapping("/organizacoes")
    public ResponseEntity cadastrarOrganizacao(@Valid @RequestBody OrganizacaoDTO dadosOrganizacao) {
        if(!organizacaoService.cadastrarOrganizacao(dadosOrganizacao)) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/indicacoes")
    public ResponseEntity indicarOrganizacao(@RequestBody IndicacaoDTO dadosOrganizacao) {
        organizacaoService.indicarOrganizacao(dadosOrganizacao);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/indicacoes/{id}")
    public ResponseEntity converterIndicacao(@PathVariable("id") Integer organizacaoID) {
        organizacaoService.converterIndicacao(organizacaoID);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/organizacoes/{id}")
    public ResponseEntity atualizarOrganizacaoCadastrada(@PathVariable("id") Integer organizacaoID, @Valid @RequestBody OrganizacaoDTO dadosOrganizacao) {
        organizacaoService.atualizarOrganizacaoCadastrada(organizacaoID, dadosOrganizacao);

        if (organizacaoID == null || organizacaoID<0) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
