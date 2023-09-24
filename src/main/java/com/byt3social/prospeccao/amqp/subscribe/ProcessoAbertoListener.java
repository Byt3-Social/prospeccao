package com.byt3social.prospeccao.amqp.subscribe;

import com.byt3social.prospeccao.dto.OrganizacaoDTO;
import com.byt3social.prospeccao.repositories.OrganizacaoRepository;
import com.byt3social.prospeccao.services.ProspeccaoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProcessoAbertoListener {
    @Autowired
    private ProspeccaoService prospeccaoService;

    @RabbitListener(queues = "processo.aberto")
    public void processoAberto(@Payload OrganizacaoDTO organizacao) {
        System.out.println(organizacao);
        prospeccaoService.atualizaStatus(organizacao);
    }
}
