package com.example.algamoney.api.config;

import com.example.algamoney.api.config.property.AlgamoneyApiProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Autowired
    private AlgamoneyApiProperty property;

    @Bean
    public JavaMailSender javaMailSender(){
        Properties properties = new Properties();
        properties.put("mail.transport.protocol","smtp");
        properties.put("mail.smtp.starttls.enable",true);
        properties.put("mail.smtp.auth",true);
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.connectiontimeout", 10 * 1000);

        JavaMailSenderImpl mail = new JavaMailSenderImpl();
        mail.setJavaMailProperties(properties);
        mail.setHost(property.getMail().getHost());
        mail.setPort(property.getMail().getPort());
        mail.setUsername(property.getMail().getUsername());
        mail.setPassword(property.getMail().getPassword());
        return mail;
    }
}
