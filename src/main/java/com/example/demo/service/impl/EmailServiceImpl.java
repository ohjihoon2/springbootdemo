package com.example.demo.service.impl;

import com.example.demo.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;


@Component
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    String sendFrom;

    /**
     * 기본 메일 전송
     * @param to
     * @param subject
     * @param text
     * @return
     */
    @Override
    public boolean sendMail(String to, String subject, String text) {
        String sendTo = to;
        String mailTitle = subject;
        String mailContent = text;

        MimeMessagePreparator preparator = mimeMessage -> {
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true,"UTF-8");

            message.setTo(sendTo);
            message.setFrom(sendFrom);	//env.getProperty("spring.mail.username")
            message.setSubject(mailTitle);
            message.setText(mailContent, true); //ture : html 형식 사용

            //Mail에 img 삽입
//            ClassPathResource resource = new ClassPathResource("img 주소/img 이름.png");
//            message.addInline("img", resource.getFile());
        };

        try{
            emailSender.send(preparator);
            log.debug("이메일 발송 완료");
        } catch (MailException e){
            log.debug("mailException = {}",e);
            log.debug("이메일 발송 실패");
            return false;
        }
        return true;
    }

    /**
     * 파일 첨부 메일 전송
     * @param to
     * @param subject
     * @param text
     * @param filePath
     * @return
     */
    @Override
    public boolean sendMailwithFile(String to, String subject, String text, String filePath) {
        // javax.mail.internet.MimeMessage
        MimeMessage message = emailSender.createMimeMessage();

        try {
            // org.springframework.mail.javamail.MimeMessageHelper
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setSubject(subject);
            helper.setText(text, true);
            helper.setFrom(sendFrom);
            helper.setTo(to);

            // 첨부 파일 처리
            if (filePath != null) {
                File file = new File(filePath);
                if (file.exists()) {
                    helper.addAttachment(file.getName(), new File(filePath));
                }
            }

            emailSender.send(message);
            log.debug("이메일 발송 완료");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            log.debug("이메일 발송 실패");
        }
        return false;
    }
}
