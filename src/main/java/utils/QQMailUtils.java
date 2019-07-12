package utils;

/**
 * @Classname QQMailUtils
 * @Description
 * @@Author Pupansheng
 * @Date 2019/7/10 9:09
 * @Vestion 1.0
 **/
import java.util.Date;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class QQMailUtils {

    public static String myEmailAccount ="2944809997@qq.com";
    public static String myEmailPassword ="glzeoficcqavdhfh";//这个密码不是QQ密码或者邮箱密码，要验证的。自己百度

    // 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般格式为: smtp.xxx.com
    // qq邮箱的 SMTP 服务器地址为: smtp.qq.com
    public static String myEmailSMTPHost = "smtp.qq.com";

    // 收件人邮箱（替换为自己知道的有效邮箱）
    public static String receiveMailAccount;

    public static String getMyEmailAccount() {
        return myEmailAccount;
    }

    public static void setMyEmailAccount(String myEmailAccount) {
        QQMailUtils.myEmailAccount = myEmailAccount;
    }

    public static String getMyEmailPassword() {
        return myEmailPassword;
    }

    public static void setMyEmailPassword(String myEmailPassword) {
        QQMailUtils.myEmailPassword = myEmailPassword;
    }

    public static String getMyEmailSMTPHost() {
        return myEmailSMTPHost;
    }

    public static void setMyEmailSMTPHost(String myEmailSMTPHost) {
        QQMailUtils.myEmailSMTPHost = myEmailSMTPHost;
    }

    public static String getReceiveMailAccount() {
        return receiveMailAccount;
    }

    public static void setReceiveMailAccount(String receiveMailAccount) {
        QQMailUtils.receiveMailAccount = receiveMailAccount;
    }
    public static void sent(String sentPoint,String context,String topic)
    {

        //设置收件人
        setReceiveMailAccount(sentPoint);

        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证

        // 开启 SSL 连接, 以及更详细的发送步骤请看上一篇: 基于 JavaMail 的 Java 邮件发送：简单邮件发送
        //QQ邮箱端口有两个，可以百度。
        final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);

        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getInstance(props);
        // 设置为debug模式, 可以查看详细的发送 log
        //  session.setDebug(true);

        // 3. 创建一封邮件
        MimeMessage message;
        try {
            message = createMimeMessage(session, myEmailAccount, receiveMailAccount,topic,context);


            // 也可以保持到本地查看
            // message.writeTo(file_out_put_stream);

            // 4. 根据 Session 获取邮件传输对象
            Transport transport = session.getTransport();

            // 5. 使用 邮箱账号 和 密码 连接邮件服务器
            //    这里认证的邮箱必须与 message 中的发件人邮箱一致，否则报错
            try {
                transport.connect(myEmailAccount,myEmailPassword);
            } catch (Exception e) {
                System.out.println("连接错误");
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());

            // 7. 关闭连接
            transport.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }






    }
    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail,String topic,String context) throws Exception {
        // 1. 创建邮件对象
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人
        message.setFrom(new InternetAddress(sendMail, "蒲蒲", "UTF-8"));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.addRecipient(RecipientType.TO, new InternetAddress(receiveMail, "亲爱的", "UTF-8"));

        // 4. Subject: 邮件主题
        message.setSubject(topic, "UTF-8");


        //创建文本“节点”
        MimeBodyPart text = new MimeBodyPart();
        //    这里添加图片的方式是将整个图片包含到邮件内容中, 实际上也可以以 http 链接的形式添加网络图片

        text.setContent(context, "text/html;charset=UTF-8");


        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text);



        // 11. 设置整个邮件的关系（将最终的混合“节点”作为邮件的内容添加到邮件对象）
        message.setContent(mm);

        // 12. 设置发件时间

        message.setSentDate(new Date());

        // 13. 保存上面的所有设置
        message.saveChanges();

        return message;
    }





}