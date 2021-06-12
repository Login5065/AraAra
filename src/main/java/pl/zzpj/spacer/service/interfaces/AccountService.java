package pl.zzpj.spacer.service.interfaces;

import pl.zzpj.spacer.exception.AccountException;
import pl.zzpj.spacer.exception.AppBaseException;
import pl.zzpj.spacer.model.Account;

import java.util.List;

public interface AccountService {
    void addAccount(Account account) throws AccountException;

    Account getAccount(String username) throws AccountException;

    List<Account> getAll();

    void editAccount(String username, Account account) throws AccountException;

    void deleteAccount(String username) throws AppBaseException;
}
