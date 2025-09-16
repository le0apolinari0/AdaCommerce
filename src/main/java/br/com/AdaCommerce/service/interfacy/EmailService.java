package br.com.AdaCommerce.service.interfacy;

import java.util.Map;

public interface EmailService {
    void enviarEmail(String destinatario, String assunto, String mensagem);
    void enviarEmailTemplate(String destinatario, String assunto, String templateName, Map<String, Object> variaveis);
}

