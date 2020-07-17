package com.example.hbasestudy.controller;

import org.apache.commons.httpclient.util.DateUtil;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * @author liwenxing
 * @Version 1.0
 * @Description cesh
 * @date 2020/7/13 16:32
 */
public class SendMailText {

    //发件人地址
    public static String senderAddress = "lwx18848956826@163.com";
    //收件人地址
    public static String recipientAddress = "2632669616@qq.com";
    //发件人账户名
    public static String senderAccount = "lwx18848956826";
    //发件人账户密码
    public static String senderPassword = "li150187@";

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
        transport.sendMessage(msg, msg.getAllRecipients());

        //如果只想发送给指定的人，可以如下写法
        //transport.sendMessage(msg, new Address[]{new InternetAddress("xxx@qq.com")});

        //5、关闭邮件连接
        transport.close();
    }

    /**
     * 获得创建一封邮件的实例对象
     *
     * @param session
     * @return
     * @throws MessagingException
     * @throws AddressException
     */
    public static MimeMessage getMimeMessage(Session session) throws Exception {
        //创建一封邮件的实例对象
        MimeMessage msg = new MimeMessage(session);
        //设置发件人地址
        msg.setFrom(new InternetAddress(senderAddress));
        /**
         * 设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
        msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recipientAddress));
        //设置邮件主题
        msg.setSubject("邮件主题", "UTF-8");
        //设置邮件正文
        msg.setContent("简单的纯文本邮件！", "text/html;charset=UTF-8");
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());

        return msg;
    }

    public StringBuffer sendMessage() {
        //定义拼接的字符集
        StringBuffer tips = new StringBuffer();


        tips.setLength(0);
        tips.append("tips.append(<!DOCTYPE html>                                                                                                                              ");
        tips.append("<html lang=\"en\">                                                                                                                                       ");
        tips.append("  <head>                                                                                                                                                 ");
        tips.append("    <meta charset=\"UTF-8\" />                                                                                                                           ");
        tips.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />                                                                         ");
        tips.append("    <title>Document</title>                                                                                                                              ");
        tips.append("  </head>                                                                                                                                                ");
        tips.append("  <body>                                                                                                                                                 ");
        tips.append("    <div class=\"content\">                                                                                                                              ");
        tips.append("      <img src=\"" + "C:\\Users\\admin\\Desktop\\soft\\FSG\\images\\header.png" + "\" class=\"content_header\" alt=\"\" />                                                                              ");
        tips.append("      <div class=\"content_body_box\">                                                                                                                   ");
        tips.append("        <div class=\"content_body\">                                                                                                                     ");
        tips.append("          <p class=\"p2\">尊敬的" + "liwenxing" + "：</p>                                                                                              ");
        tips.append("          <p class=\"p2\" style=\"text-indent: 2em;\">您有" + 14 + "条待办待您审批，请登陆Portal门户收件箱尽快完成审批。</p>                               ");
        tips.append("          <table border=\"1\" cellspacing=\"0\" cellpadding=\"10\">                                                                                      ");
        tips.append("            <tr class=\"table_tr\">                                                                                                                      ");
        tips.append("              <td>流程类型</td>                                                                                                                          ");
        tips.append("              <td>名称详情</td>                                                                                                                          ");
        tips.append("              <td>发起人</td>                                                                                                                            ");
        tips.append("              <td>创建时间</td>                                                                                                                          ");
        tips.append("              <td>流程ID</td>                                                                                                                            ");
        tips.append("            </tr>                                                                                                                                        ");
        tips.append("          </table>                                                                                                                                       ");
        tips.append("                                                                                                                                                         ");
        tips.append("          <p class=\"p2\" style=\"text-indent: 2em;\">本次邮件提醒由SAP于" + DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss") + "自动发送，请勿回复。如有问题，请联系IT服务台4001508585。</p> ");
        tips.append("          <p class=\"p2\" style=\"text-indent: 2em;\">谢谢！</p>                                                                                         ");
        tips.append("        </div>                                                                                                                                           ");
        tips.append("      </div>                                                                                                                                             ");
        tips.append("                                                                                                                                                         ");
        tips.append("      <img src=\"./images/footer.jpg\" class=\"content_footer\" alt=\"\" />                                                                              ");
        tips.append("    </div>                                                                                                                                               ");
        tips.append("  </body>                                                                                                                                                ");
        tips.append("  <style>                                                                                                                                                ");
        tips.append("    * {                                                                                                                                                  ");
        tips.append("      margin: 0;                                                                                                                                         ");
        tips.append("      padding: 0;                                                                                                                                        ");
        tips.append("    }                                                                                                                                                    ");
        tips.append("    .content_header {                                                                                                                                    ");
        tips.append("      display: block;                                                                                                                                    ");
        tips.append("      width: 100%;                                                                                                                                       ");
        tips.append("    }                                                                                                                                                    ");
        tips.append("    .content_body_box {                                                                                                                                  ");
        tips.append("      overflow: hidden;                                                                                                                                  ");
        tips.append("      background: #9ed8f6;                                                                                                                               ");
        tips.append("    }                                                                                                                                                    ");
        tips.append("    .content_body {                                                                                                                                      ");
        tips.append("      min-height: 500px;                                                                                                                                 ");
        tips.append("      overflow: hidden;                                                                                                                                  ");
        tips.append("      background: #eaf3fa;                                                                                                                               ");
        tips.append("      padding: 50px 20px 30px 20px;                                                                                                                      ");
        tips.append("      margin: 0 10px 0 10px;                                                                                                                             ");
        tips.append("    }                                                                                                                                                    ");
        tips.append("    .content_footer {                                                                                                                                    ");
        tips.append("      display: block;                                                                                                                                    ");
        tips.append("      width: 100%;                                                                                                                                       ");
        tips.append("    }                                                                                                                                                    ");
        tips.append("    .p2 {                                                                                                                                                ");
        tips.append("      font-size: 14px;                                                                                                                                   ");
        tips.append("      line-height: 40px;                                                                                                                                 ");
        tips.append("    }                                                                                                                                                    ");
        tips.append("    table {                                                                                                                                              ");
        tips.append("      margin-left: 2em;                                                                                                                                  ");
        tips.append("      margin-top: 20px;                                                                                                                                  ");
        tips.append("      margin-bottom: 20px;                                                                                                                               ");
        tips.append("    }                                                                                                                                                    ");
        tips.append("    .table_tr {                                                                                                                                          ");
        tips.append("      text-align: center;                                                                                                                                ");
        tips.append("      font-size: 14px;                                                                                                                                   ");
        tips.append("    }                                                                                                                                                    ");
        tips.append("    .table_trr {                                                                                                                                         ");
        tips.append("      font-size: 14px;                                                                                                                                   ");
        tips.append("    }                                                                                                                                                    ");
        tips.append("    .table_trr td {                                                                                                                                      ");
        tips.append("      padding: 5px;                                                                                                                                      ");
        tips.append("    }                                                                                                                                                    ");
        tips.append("  </style>                                                                                                                                               ");
        tips.append("</html>   ");
        return tips;
    }
}
