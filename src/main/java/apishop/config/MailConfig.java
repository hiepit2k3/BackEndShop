package apishop.config;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${config.mail.host}")
    private String host;
    @Value("${config.mail.port}")
    private String port;
    @Value("${config.mail.username}")
    private String email;
    @Value("${config.mail.password}")
    private String password;

    @Bean
    public Properties getProps() {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", port);
        return props;
    }
    @Bean
    public Session getSession() {
        return Session.getInstance(getProps(),
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, password);
                    }
                });
    }

    @Bean
    public Message getMessage() throws MessagingException {
        Message message = new MimeMessage(getSession());
        message.setFrom(new InternetAddress(email));
        return message;
    }
}
