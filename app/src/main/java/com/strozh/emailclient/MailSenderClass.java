package com.strozh.emailclient;

import android.util.Log;

import java.security.Security;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by Woods on 29.05.2016.
 */
public class MailSenderClass extends Authenticator {

    private String user;
    private String password;
    private Session session;
    private Multipart multipart;

    static {
        Security.addProvider(new JSSEProvider());
    }

    public MailSenderClass(String user, String password, String host, String port) {
        this.user = user;
        this.password = password;

        multipart = new MimeMultipart();

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", host.substring(0, 4));
        props.setProperty("mail.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.socketFactory.port", port);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");

        session = Session.getDefaultInstance(props, this);
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password);
    }

    public synchronized void sendMail(String subject, String body, String sender, String recipients, String filename) throws Exception {
        try {
            MimeMessage message = new MimeMessage(session);

            // Кто
            message.setSender(new InternetAddress(sender));
            // О чем
            message.setSubject(subject);
            // Кому
            if (recipients.indexOf(',') > 0)
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(recipients));
            else
                message.setRecipient(Message.RecipientType.TO,
                        new InternetAddress(recipients));

            // Хочет сказать
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);
            multipart.addBodyPart(messageBodyPart);

            /*// И что показать
            if (!filename.equalsIgnoreCase("")) {
                BodyPart attachBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(filename);
                attachBodyPart.setDataHandler(new DataHandler(source));
                attachBodyPart.setFileName(filename);

                multipart.addBodyPart(attachBodyPart);
            }*/

            message.setContent(multipart);

            Transport.send(message);
        } catch (Exception e) {
            Log.e("sendMail", "Error sending email in function sendMail! ", e);
        }
    }
}
