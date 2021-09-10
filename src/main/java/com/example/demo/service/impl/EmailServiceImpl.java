package com.example.demo.service.impl;

import com.example.demo.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;


@Component
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    String sendFrom;

    @Override
    public boolean sendMail(String to, String subject, String text) {
        String sendTo = to;
        String mailTitle = subject;
        String mailContent = text;

        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true,"UTF-8");

                message.setTo(sendTo);
                message.setFrom(sendFrom);	//env.getProperty("spring.mail.username")
                message.setSubject(mailTitle);
                message.setText(mailContent, true); //ture : html 형식 사용

                //Mail에 img 삽입
//                ClassPathResource resource = new ClassPathResource("img 주소/img 이름.png");
//                message.addInline("img", resource.getFile());
            }
        };

        try{
            emailSender.send(preparator);
        } catch (MailException e){
            return false;
        }
        return true;
    }
}
