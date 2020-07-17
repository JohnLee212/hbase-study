package com.example.hbasestudy.controller;


import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

/**
 * @author liwenxing
 * @Version 1.0
 * @Description ceshi
 * @date 2020/7/13 19:55
 */
public class EmailHelper {
    private String host;
    private String username;
    private String password;
    private String from;

    private String to;
    private String subject;
    private String htmlContent;
    private String attachedFileName;

    public EmailHelper(String host, String username, String password, String from) throws AddressException, MessagingException {
        this.host = host;
        this.username = username;
        this.password = password;
        this.from = from;
    }

    public void send() throws Exception {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", host);

        final String username1 = username;
        final String password1 = password;

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        // 密码验证
                        return new PasswordAuthentication("lwx18848956826", "JEHOOGHIEBVEJKCK");
                    }
                });

        Message message = new MimeMessage(session);


        message.setFrom(new InternetAddress(from));

        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

        message.setSubject(subject);


        Multipart multipart = new MimeMultipart();

        if (htmlContent != null) {
            System.out.println(" has html ");

            BodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlContent, "text/html");
            multipart.addBodyPart(htmlPart);
        }

        if (attachedFileName != null) {
            System.out.println(" has attached file ");

            BodyPart attchmentPart = new MimeBodyPart();
            DataSource source = new FileDataSource(attachedFileName);
            attchmentPart.setDataHandler(new DataHandler(source));
            attchmentPart.setFileName(attachedFileName);
            multipart.addBodyPart(attchmentPart);
        }

        message.setContent(multipart);
        Transport.send(message);

        System.out.println(" Sent ");
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public void setAttachedFileName(String attachedFileName) {
        this.attachedFileName = attachedFileName;
    }

    public static void main(String[] args) {
        String host = "smtp.163.com";// use your smtp server host

        final String username = "lwx18848956826@163.com"; // use your username
        final String password = "li150187@"; // use your password

        String from = "lwx18848956826@163.com"; // use your sender email address

        String to = "2632669616@qq.com";// use your reciever email address
        try {
            EmailHelper emailHelper = new EmailHelper(host, username, password, from);
            emailHelper.setTo(to);
            emailHelper.setSubject("subject ttt test");
            emailHelper.setHtmlContent("<h1> This is html </h1>");
//            emailHelper.setAttachedFileName("/Users/grs/Documents/Java/mavenEmail/test/src/main/resource/attachment3.txt");

            emailHelper.send();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
