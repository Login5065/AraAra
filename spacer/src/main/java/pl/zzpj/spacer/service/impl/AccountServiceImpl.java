package pl.zzpj.spacer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zzpj.spacer.model.AccountEntity;
import pl.zzpj.spacer.repository.AccountRepository;
import pl.zzpj.spacer.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    @Autowired
    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public AccountEntity findAccount(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public AccountEntity createAccount(AccountEntity entityDTO) {
       return repository.save(entityDTO);
    }
}
