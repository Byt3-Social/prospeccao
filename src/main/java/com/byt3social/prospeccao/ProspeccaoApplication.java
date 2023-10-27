package com.byt3social.prospeccao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "API Prospecção",
        version = "1.0",
        description = "API destinada ao cadastro das organizações.",
        contact = @io.swagger.v3.oas.annotations.info.Contact(
            name = "Byt3Social",
            email = "byt3social@gmail.com"
        )
    )
)
public class ProspeccaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProspeccaoApplication.class, args);
	}

}
