package pl.zzpj.spacer.service;

import java.util.List;
import java.util.Properties;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import pl.zzpj.spacer.exception.AccountException;
import pl.zzpj.spacer.model.Account;
import pl.zzpj.spacer.repositories.AccountRepository;
import pl.zzpj.spacer.service.interfaces.EmailService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {
    private static final String NOREPLY_ADDRESS = "spacerjava@gmail.com";

    private final AccountRepository accountRepository;


    private final JavaMailSender emailSender = getJavaMailSender();

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("spacerjava@gmail.com");
        mailSender.setPassword("wpptsnipayqfbflp");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    @Override
    public void sendMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(NOREPLY_ADDRESS);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void sendMessageToAllAccounts(String subject, String text) throws AccountException {
        List<Account> accounts = accountRepository.findAll();
        for(Account acc : accounts) {
            if(isValidEmailAddress(acc.getEmail())) {
                sendMessage(acc.getEmail(), subject, text);
            } else {
                throw AccountException.noSuchEmailException();
            }
        }
    }

    @Override
    public boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
}
