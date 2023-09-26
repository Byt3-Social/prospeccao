package com.byt3social.prospeccao.amqp.subscribe.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessoAtualizadoAMQPConfiguration {
    @Bean
    public Queue processoAtualizadoQueue() {
        return QueueBuilder.nonDurable("processo.atualizado").build();
    }

    @Bean
    public DirectExchange processosDirectExchange() {
        return ExchangeBuilder.directExchange("compliance.ex").build();
    }

    @Bean
    public Binding bindProcessoAtualizadoToProcessos() {
        return BindingBuilder.bind(processoAtualizadoQueue()).to(processosDirectExchange()).with("processo.atualizado");
    }
}
