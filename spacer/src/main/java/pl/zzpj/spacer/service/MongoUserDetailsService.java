package pl.zzpj.spacer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.zzpj.spacer.exception.AccountException;
import pl.zzpj.spacer.model.Account;
import pl.zzpj.spacer.repositories.AccountRepository;

import java.util.Collection;
import java.util.Collections;

@Service
public class MongoUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Autowired
    public MongoUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Account not found",
                        AccountException.noSuchAccountException()));
        Collection<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("USER"));
        return User
                .withUsername(account.getUsername())
                .password(account.getPassword())
                .authorities(authorities)
                .build();
    }
}
