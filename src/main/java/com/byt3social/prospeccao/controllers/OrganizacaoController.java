package com.byt3social.prospeccao.controllers;

import com.byt3social.prospeccao.dto.OrganizacaoDTO;
import com.byt3social.prospeccao.models.Organizacao;
import com.byt3social.prospeccao.services.ProspeccaoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/organizacao")
public class OrganizacaoController {
    @Autowired
    private ProspeccaoService prospeccaoService;

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrarPotencialOrganizacao(@RequestBody @Valid OrganizacaoDTO dados) {
        prospeccaoService.cadastraOrganizacao(dados);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
