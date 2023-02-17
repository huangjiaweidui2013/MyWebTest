package com.huang.learn.service;

import cn.hutool.core.util.ArrayUtil;
import com.huang.learn.domain.EmailPropertiesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author localuser
 * create at 2023/2/14 14:52
 * @description 邮件发送 service
 */
@Component
public class EmailService {
    // JavaMailSender 在Mail 自动配置类 MailSenderAutoConfiguration 中已经导入，这里直接注入使用即可
    @Autowired
    private JavaMailSender javaMailSender;

    //方法5个参数分别表示：邮件发送者、收件人、抄送人、邮件主题以及邮件内容
    public void sendSimpleMail(EmailPropertiesDTO dto) {
        SimpleMailMessage message = new SimpleMailMessage();
        // 简单邮件直接构建一个 SimpleMailMessage 对象进行配置并发送即可
        message.setFrom(dto.getFrom());
        message.setTo(dto.getTo());
        message.setCc(dto.getCc());
        message.setSubject(dto.getSubject());
        message.setText(dto.getContent());
        javaMailSender.send(message);
    }

    public void sendEmailWithAttachment(EmailPropertiesDTO dto) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        // 这里使用 MimeMessageHelper 简化了邮件配置
        // 第二个参数true表示构造一个 multipart message 类型邮件
        // multipart message类型邮件包含多个正文、附件以及内嵌资源，邮件表现形式更加丰富
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(dto.getFrom());
        helper.setTo(dto.getTo());
        helper.setSubject(dto.getSubject());
        helper.setText(dto.getContent(), dto.isHtml());

        String[] cc = dto.getCc();
        if (ArrayUtil.isNotEmpty(cc)) {
            helper.setCc(cc);
        }

        File[] files = dto.getFiles();
        if (ArrayUtil.isNotEmpty(files)) {
            for (File f : files) {
                helper.addAttachment(f.getName(), f);
            }
        }
        javaMailSender.send(message);
    }
}
