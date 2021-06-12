package pl.zzpj.spacer.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.zzpj.spacer.exception.AccountException;
import pl.zzpj.spacer.exception.AppBaseException;
import pl.zzpj.spacer.model.Account;
import pl.zzpj.spacer.repositories.AccountRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AccountServiceImpl {


    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;


    public void addAccount(Account account) throws AppBaseException {
        if (accountRepository.findByUsername(account.getUsername()).isEmpty()) {
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            accountRepository.save(account);
        } else {
            throw AccountException.accountExistsException();
        }
    }

    public Account getAccount(String username) throws AccountException {
        return accountRepository.findByUsername(username).orElseThrow(AccountException::noSuchAccountException);
    }

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public void editAccount(String username, Account account) throws AppBaseException {
        Optional<Account> queryAccount = accountRepository.findByUsername(username);
        if (queryAccount.isPresent()) {
            Account temp = queryAccount.get();
            if (username.equals(account.getUsername()) && account.getPassword() != null) {
                temp.setPassword(passwordEncoder.encode(account.getPassword()));
            } else {
                throw AccountException.usernameMismatch();
            }
            temp.setFirstName(account.getFirstName());
            temp.setLastName(account.getLastName());
            accountRepository.save(temp);
        } else {
            throw AccountException.noSuchAccountException();
        }
    }

    public void deleteAccount(String username) throws AppBaseException {
        Optional<Account> queryAccount = accountRepository.findByUsername(username);
        if (queryAccount.isPresent()) {
            Account temp = queryAccount.get();
            accountRepository.delete(temp);
        } else {
            throw AccountException.noSuchAccountException();
        }
    }

}
