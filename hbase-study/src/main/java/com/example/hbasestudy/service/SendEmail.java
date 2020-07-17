package com.example.hbasestudy.service;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.example.hbasestudy.controller.SendMailText_Picture_Enclosure;
import org.junit.Test;
/**
 * @author liwenxing
 * @Version 1.0
 * @Description ceshi
 * @date 2020/7/13 20:07
 */
public class SendEmail {
    // 邮件发送协议
    private final static String PROTOCOL = "smtp";

    // SMTP邮件服务器
    private final static String HOST = "smtp.163.com";

    // SMTP邮件服务器默认端口
    private final static String PORT = "25";

    // 是否要求身份认证
    private final static String IS_AUTH = "true";

    // 是否启用调试模式（启用调试模式可打印客户端与服务器交互过程时一问一答的响应消息）
    private final static String IS_ENABLED_DEBUG_MOD = "true";

    // 发件人
    private static String from = "lwx18848956826@163.com";
    private static String verification_code = "JEHOOGHIEBVEJKCK";

    // 收件人
    private static String to = "2632669616@qq.com";

    // 初始化连接邮件服务器的会话信息
    private static Properties props = null;

    static {
        props = new Properties();
        props.setProperty("mail.transport.protocol", PROTOCOL);
        props.setProperty("mail.smtp.host", HOST);
        props.setProperty("mail.smtp.port", PORT);
        props.setProperty("mail.smtp.auth", IS_AUTH);
        props.setProperty("mail.debug",IS_ENABLED_DEBUG_MOD);
    }

    /**
     * 发送简单的文本邮件
     */
    @Test
    public void sendTextEmail() throws Exception {
        // 创建Session实例对象
        Session session = Session.getDefaultInstance(props);

        // 创建MimeMessage实例对象
        MimeMessage message = new MimeMessage(session);
        // 设置发件人
        message.setFrom(new InternetAddress(from));
        // 设置邮件主题
        message.setSubject("使用javamail发送简单文本邮件");
        // 设置收件人
        message.setRecipient(RecipientType.TO, new InternetAddress(to));
        // 设置发送时间
        message.setSentDate(new Date());
        // 设置纯文本内容为邮件正文
        message.setText("使用POP3协议发送文本邮件测试!!!");
        // 保存并生成最终的邮件内容
        message.saveChanges();

        // 获得Transport实例对象
        Transport transport = session.getTransport();
        // 打开连接
        transport.connect("chenxingxing745", "cx*******9");
        // 将message对象传递给transport对象，将邮件发送出去
        transport.sendMessage(message, message.getAllRecipients());
        // 关闭连接
        transport.close();
    }

    /**
     * 发送简单的html邮件
     */
    @Test
    public void sendHtmlEmail() throws Exception {
        // 创建Session实例对象
        Session session = Session.getInstance(props, new MyAuthenticator(from,verification_code));

        // 创建MimeMessage实例对象
        MimeMessage message = new MimeMessage(session);
        // 设置邮件主题
        message.setSubject("html邮件主题");
        // 设置发送人
        message.setFrom(new InternetAddress(from));
        // 设置发送时间
        message.setSentDate(new Date());
        // 设置收件人
        message.setRecipients(RecipientType.TO, InternetAddress.parse(to));
        // 设置html内容为邮件正文，指定MIME类型为text/html类型，并指定字符编码为gbk
        message.setContent("<span style='color:red;'>html邮件测试...</span>","text/html;charset=gbk");

        // 保存并生成最终的邮件内容
        message.saveChanges();

        // 发送邮件
        Transport.send(message);
    }

