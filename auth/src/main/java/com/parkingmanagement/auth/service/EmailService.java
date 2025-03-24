package com.parkingmanagement.auth.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendResetPasswordEmail(String to, String userName, String resetLink) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        String htmlMsg = "<body style=\"font-family: Arial, sans-serif; background-color: #f7f7f7; padding: 20px;\">"
                + "<div style=\"max-width: 600px; margin: auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px; background-color: #fff;\">"
                + "<h2 style=\"color: #333; text-align: center;\">Redefinição de senha</h2>"
                + "<p style=\"font-size: 16px; color: #555;\">Olá, " + userName + ".</p>"
                + "<p style=\"font-size: 16px; color: #555;\">Recebemos uma solicitação para redefinir sua senha. Clique no botão abaixo para redefinir sua senha:</p>"
                + "<div style=\"text-align: center;\"><a href=\"" + resetLink + "\" style=\"display: inline-block; padding: 10px 20px; margin: 20px 0; font-size: 16px; color: #fff; background-color: #007bff; text-decoration: none; border-radius: 5px;\">Redefinir senha</a></div>"
                + "<p style=\"font-size: 16px; color: #555;\">Se você não solicitou a redefinição de senha, por favor ignore este e-mail.</p>"
                + "<p style=\"font-size: 16px; color: #555;\">Atenciosamente,<br/>Equipe do Gerenciador de Estacionamento.</p>"
                + "</div>"
                + "</body>";

        try {
            helper.setTo(to);
            helper.setSubject("Redefinição de senha");
            helper.setText(htmlMsg, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail de redefinição de senha", e);
        }
    }

    public void sendPasswordResetConfirmationEmail(String to, String userName) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        String parkingManagementLink = "http://localhost:4200/";

        String htmlMsg = "<body style=\"font-family: Arial, sans-serif; background-color: #f7f7f7; padding: 20px;\">"
                + "<div style=\"max-width: 600px; margin: auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px; background-color: #fff;\">"
                + "<h2 style=\"color: #333; text-align: center;\">Senha atualizada</h2>"
                + "<p style=\"font-size: 16px; color: #555;\">Olá, " + userName + ".</p>"
                + "<p style=\"font-size: 16px; color: #555;\">Redefinimos sua senha conforme seu pedido. Para ver ou mudar os dados da sua conta, visite o <a href=\"" + parkingManagementLink + "\">Gerenciador de Estacionamento</a>.</p>"
                + "<p style=\"font-size: 16px; color: #555;\">Se você não solicitou a redefinição da sua senha, entre em contato conosco pelo e-mail de suporte: parkingmanagement.leonardodev@gmail.com</p>"
                + "<p style=\"font-size: 16px; color: #555;\">Atenciosamente,<br/>Equipe do Gerenciador de Estacionamento.</p>"
                + "</div>"
                + "</body>";

        try {
            helper.setTo(to);
            helper.setSubject("Sua senha foi atualizada");
            helper.setText(htmlMsg, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail de confirmação de redefinição de senha", e);
        }
    }
}