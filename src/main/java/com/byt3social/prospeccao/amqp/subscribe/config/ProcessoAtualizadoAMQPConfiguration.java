package com.byt3social.prospeccao.amqp.subscribe.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessoAtualizadoAMQPConfiguration {
    @Bean
    public Queue processoAtualizadoQueue() {
        return QueueBuilder.nonDurable("processo.atualizado.prospeccao").build();
    }

    @Bean
    public FanoutExchange complianceFanoutExchange() {
        return ExchangeBuilder.fanoutExchange("compliance.ex").build();
    }

    @Bean
    public Binding bindProcessoAtualizadoToCompliance() {
        return BindingBuilder.bind(processoAtualizadoQueue()).to(complianceFanoutExchange());
    }
}
