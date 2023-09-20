package com.byt3social.prospeccao.controllers;

import com.byt3social.prospeccao.dto.OrganizacaoDTO;
import com.byt3social.prospeccao.models.Organizacao;
import com.byt3social.prospeccao.services.ProspeccaoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class OrganizacaoController {
    @Autowired
    private ProspeccaoService prospeccaoService;

    @PostMapping("/organizacao/cadastrar")
    public ResponseEntity cadastrarPotencialOrganizacao(@RequestBody @Valid OrganizacaoDTO dados) {
        prospeccaoService.cadastraOrganizacao(dados);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(value = "/organizacoes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity consultaOrganizacoes() {
        List<Organizacao> organizacaos = prospeccaoService.consultaPotenciaisOrganizacoes();

        return new ResponseEntity(organizacaos, HttpStatus.OK);
    }

    @GetMapping(value = "/organizacao/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity consultaOrganizacao(@PathVariable Integer id) {
        Organizacao organizacao = prospeccaoService.consultaPotencialOrganizacao(id);

        return new ResponseEntity(organizacao, HttpStatus.OK);
    }

    @PutMapping("organizacao")
    public ResponseEntity atualizaOrganizacao(@RequestBody OrganizacaoDTO dados) {
        prospeccaoService.atualizarOrganizacao(dados);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
