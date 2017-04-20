package com.zensar.userapplication.dao;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class ForgotPasswordDAOServiceImpl implements ForgotPasswordDAOService {
    public boolean getPasswordResetLink(String username, String email) {
        String port = "465";
        String host = "smtp.gmail.com";
        final String password = "thegr8kaju@123";
        final String from = "kajalkukreja694@gmail.com";
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.socketFactory.port", port);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.user", from);
        properties.put("mail.password", password);
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("Reset Password Link");
            message.setText("<style> a { color: #337ab7; cursor: pointer; } a:hover { text-decoration: underline; } </style><br /><br />Hi " + username + ",<br /><br /><p>You requested to reset your password. This email contains link to reset it.</p><p>Click <a href='http://localhost:8080/userapp/resetpassword?username=" + username +"' target='_blank'><b>here</b></a> to reset your password.</p><p>Thank you.</p>", "UTF-8", "html");
            Transport.send(message);
            System.out.print("Message sent successfully!");
            return true;
        } catch (SendFailedException e){
            System.err.println("SendFailedException!!" + e.getMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("MessagingException!!" + e.getMessage());
        }
        return false;
    }
    public static void main(String[] args) {
        new ForgotPasswordDAOServiceImpl().getPasswordResetLink("kajalkukreja", "kajalkukreja694@gmail.com");
    }
}