package com.byt3social.prospeccao.amqp.subscribe.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessoAbertoAMQPConfiguration {
    @Bean
    public Queue processoAbertoQueue() {
        return QueueBuilder.nonDurable("processo.aberto").build();
    }

    @Bean
    public DirectExchange processosDirectExchange() {
        return ExchangeBuilder.directExchange("processos.ex").build();
    }

    @Bean
    public Binding bindProcessoAbertoToProcessos() {
        return BindingBuilder.bind(processoAbertoQueue()).to(processosDirectExchange()).with("processo.aberto");
    }
}
