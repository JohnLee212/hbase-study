package com.example.hbasestudy.mail;

import com.example.hbasestudy.controller.ByteArrayDataSource;
import com.example.hbasestudy.controller.SendMailText_Picture_Enclosure;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.Properties;

/**
 * @author liwenxing
 * @Version 1.0
 * @Description 测试
 * @date 2020/7/13 18:38
 */
public class SendEmail {

    //发件人地址
    public static String senderAddress = "lwx18848956826@163.com";
    //收件人地址
    public static String recipientAddress = "2632669616@qq.com";
    //发件人账户名
    public static String senderAccount = "lwx18848956826";
    //发件人账户密码
    public static String senderPassword = "li150187@";


    public static void send() throws Exception{
        //1、连接邮件服务器的参数配置
        Properties props = new Properties();
        //设置用户的认证方式
        props.setProperty("mail.smtp.auth", "true");
        //设置传输协议
        props.setProperty("mail.transport.protocol", "smtp");
        //设置发件人的SMTP服务器地址
        props.setProperty("mail.smtp.host", "smtp.163.com");
        // 创建验证器
        Authenticator auth = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                // 密码验证
                return new PasswordAuthentication("lwx18848956826", "JEHOOGHIEBVEJKCK");
            }
        };
        //2、创建定义整个应用程序所需的环境信息的 Session 对象
        Session session = Session.getInstance(props,auth);
        //设置调试信息在控制台打印出来
        session.setDebug(true);
        //3、创建邮件的实例对象
        Message msg = getMimeMessage(session);
        //4、根据session对象获取邮件传输对象Transport
        Transport transport = session.getTransport();
        //设置发件人的账户名和密码
        transport.connect(senderAccount, senderPassword);
        //发送邮件，并发送到所有收件人地址，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(msg,msg.getAllRecipients());
        //5、关闭邮件连接
        transport.close();
    }

    private static Message getMimeMessage(Session session) throws Exception {

        MimeMessage message =  new MimeMessage(session);
        LinkedList<Object> attachList = new LinkedList();
        // 新建一个MimeMultipart对象用来存放BodyPart对象(事实上可以存放多个)
        MimeMultipart mm = new MimeMultipart();
        // 新建一个存放信件内容的BodyPart对象
        MimeBodyPart mdp = new MimeBodyPart();
        // 给BodyPart对象设置内容和格式/编码方式
        StringBuffer stringBuffer = SendMailText_Picture_Enclosure.sendMsg("cid:header", "cid:footer");
        mdp.setContent(stringBuffer.toString(), "text/html;charset=UTF-8");
        // 这句很重要，千万不要忘了
        mm.setSubType("related");
        mm.addBodyPart(mdp);
        /*attachList.add(new ByteArrayDataSource(new FileInputStream(new File("src/main/resources/images/header.png")),"application/octet-stream"));
        attachList.add(new ByteArrayDataSource(new FileInputStream(new File("src/main/resources/images/footer.jpg")),"application/octet-stream"));
        // add the attachments
        for( int i=0; i< attachList.size(); i++)
        {
            // 新建一个存放附件的BodyPart
            mdp = new MimeBodyPart();
            DataHandler dh = new DataHandler(new ByteArrayDataSource((byte[])attachList.get(i),"application/octet-stream"));
            mdp.setDataHandler(dh);
            // 加上这句将作为附件发送,否则将作为信件的文本内容
            mdp.setFileName(new Integer(i).toString() + ".jpg");
            mdp.setHeader("Content-ID", "IMG" + new Integer(i).toString());
            // 将含有附件的BodyPart加入到MimeMultipart对象中
            mm.addBodyPart(mdp);
        }*/
        // 5. 创建图片"节点"
        MimeBodyPart image = new MimeBodyPart();
        DataHandler dh = new DataHandler(new FileDataSource("src/main/resources/images/header.png"));
        // 将图片数据添加到"节点"
        image.setDataHandler(dh);
        // 为"节点"设置一个唯一编号（在文本"节点"将引用该ID）
        image.setContentID("header");
        mm.addBodyPart(image);

        // 5. 创建图片"节点"
        MimeBodyPart image2 = new MimeBodyPart();
        // 读取本地文件
//        classpath:excleTemplate/test.xlsx
        DataHandler dh3 = new DataHandler(new FileDataSource("src/main/resources/images/footer.jpg"));
        // 将图片数据添加到"节点"
        image2.setDataHandler(dh3);
        // 为"节点"设置一个唯一编号（在文本"节点"将引用该ID）
        image2.setContentID("footer");
        mm.addBodyPart(image2);
        // 把mm作为消息对象的内容
        message.setContent(mm);
        message.saveChanges();
        return message;
    }


    public static void main(String[] args) throws Exception{
         send();

    }
}