    /**
     * 发送带内嵌图片的HTML邮件
     */
    @Test
    public void sendHtmlWithInnerImageEmail() throws MessagingException {
        // 创建Session实例对象
        Session session = Session.getDefaultInstance(props, new MyAuthenticator(from,verification_code));

        // 创建邮件内容
        MimeMessage message = new MimeMessage(session);
        // 邮件主题,并指定编码格式
        message.setSubject("带内嵌图片的HTML邮件", "utf-8");
        // 发件人
        message.setFrom(new InternetAddress(from));
        // 收件人
        message.setRecipients(RecipientType.TO, InternetAddress.parse(to));
        message.setSentDate(new Date());

        // 创建一个MIME子类型为“related”的MimeMultipart对象
        MimeMultipart mp = new MimeMultipart("related");
        // 创建一个表示正文的MimeBodyPart对象，并将它加入到前面创建的MimeMultipart对象中
        MimeBodyPart htmlPart = new MimeBodyPart();
        mp.addBodyPart(htmlPart);
        // 将MimeMultipart对象设置为整个邮件的内容
        message.setContent(mp);

        // 创建一个表示图片资源的MimeBodyPart对象，将将它加入到前面创建的MimeMultipart对象中
        MimeBodyPart imagePart = new MimeBodyPart();
        MimeBodyPart imagePart1 = new MimeBodyPart();
        mp.addBodyPart(imagePart);
        mp.addBodyPart(imagePart1);

        // 设置内嵌图片邮件体
        DataSource ds = new FileDataSource(new File("src/main/resources/images/header.png"));
        DataHandler dh = new DataHandler(ds);
        imagePart.setDataHandler(dh);
        imagePart.setContentID("psb.jpg");  // 设置内容编号,用于其它邮件体引用

        DataSource ds1 = new FileDataSource(new File("src/main/resources/images/footer.jpg"));
        DataHandler dh1 = new DataHandler(ds1);
        imagePart1.setDataHandler(dh1);
        imagePart1.setContentID("loading.gif");  // 设置内容编号,用于其它邮件体引用


        // 创建一个MIME子类型为"alternative"的MimeMultipart对象，并作为前面创建的htmlPart对象的邮件内容
        MimeMultipart htmlMultipart = new MimeMultipart("alternative");
        // 创建一个表示html正文的MimeBodyPart对象
        MimeBodyPart htmlBodypart = new MimeBodyPart();
        // 其中cid=androidlogo.gif是引用邮件内部的图片，即imagePart.setContentID("androidlogo.gif");方法所保存的图片
//        StringBuffer stringBuffer = SendMailText_Picture_Enclosure.sendMsg("cid:psb.jpg", "cid:loading.gif");
//        String str = "<span style='color:red;'>这是带内嵌图片的HTML邮件哦！！！<img src=\"cid:psb.jpg\" /></span>多张图片<img src=\"cid:psb.jpg\" />侧认<img src=\"cid:loading.gif\" />识\"";
        String str2 = "<img src=\"cid:psb.jpg\" style=\"display: block;width: 100%\" />";
        String str3 = "<img src=\"cid:loading.gif\" style=\"display: block;width: 100%\" />";
//        htmlBodypart.setContent(str2,"text/html;charset=utf-8");
        htmlBodypart.setContent(getMsg(str2,str3),"text/html;charset=utf-8");
        htmlMultipart.addBodyPart(htmlBodypart);
        htmlPart.setContent(htmlMultipart);

        // 保存并生成最终的邮件内容
        message.saveChanges();

        // 发送邮件
        Transport.send(message);
    }


