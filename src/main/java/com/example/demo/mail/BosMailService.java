package com.example.demo.mail;

import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

/**
 * <p>
 *
 * </p>
 *
 * @Author xiaodong.xuexd
 * @Date 2019-11-19
 * @Time 14:37
 */
@Slf4j
@Component
public class BosMailService {

    private static final String PASSWORD = "密码";

    private static final String MAIL_TIMEOUT = "30000";

    @Value("${spring.mail.default-cc}")
    private String[] mailCc;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Resource
    private JavaMailSenderImpl mailSender;

    @Autowired
    private Configuration configuration;


    @PostConstruct
    void configSenderPassword() {
        mailSender.setPassword(PASSWORD);
    }


    public boolean sendMailNewRetail(MailInfo mailInfo) {
        mailInfo.setFromAddress(this.mailFrom);

        return sendMail(mailInfo);
    }

    public boolean sendMail(MailInfo mailInfo) {
        boolean flag = true;
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            // 设置邮件消息的发送者
            MimeMessageHelper sendMailMessage = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            // 设置发送人
            if (!StringUtils.isEmpty(mailInfo.getFromAddress())) {
                sendMailMessage.setFrom(mailInfo.getFromAddress());
            } else {
                sendMailMessage.setFrom(mailFrom);
            }

            // 设置邮件接收者地址`
            sendMailMessage.setTo(mailInfo.getToAddress());

            // 设置邮件抄送者地址
            if (isNotEmpty(mailInfo.getToCarbonCopyAddress())) {
                sendMailMessage.setCc(mailInfo.getToCarbonCopyAddress());
            }

            // 设置邮件密送者地址
            if (isNotEmpty(mailInfo.getToBlindCarbonCopyAddress())) {
                sendMailMessage.setBcc(mailInfo.getToBlindCarbonCopyAddress());
            }

            // 设置主题
            sendMailMessage.setSubject(mailInfo.getSubject());
            // 设置邮件发送的时间
            sendMailMessage.setSentDate(new Date());
            // 设置内容
            sendMailMessage.setText(mailInfo.getContent(), true);
            // 设置附件
            if (isNotEmpty(mailInfo.getAttachFileNames())) {
                for (String filePath : mailInfo.getAttachFileNames()) {
                    FileSystemResource file = new FileSystemResource(new File(filePath));
                    sendMailMessage.addAttachment(file.getFilename(), file);
                }
            }

            // 设置超时
            Properties prop = new Properties();
            prop.put("mail.smtp.timeout", MAIL_TIMEOUT);
            prop.put("mail.smtp.connectiontimeout", MAIL_TIMEOUT);
            mailSender.setJavaMailProperties(prop);

            // 发送内容
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            flag = false;
            log.error("发送邮件发生异常！error=", e);
        } catch (MailSendException me) {

        }

        return flag;
    }

    private String buildAddressError(Address[] addresses) {
        if (addresses != null) {
            return Arrays.toString(addresses);
        }
        return "null";
    }

    private boolean isNotEmpty(String[] strings){
        return false;
    }


}
