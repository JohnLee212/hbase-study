package com.example.hbasestudy.controller;


import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
/**
 * @author liwenxing
 * @Version 1.0
 * @Description ces
 * @date 2020/7/13 16:53
 */
@Service
public class SendMailText_Picture_Enclosure {

    //发件人地址
    public static String senderAddress = "lwx18848956826@163.com";
    //收件人地址
    public static String recipientAddress = "2632669616@qq.com";
    //发件人账户名
    public static String senderAccount = "lwx18848956826";
    //发件人账户密码
    public static String senderPassword = "li150187@";


    public void  send() throws  Exception{
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


    public static void main(String[] args) throws Exception {
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

    /**
     * 获得创建一封邮件的实例对象
     * @param session
     * @return
     * @throws MessagingException
     * @throws AddressException
     */
    public static MimeMessage getMimeMessage(Session session) throws Exception{
        //1.创建一封邮件的实例对象
        MimeMessage msg = new MimeMessage(session);
        //2.设置发件人地址
        msg.setFrom(new InternetAddress(senderAddress));
        /**
         * 3.设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
        msg.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(recipientAddress));
        //4.设置邮件主题
        msg.setSubject("邮件主题(包含图片和附件)","UTF-8");

        //下面是设置邮件正文
        msg.setContent("简单的纯文本邮件！", "text/html;charset=UTF-8");

        // 5. 创建图片"节点"
        MimeBodyPart image = new MimeBodyPart();
        // 读取本地文件
//        classpath:excleTemplate/test.xlsx
        DataHandler dh = new DataHandler(new FileDataSource("src/main/resources/images/header.png"));
        // 将图片数据添加到"节点"
        image.setDataHandler(dh);
        // 为"节点"设置一个唯一编号（在文本"节点"将引用该ID）
        image.setContentID("header");

        // 5. 创建图片"节点"
        MimeBodyPart image2 = new MimeBodyPart();
        // 读取本地文件
//        classpath:excleTemplate/test.xlsx
        DataHandler dh3 = new DataHandler(new FileDataSource("src/main/resources/images/footer.jpg"));
        // 将图片数据添加到"节点"
        image2.setDataHandler(dh3);
        // 为"节点"设置一个唯一编号（在文本"节点"将引用该ID）
        image2.setContentID("footer");


        // 读取本地文件
      /*  DataHandler dh3= new DataHandler(new FileDataSource("src/main/resources/images/footer.jpg"));
        image.setDataHandler(dh3);
        image.setContentID("footer");*/


        // 6. 创建文本"节点"
        MimeBodyPart text = new MimeBodyPart();
        // 这里添加图片的方式是将整个图片包含到邮件内容中, 实际上也可以以 http 链接的形式添加网络图片
//        text.setContent("这是一张图片<br/><a href='http://www.cnblogs.com/ysocean/p/7666061.html'><img src='cid:mailTestPic'/></a>", "text/html;charset=UTF-8");
        text.setContent(sendMsg("cid:header","cid:footer").toString(),"text/html;charset=UTF-8");
        // 7. （文本+图片）设置 文本 和 图片"节点"的关系（将 文本 和 图片"节点"合成一个混合"节点"）
        MimeMultipart mm_text_image = new MimeMultipart();
        mm_text_image.addBodyPart(text);
        mm_text_image.addBodyPart(image);
        mm_text_image.addBodyPart(image2);
        mm_text_image.setSubType("related");    // 关联关系

        // 8. 将 文本+图片 的混合"节点"封装成一个普通"节点"
        // 最终添加到邮件的 Content 是由多个 BodyPart 组成的 Multipart, 所以我们需要的是 BodyPart,
        // 上面的 mailTestPic 并非 BodyPart, 所有要把 mm_text_image 封装成一个 BodyPart
        MimeBodyPart text_image = new MimeBodyPart();
        text_image.setContent(mm_text_image);

        // 9. 创建附件"节点"
        MimeBodyPart attachment = new MimeBodyPart();
        // 读取本地文件
        DataHandler dh2 = new DataHandler(new FileDataSource("src\\mailTestDoc.docx"));
        // 将附件数据添加到"节点"
        attachment.setDataHandler(dh2);
        // 设置附件的文件名（需要编码）
        attachment.setFileName(MimeUtility.encodeText(dh2.getName()));

        // 10. 设置（文本+图片）和 附件 的关系（合成一个大的混合"节点" / Multipart ）
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text_image);
        mm.addBodyPart(attachment);     // 如果有多个附件，可以创建多个多次添加
        mm.setSubType("mixed");         // 混合关系

        // 11. 设置整个邮件的关系（将最终的混合"节点"作为邮件的内容添加到邮件对象）
        msg.setContent(mm);
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());

        return msg;
    }

