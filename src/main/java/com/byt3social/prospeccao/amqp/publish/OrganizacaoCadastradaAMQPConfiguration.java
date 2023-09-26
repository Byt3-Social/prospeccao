package com.byt3social.prospeccao.amqp.publish;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrganizacaoCadastradaAMQPConfiguration {
    @Bean
    public DirectExchange cadastrosDirectExchange() {
        return new DirectExchange("prospeccao.ex");
    }
}
