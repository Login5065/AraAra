package pl.zzpj.spacer.service;

import pl.zzpj.spacer.model.AccountEntity;

public interface AccountService {

    AccountEntity findAccount(long id);

    AccountEntity createAccount(AccountEntity entityDTO);
}