    public static StringBuffer sendMsg(String header,String footer){

        StringBuffer stringBuffer = new StringBuffer();

        String msg = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "    <title>Document</title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <div class=\"content\">\n" +
                "      <img src="+header+" class=\"content_header\" alt=\"\" />\n" +
                "      <div class=\"content_body_box\">\n" +
                "        <div class=\"content_body\">\n" +
                "          <p class=\"p2\">尊敬的XXX，</p>\n" +
                "          <p class=\"p2\" style=\"text-indent: 2em;\">您有XX条待办待您审批，请登陆Portal门户收件箱尽快完成审批。</p>\n" +
                "          <table border=\"1\" cellspacing=\"0\" cellpadding=\"10\">\n" +
                "            <tr class=\"table_tr\">\n" +
                "              <td>流程类型</td>\n" +
                "              <td>名称详情</td>\n" +
                "              <td>发起人</td>\n" +
                "              <td>创建时间</td>\n" +
                "              <td>流程ID</td>\n" +
                "            </tr>\n" +
                "            <tr class=\"table_trr\">\n" +
                "              <td>原收件箱清单-流程名称字段</td>\n" +
                "              <td>新增字段，取值流程单据中的客户名称。营销计划/营销活动审批单据取值营销计划/营销活动的名称描述</td>\n" +
                "              <td>取值流程发起人，原收件箱清单-发起人字段</td>\n" +
                "              <td>BPM流程单创建时间，原收件箱清单-创建时间字段</td>\n" +
                "              <td>BPM审批流程单号，原收件箱-流程ID字段</td>\n" +
                "            </tr>\n" +
                "          </table>\n" +
                "\n" +
                "          <p class=\"p2\" style=\"text-indent: 2em;\">本次邮件提醒由SAP于YYYY-MM-DD HH:MM:SS自动发送，请勿回复。如有问题，请联系IT服务台4001508585。</p>\n" +
                "          <p class=\"p2\" style=\"text-indent: 2em;\">谢谢！</p>\n" +
                "        </div>\n" +
                "      </div>\n" +
                "\n" +
                "      <img src="+footer+" class=\"content_footer\" alt=\"\" />\n" +
                "    </div>\n" +
                "  </body>\n" +
                "  <style>\n" +
                "    * {\n" +
                "      margin: 0;\n" +
                "      padding: 0;\n" +
                "    }\n" +
                "    .content_header {\n" +
                "      display: block;\n" +
                "      width: 100%;\n" +
                "    }\n" +
                "    .content_body_box {\n" +
                "      overflow: hidden;\n" +
                "      background: #9ed8f6;\n" +
                "    }\n" +
                "    .content_body {\n" +
                "      min-height: 500px;\n" +
                "      overflow: hidden;\n" +
                "      background: #eaf3fa;\n" +
                "      padding: 50px 20px 30px 20px;\n" +
                "      margin: 0 10px 0 10px;\n" +
                "    }\n" +
                "    .content_footer {\n" +
                "      display: block;\n" +
                "      width: 100%;\n" +
                "    }\n" +
                "    .p2 {\n" +
                "      font-size: 14px;\n" +
                "      line-height: 40px;\n" +
                "    }\n" +
                "    table {\n" +
                "      margin-left: 2em;\n" +
                "      margin-top: 20px;\n" +
                "      margin-bottom: 20px;\n" +
                "    }\n" +
                "    .table_tr {\n" +
                "      text-align: center;\n" +
                "      font-size: 14px;\n" +
                "    }\n" +
                "    .table_trr {\n" +
                "      font-size: 14px;\n" +
                "    }\n" +
                "    .table_trr td {\n" +
                "      padding: 5px;\n" +
                "    }\n" +
                "  </style>\n" +
                "</html>\n";
        stringBuffer.append(msg);
        return stringBuffer;
    }

}
