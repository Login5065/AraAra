package pl.zzpj.spacer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.zzpj.spacer.exception.AppBaseException;
import pl.zzpj.spacer.model.Account;
import pl.zzpj.spacer.repositories.AccountRepository;


@Service
public class AccountServiceImpl {


    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void addAccount(Account account) throws AppBaseException {
        if (accountRepository.findById(account.getId()).isEmpty()) {
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            accountRepository.insert(account);
        }
    }

}
