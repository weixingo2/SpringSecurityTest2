package com.springSecurity.utils;

import com.springSecurity.entity.MailBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MailUtil {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    //要发送到的邮箱
    private final static String TO_MAIL_ADDR = "1309463811@qq.com";

    /**
     * 发送邮件测试方法
     */
    public void sendMail() {
        SimpleMailMessage mimeMessage = new SimpleMailMessage();
        mimeMessage.setFrom(sender);
        mimeMessage.setTo(TO_MAIL_ADDR);
        mimeMessage.setSubject("SpringBoot集成JavaMail实现邮件发送");
        mimeMessage.setText("SpringBoot集成JavaMail实现邮件发送正文");
        mailSender.send(mimeMessage);
    }

    /**
     * 发送简易邮件
     * @param MailBean
     */
    public void sendMail(MailBean MailBean) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            helper.setFrom(sender);
            helper.setTo(TO_MAIL_ADDR);
            helper.setSubject(MailBean.getSubject());
            helper.setText(MailBean.getText());
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        mailSender.send(mimeMessage);
    }

    /**
     * 发送邮件-邮件正文是HTML
     * @param MailBean
     */
    public void sendMailHtml(MailBean MailBean) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            helper.setFrom(sender);
            helper.setTo(TO_MAIL_ADDR);
            helper.setSubject(MailBean.getSubject());
            helper.setText(MailBean.getText(), true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        mailSender.send(mimeMessage);
    }
    /**
     * 发送邮件-附件邮件
     * @param MailBean
     */
    public void sendMailAttachment(MailBean MailBean) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(sender);
            helper.setTo(TO_MAIL_ADDR);
            helper.setSubject(MailBean.getSubject());
            helper.setText(MailBean.getText(), true);
            // 增加附件名称和附件
            helper.addAttachment(MailBean.getAttachmentFilename(), MailBean.getFile());
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 内联资源（静态资源）邮件发送
     * 由于邮件服务商不同，可能有些邮件并不支持内联资源的展示
     * 在测试过程中，新浪邮件不支持，QQ邮件支持
     * 不支持不意味着邮件发送不成功，而且内联资源在邮箱内无法正确加载
     * @param MailBean
     */
    public void sendMailInline(MailBean MailBean) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(sender);
            helper.setTo(TO_MAIL_ADDR);
            helper.setSubject(MailBean.getSubject());

            /*
             * 内联资源邮件需要确保先设置邮件正文，再设置内联资源相关信息
             */
            helper.setText(MailBean.getText(), true);
            helper.addInline(MailBean.getContentId(), MailBean.getFile());

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 模板邮件发送
     * @param MailBean
     */
    public void sendMailTemplate(MailBean MailBean) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(sender);
            helper.setTo(TO_MAIL_ADDR);
            helper.setSubject(MailBean.getSubject());
            helper.setText(MailBean.getText(), true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}