package com.huang.demo.email;

import cn.hutool.core.util.ZipUtil;
import com.sun.mail.util.MailSSLSocketFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

/**
 * @author localuser
 * create at 2023/2/8 15:00
 * @description Java实现纯文本邮件发送
 */
public class SendEmailZipDemo {

    private static final String MAIL_HOST = "smtp.exmail.qq.com";
    private static final String USER_NAME = "huanglang@zwcad.com";
    private static final String PASS_WORD = "163168abcABC2023";
    private static final String AUTH_CODE = "5Xq4YWqKYrso5fod";


    public static void main(String[] args) throws GeneralSecurityException, MessagingException, UnsupportedEncodingException {
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));

        //创建一个配置文件并保存
        Properties properties = new Properties();
        properties.setProperty("mail.host", MAIL_HOST);
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");

        //设置SSL加密
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);

        //创建一个session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER_NAME, PASS_WORD);
            }
        });

        //开启debug模式
//        session.setDebug(true);

        //获取连接对象
        Transport transport = session.getTransport();

        //连接服务器：邮箱发送服务器、邮箱账号、16位授权码
        transport.connect(MAIL_HOST, USER_NAME, AUTH_CODE);

        //创建邮件对象
        MimeMessage mimeMessage = new MimeMessage(session);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent("测试邮件，时间：" + dateTime, "text/html;charset=UTF-8");

        MimeMultipart mimeMultipart = new MimeMultipart("mixed");//指定为混合关系

        mimeMultipart.addBodyPart(mimeBodyPart);

        //组装附件
        String filePath = "D:\\壁纸";
//        File targetFile = new File(filePath + File.separator + "20230209_log.rar");
        String zipPath = "D:\\壁纸压缩\\";
        File zipFile = ZipUtil.zip(filePath, zipPath + dateTime + "_log.rar", Charset.defaultCharset(), false);
        if (zipFile.exists()) {
            MimeBodyPart part = new MimeBodyPart();
            part.setDataHandler(new DataHandler(new FileDataSource(zipFile.getAbsolutePath())));
            part.setFileName(zipFile.getName());
            mimeMultipart.addBodyPart(part);
        }

//        File file = new File(filePath);
//        File[] files = file.listFiles();
//        if (files != null) {
//            for (File f : files) {
//                MimeBodyPart part = new MimeBodyPart();
//                part.setDataHandler(new DataHandler(new FileDataSource(f.getAbsolutePath())));
//                part.setFileName(f.getName());
//                mimeMultipart.addBodyPart(part);
//            }
//        }

        mimeMessage.setContent(mimeMultipart);

        //邮件发送人
        mimeMessage.setFrom(new InternetAddress("huanglang@zwcad.com"));

        //邮件接收人
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress("huanglang@zwcad.com"));

        //邮件标题
        mimeMessage.setSubject("来自 java demo 的一封邮件");

        //发送邮件
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());

        //关闭连接
        transport.close();
    }
}
