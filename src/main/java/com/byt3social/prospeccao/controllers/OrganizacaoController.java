package com.byt3social.prospeccao.controllers;

import com.byt3social.prospeccao.dto.OrganizacaoDTO;
import com.byt3social.prospeccao.models.Organizacao;
import com.byt3social.prospeccao.services.OrganizacaoService;
import com.byt3social.prospeccao.validation.OnCreateValidation;
import com.byt3social.prospeccao.validation.OnUpdateValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrganizacaoController {
    @Autowired
    private OrganizacaoService organizacaoService;

    @GetMapping(value = "/organizacoes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity consultarOrganizacoesCadastradas() {
        List<Organizacao> organizacaos = organizacaoService.consultarOrganizacoesCadastradas();

        return new ResponseEntity(organizacaos, HttpStatus.OK);
    }

    @GetMapping(value = "/organizacoes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity consultaOrganizacao(@PathVariable Integer id) {
        Organizacao organizacao = organizacaoService.consultarOrganizacaoCadastrada(id);

        return new ResponseEntity(organizacao, HttpStatus.OK);
    }

    @PostMapping("/organizacoes")
    public ResponseEntity cadastrarOrganizacao(@Validated(OnCreateValidation.class) @RequestBody OrganizacaoDTO dadosOrganizacao) {
        organizacaoService.cadastrarOrganizacao(dadosOrganizacao);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/organizacoes/{id}")
    public ResponseEntity atualizarOrganizacaoCadastrada(@PathVariable("id") Integer organizacaoID, @Validated(OnUpdateValidation.class) @RequestBody OrganizacaoDTO dadosOrganizacao) {
        organizacaoService.atualizarOrganizacaoCadastrada(organizacaoID, dadosOrganizacao);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
