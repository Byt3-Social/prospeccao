package com.byt3social.prospeccao.amqp.publish;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrganizacaoCadastradaAMQPConfiguration {
    @Bean
    public FanoutExchange prospeccaoFanoutExchange() {
        return new FanoutExchange("prospeccao.ex");
    }
}