    /**
     * 发送带内嵌图片、附件、多收件人(显示邮箱姓名)、邮件优先级、阅读回执的完整的HTML邮件
     */
    @Test
    public  void sendMultipleEmail() throws Exception {
        String charset = "utf-8";   // 指定中文编码格式
        // 创建Session实例对象
        Session session = Session.getInstance(props,new MyAuthenticator(from,verification_code));

        // 创建MimeMessage实例对象
        MimeMessage message = new MimeMessage(session);
        // 设置主题
        message.setSubject("使用JavaMail发送混合组合类型的邮件测试");
        // 设置发送人
        message.setFrom(new InternetAddress(from,"163测试邮箱",charset));
        // 设置收件人
        message.setRecipients(RecipientType.TO,
                new Address[] {
                        // 参数1：邮箱地址，参数2：姓名（在客户端收件只显示姓名，而不显示邮件地址），参数3：姓名中文字符串编码
                        new InternetAddress("517697206@qq.com", "张三_qq", charset),
                        new InternetAddress("chenjiaren745@163.com", "李四_163", charset),
                }
        );
        // 设置抄送
        //message.setRecipient(RecipientType.CC, new InternetAddress("xyang0917@gmail.com","王五_gmail",charset));
        // 设置密送
        //message.setRecipient(RecipientType.BCC, new InternetAddress("xyang0917@qq.com", "赵六_QQ", charset));
        // 设置发送时间
        message.setSentDate(new Date());
        // 设置回复人(收件人回复此邮件时,默认收件人)
        message.setReplyTo(InternetAddress.parse("\"" + MimeUtility.encodeText("田七") + "\" <chenxingxing745@163.com>"));
        // 设置优先级(1:紧急   3:普通    5:低)
        message.setHeader("X-Priority", "1");
        // 要求阅读回执(收件人阅读邮件时会提示回复发件人,表明邮件已收到,并已阅读)
        message.setHeader("Disposition-Notification-To", from);

        // 创建一个MIME子类型为"mixed"的MimeMultipart对象，表示这是一封混合组合类型的邮件
        MimeMultipart mailContent = new MimeMultipart("mixed");
        message.setContent(mailContent);

        // 附件
        MimeBodyPart attach1 = new MimeBodyPart();
        MimeBodyPart attach2 = new MimeBodyPart();
        // 内容
        MimeBodyPart mailBody = new MimeBodyPart();

        // 将附件和内容添加到邮件当中
        mailContent.addBodyPart(attach1);
        mailContent.addBodyPart(attach2);
        mailContent.addBodyPart(mailBody);

        // 附件1(利用jaf框架读取数据源生成邮件体)
        DataSource ds1 = new FileDataSource("src/loading.gif");
        DataHandler dh1 = new DataHandler(ds1);
        attach1.setFileName(MimeUtility.encodeText("loading.gif"));
        attach1.setDataHandler(dh1);

        // 附件2
        DataSource ds2 = new FileDataSource("src/车.png");
        DataHandler dh2 = new DataHandler(ds2);
        System.out.println("======================"+dh2.getName()+"====================");
        attach2.setDataHandler(dh2);
        attach2.setFileName(MimeUtility.encodeText(dh2.getName()));

        // 邮件正文(内嵌图片+html文本)
        MimeMultipart body = new MimeMultipart("related");  //邮件正文也是一个组合体,需要指明组合关系
        mailBody.setContent(body);

        // 邮件正文由html和图片构成
        MimeBodyPart imgPart = new MimeBodyPart();
        MimeBodyPart htmlPart = new MimeBodyPart();
        body.addBodyPart(imgPart);
        body.addBodyPart(htmlPart);

        // 正文图片
        DataSource ds3 = new FileDataSource("src/psb.jpg");
        DataHandler dh3 = new DataHandler(ds3);
        imgPart.setDataHandler(dh3);
        imgPart.setContentID("psb.jpg");

        // html邮件内容
        MimeMultipart htmlMultipart = new MimeMultipart("alternative");
        htmlPart.setContent(htmlMultipart);
        MimeBodyPart htmlContent = new MimeBodyPart();
        htmlContent.setContent(
                "<span style='color:red'>这是我自己用java mail发送的邮件哦！" +
                        "<img src='cid:psb.jpg' /></span> 复杂邮件"
                , "text/html;charset=gbk");
        htmlMultipart.addBodyPart(htmlContent);

        // 保存邮件内容修改
        message.saveChanges();

        // 发送邮件
        Transport.send(message);
    }

