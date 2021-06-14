package pl.zzpj.spacer.service;

import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.MailException;
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

    private final JavaMailSender emailSender;

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
