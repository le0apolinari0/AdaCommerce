package br.com.AdaCommerce.service.impl;


import br.com.AdaCommerce.service.interfacy.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.thymeleaf.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void enviarEmail(
            String destinatario,
            String assunto,
            String mensagem
    ) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(destinatario);
        email.setSubject(assunto);
        email.setText(mensagem);
        email.setFrom("noreply@adacommerce.com.br");

        mailSender.send(email);
    }

    @Override
    public void enviarEmailTemplate(
            String destinatario,
            String assunto,
            String templateName,
            Map<String, Object> variaveis
    ) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            Context context = new Context();
            context.setVariables(variaveis);

            String htmlContent = templateEngine.process(templateName, context);

            helper.setTo(destinatario);
            helper.setSubject(assunto);
            helper.setText(htmlContent, true);
            helper.setFrom("noreply@adacommerce.com.br");

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail", e);
        }
    }
}
