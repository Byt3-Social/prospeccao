package com.byt3social.prospeccao.services;

import com.byt3social.prospeccao.exceptions.FailedToDeliverEmailException;
import com.byt3social.prospeccao.models.Indicacao;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("classpath:templates/email/indicacao.html")
    private Resource resource;

    public void notificarIndicacao(Indicacao indicacao, String linkFormulario) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            message.setFrom(new InternetAddress("byt3social@gmail.com"));
            message.setRecipients(MimeMessage.RecipientType.TO, indicacao.getResponsavel().getEmail());
            message.setSubject("B3 Social | Indicação");

            InputStream inputStream = resource.getInputStream();
            String htmlTemplate = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            htmlTemplate = htmlTemplate.replace("${indicacao_url}", linkFormulario);

            message.setContent(htmlTemplate, "text/html; charset=utf-8");

            mailSender.send(message);
        } catch (Exception e) {
            throw new FailedToDeliverEmailException();
        }
    }
}
