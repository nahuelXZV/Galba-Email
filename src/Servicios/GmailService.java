package Servicios;

import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class GmailService {
    private final static String PORT_SMTP = "465";
    private final static String PROTOCOL = "smtp";
    private final static String HOST = "smtp.googlemail.com";
    private final static String USER = "zalazarnahuel43@gmail.com";
    private final static String MAIL = "zalazarnahuel43@gmail.com";
    private final static String MAIL_PASSWORD = "bzljqgesalffzmsx";

    public boolean sendEmail(String mensaje, String receptor) {
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", PROTOCOL);
        properties.setProperty("mail.smtp.host", HOST);
        properties.setProperty("mail.smtp.port", PORT_SMTP);
        // properties.setProperty("mail.smtp.tls.enable", "true");//cuando user tecnoweb
        properties.setProperty("mail.smtp.ssl.enable", "true");// cuando usen Gmail
        properties.setProperty("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER, MAIL_PASSWORD);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(MAIL));

            InternetAddress[] toAddresses = { new InternetAddress(receptor) };

            message.setRecipients(MimeMessage.RecipientType.TO, toAddresses);
            message.setSubject("Respuesta a consulta");

            Multipart multipart = new MimeMultipart("alternative");
            MimeBodyPart htmlPart = new MimeBodyPart();

            htmlPart.setContent(mensaje, "text/html; charset=utf-8");
            multipart.addBodyPart(htmlPart);
            message.setContent(multipart);
            message.saveChanges();

            Transport.send(message);
            System.out.println("EMAIL SEND");
            return true;
        } catch (NoSuchProviderException | AddressException ex) {
            System.out.println("Error 1" + ex);
            return false;
        } catch (MessagingException ex) {
            System.out.println("Error 2" + ex);
            return false;
        }
    }

    public boolean sendEmailError(String subject, String body, String receptor) {
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", PROTOCOL);
        properties.setProperty("mail.smtp.host", HOST);
        properties.setProperty("mail.smtp.port", PORT_SMTP);
        // properties.setProperty("mail.smtp.tls.enable", "true");//cuando user tecnoweb
        properties.setProperty("mail.smtp.ssl.enable", "true");// cuando usen Gmail
        properties.setProperty("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER, MAIL_PASSWORD);
            }/*  */
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(MAIL));

            InternetAddress[] toAddresses = { new InternetAddress(receptor) };

            message.setRecipients(MimeMessage.RecipientType.TO, toAddresses);
            message.setSubject(subject);

            Multipart multipart = new MimeMultipart("alternative");
            MimeBodyPart htmlPart = new MimeBodyPart();

            htmlPart.setContent(body, "text/html; charset=utf-8");
            multipart.addBodyPart(htmlPart);
            message.setContent(multipart);
            message.saveChanges();

            Transport.send(message);
            System.out.println("EMAIL SEND");
            return true;
        } catch (NoSuchProviderException | AddressException ex) {
            System.out.println("Error 1" + ex);
            return false;
        } catch (MessagingException ex) {
            System.out.println("Error 2" + ex);
            return false;
        }
    }

}