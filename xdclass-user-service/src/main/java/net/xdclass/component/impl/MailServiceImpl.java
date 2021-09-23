package net.xdclass.component.impl;


import lombok.extern.slf4j.Slf4j;
import net.xdclass.component.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailServiceImpl implements MailService {


    /**
     * springBoot:提供的简单发送邮件配置
     */
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.from}")
    private String fromAddress;


    @Override
    public void sendMail(String to, String subject, String content) {
        //创建一个邮箱消息对象
        SimpleMailMessage message = new SimpleMailMessage();

        //配置邮箱发送人
        message.setFrom(fromAddress);

        //邮件的收件人
        message.setTo(to);

        //邮件的主题
        message.setSubject(subject);

        //邮件的内容
        message.setText(content);

        mailSender.send(message);

        log.info("邮件发送成功:{}", message.toString());
    }
}
