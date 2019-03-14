package com.alpha.component;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EmailSender {
    public static void sendEmail(String subject, String text) throws MessagingException, InterruptedException {

        ClassLoader classLoader = EmailSender.class.getClassLoader();

        InputStream resource = classLoader.getResourceAsStream("config.properties");
        Properties properties = new Properties();
        try {
            properties.load(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                String user = properties.getProperty("mail.user");
                String password = properties.getProperty("mail.password");
                return new PasswordAuthentication(user, password);
            }
        });
        MimeMessage message = new MimeMessage(session);
        message.setFrom(properties.getProperty("mail.from"));
        message.addRecipients(MimeMessage.RecipientType.CC, InternetAddress.parse(properties.getProperty("mail.from")));
        message.setRecipients(Message.RecipientType.TO, properties.getProperty("mail.to"));
        message.setSubject(subject);
        message.setText(text);
        Transport transport = session.getTransport();
        transport.connect();
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}
