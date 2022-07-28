package com.example.demo.config;

import com.example.demo.service.CommonService;
import com.example.demo.vo.SingletonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Map;
import java.util.Properties;

@Configuration
public class MailConfig {

    @Autowired
    private JasyptConfig jasyptConfig;

    @Autowired
    private CommonService commonService;

    public String username;
    public String password;

    @Bean
    public JavaMailSenderImpl mailSender() {
        Map<String, Object> configData = SingletonData.getInstance().getConfigData();
        username = configData.get("mailAddress").toString();
        password = jasyptConfig.stringEncryptor().decrypt(configData.get("mailPassword").toString());

        JavaMailSenderImpl javaMailSender = null;
        String type = configData.get("mailType").toString();
        if(type.equals("google")){
            javaMailSender = googleSetting();
        }else if(type.equals("naver")){
            javaMailSender = naverSetting();
        }else if(type.equals("kakao")){
            javaMailSender = kakaoSetting();
        }else if(type.equals("nate")){
            javaMailSender = nateSetting();
        }

        return javaMailSender;//객체의 주소값을 반환
    }

    public JavaMailSenderImpl googleSetting() {
        String host = "smtp.gmail.com";
        int port= 587;
        Properties prop = new Properties();
        prop.setProperty("mail.smtp.auth","true");
        prop.setProperty("mail.smtp.starttls.enable","true");

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        javaMailSender.setJavaMailProperties(prop);

        return javaMailSender;//객체의 주소값을 반환
    }

    public JavaMailSenderImpl naverSetting() {
        String host = "smtp.naver.com";
        int port= 465;
        Properties prop = new Properties();
        prop.setProperty("mail.smtp.auth","true");
        prop.setProperty("mail.smtp.ssl.enable","true");
        prop.setProperty("mail.smtp.ssl.trust", host);

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        javaMailSender.setJavaMailProperties(prop);

        return javaMailSender;//객체의 주소값을 반환
    }

    public JavaMailSenderImpl kakaoSetting() {
        String host = "smtp.daum.net";
        int port= 465;
        Properties prop = new Properties();
        prop.setProperty("mail.smtp.auth","true");
        prop.setProperty("mail.smtp.ssl.enable","true");
        prop.setProperty("mail.smtp.ssl.trust", host);

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        javaMailSender.setJavaMailProperties(prop);

        return javaMailSender;//객체의 주소값을 반환
    }

    public JavaMailSenderImpl nateSetting() {
        String host = "smtp.mail.nate.com";
        int port= 465;
        Properties prop = new Properties();
        prop.setProperty("mail.smtp.auth","true");
        prop.setProperty("mail.smtp.ssl.enable","true");
        prop.setProperty("mail.smtp.ssl.trust", host);

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        javaMailSender.setJavaMailProperties(prop);

        return javaMailSender;//객체의 주소값을 반환
    }
}
