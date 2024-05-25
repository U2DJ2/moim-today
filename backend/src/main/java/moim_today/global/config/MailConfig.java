package moim_today.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    Properties pt = new Properties();

    @Value("${application.mail.host}")
    private String host;
    @Value("${application.mail.username}")
    private String username;
    @Value("${application.mail.password}")
    private String password;
    @Value("${application.mail.port}")
    private int port;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        javaMailSender.setPort(port);

        pt.put("mail.smtp.auth", true);
        pt.put("mail.smtp.starttls.enable", true);
        pt.put("mail.smtp.ssl.enable", true);
        pt.put("mail.smtp.debug", true);

        javaMailSender.setJavaMailProperties(pt);
        javaMailSender.setDefaultEncoding("UTF-8");

        return javaMailSender;
    }
}
