package com.byt3social.prospeccao.amqp.subscribe;

import com.byt3social.prospeccao.dto.OrganizacaoDTO;
import com.byt3social.prospeccao.services.OrganizacaoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ProcessoAtualizadoListener {
    @Autowired
    private OrganizacaoService organizacaoService;

    @RabbitListener(queues = "processo.atualizado.prospeccao")
    public void processoAtualizado(@Payload OrganizacaoDTO organizacaoDTO) {
        organizacaoService.atualizarStatusOrganizacao(organizacaoDTO);
    }
}
