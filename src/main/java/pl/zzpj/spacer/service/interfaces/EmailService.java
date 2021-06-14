package pl.zzpj.spacer.service.interfaces;

import pl.zzpj.spacer.exception.AccountException;

public interface EmailService {
    void sendMessage(String to, String subject, String text);

    void sendMessageToAllAccounts(String subject, String text) throws AccountException;

    boolean isValidEmailAddress(String email);
}
