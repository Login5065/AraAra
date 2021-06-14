package pl.zzpj.spacer.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import pl.zzpj.spacer.repositories.AccountRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailServiceImpl emailService;

    @Test
    void validateEmail() {
        //given
        String email = "baka@gmail.com";
        String badEmail = "baka-gmail/com";

        //then
        assertTrue(emailService.isValidEmailAddress(email));
        assertFalse(emailService.isValidEmailAddress(badEmail));
    }

    @Test
    void SendEmailWithStrings() {
        //given
        String email = "baka@gmail.com";
        String subject = "test";
        String text = "test";

        assertDoesNotThrow(() -> emailService.sendMessage(email, subject, text));
    }

    @Test
    void SendEmailToEveryone() {
        //given
        String subject = "test";
        String text = "test";

        assertDoesNotThrow(() -> emailService.sendMessageToAllAccounts(subject, text));
    }
}