    public String getMsg(String str2,String str3){
        StringBuffer sb =new StringBuffer();
        sb.append("<!DOCTYPE html>  ");
        sb.append("<html lang=\"en\">");
        sb.append("  <head>");
        sb.append("    <meta charset=\"UTF-8\" />");
        sb.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />");
        sb.append("    <title>Document</title>");
        sb.append("  </head>");
        sb.append("  <body>");
        sb.append("    <div class=\"content\">");
//                String str = "<span style='color:red;'>这是带内嵌图片的HTML邮件哦！！！<img src=\"cid:psb.jpg\" style=\"display: block;width: 100%\"  /></span>多张图片<img src=\"cid:psb.jpg\" />侧认<img src=\"cid:loading.gif\" />识";
        String str = "<img src=\"cid:loading.gif\" style=\"display: block;width: 100%\" />识";
//        String str = " <img src=\"cid:psb.jpg\" class=\"content_header\"  />";
//        sb.append("<span style='color:red;'>这是带内嵌图片的HTML邮件哦！！！<img src=\"cid:psb.jpg\"   /></span>");
        sb.append(str2);
//        sb.append("   <span style='color:red;'>   <img src=\"cid:psb.jpg\"  /> </span>");
        sb.append("      <div class=\"content_body_box\">");
        sb.append("        <div class=\"content_body\">");
        sb.append("          <p class=\"p2\">尊敬的XXX，</p>");
        sb.append("          <p class=\"p2\" style=\"text-indent: 2em;\">您有XX条待办待您审批，请登陆Portal门户收件箱尽快完成审批。</p>");
        sb.append("          <table border=\"1\" cellspacing=\"0\" cellpadding=\"10\">");
        sb.append("            <tr class=\"table_tr\">");
        sb.append("              <td>流程类型</td>");
        sb.append("              <td>名称详情</td>");
        sb.append("              <td>发起人</td>");
        sb.append("              <td>创建时间</td>");
        sb.append("              <td>流程ID</td>");
        sb.append("            </tr>");
        sb.append("            <tr class=\"table_trr\">");
        sb.append("              <td>原收件箱清单-流程名称字段</td>");
        sb.append("              <td>新增字段，取值流程单据中的客户名称。营销计划/营销活动审批单据取值营销计划/营销活动的名称描述</td>");
        sb.append("              <td>取值流程发起人，原收件箱清单-发起人字段</td>");
        sb.append("              <td>BPM流程单创建时间，原收件箱清单-创建时间字段</td>");
        sb.append("              <td>BPM审批流程单号，原收件箱-流程ID字段</td>");
        sb.append("            </tr>");
        sb.append("          </table>");
        sb.append("");
        sb.append("          <p class=\"p2\" style=\"text-indent: 2em;\">本次邮件提醒由SAP于YYYY-MM-DD HH:MM:SS自动发送，请勿回复。如有问题，请联系IT服务台4001508585。</p>");
        sb.append("          <p class=\"p2\" style=\"text-indent: 2em;\">谢谢！</p>");
        sb.append("        </div>");
        sb.append("      </div>");
        sb.append(" ");
        sb.append(str3);
//        sb.append("      <img src="+header+" class=\"content_footer\" alt=\"\" />");
        sb.append("    </div>");
        sb.append("  </body>");
        sb.append("  <style> ");
        sb.append("    * { ");
        sb.append("      margin: 0; ");
        sb.append("      padding: 0; ");
        sb.append("    } ");
        sb.append("    .content_header { ");
        sb.append("      display: block; ");
        sb.append("      width: 100%; ");
        sb.append("    } ");
        sb.append("    .content_body_box { ");
        sb.append("      overflow: hidden; ");
        sb.append("      background: #9ed8f6; ");
        sb.append("    } ");
        sb.append("    .content_body { ");
        sb.append("      min-height: 500px; ");
        sb.append("      overflow: hidden; ");
        sb.append("      background: #eaf3fa; ");
        sb.append("      padding: 50px 20px 30px 20px; ");
        sb.append("      margin: 0 10px 0 10px; ");
        sb.append("    } ");
        sb.append("    .content_footer { ");
        sb.append("      display: block; ");
        sb.append("      width: 100%; ");
        sb.append("    } ");
        sb.append("    .p2 { ");
        sb.append("      font-size: 14px; ");
        sb.append("      line-height: 40px; ");
        sb.append("    } ");
        sb.append("    table { ");
        sb.append("      margin-left: 2em; ");
        sb.append("      margin-top: 20px; ");
        sb.append("      margin-bottom: 20px; ");
        sb.append("    } ");
        sb.append("    .table_tr { ");
        sb.append("      text-align: center; ");
        sb.append("      font-size: 14px; ");
        sb.append("    } ");
        sb.append("    .table_trr { ");
        sb.append("      font-size: 14px; ");
        sb.append("    }  ");
        sb.append("    .table_trr td { ");
        sb.append("      padding: 5px; ");
        sb.append("    } ");
        sb.append("  </style> ");
        sb.append("</html> ");
        return sb.toString();
    }

}
