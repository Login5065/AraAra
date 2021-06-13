package pl.zzpj.spacer.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.zzpj.spacer.exception.AccountException;
import pl.zzpj.spacer.model.Account;
import pl.zzpj.spacer.repositories.AccountRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Captor
    private ArgumentCaptor<Account> accountArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> usernameArgumentCaptor;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void addAccountWithNoDuplicatesInDatabase() {
        //given
        Account account = new Account("TestId", "TestUsername", "TestPassword",
                "TestFirstName", "TestLastName", new ArrayList<>());
        //when
        assertDoesNotThrow(() -> accountService.addAccount(account));
        verify(accountRepository).save(accountArgumentCaptor.capture());
        //then
        then(accountRepository).should().findByUsername(accountArgumentCaptor.getValue().getUsername());
        then(accountRepository).should().save(accountArgumentCaptor.getValue());
        then(accountRepository).shouldHaveNoMoreInteractions();
        assertEquals(account, accountArgumentCaptor.getValue());
    }

    @Test
    void addAccountWithDuplicatesInDatabase() {
        //given
        Account account = new Account("TestId", "TestUsername", "TestPassword",
                "TestFirstName", "TestLastName", new ArrayList<>());
        given(accountRepository.findByUsername(any(String.class))).willReturn(java.util.Optional.of(account));
        //when
        assertThrows(AccountException.class, () -> accountService.addAccount(account));
        //then
        then(accountRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void getAccountWithProperUsername() {
        //given
        Account account = new Account("TestId", "TestUsername", "TestPassword",
                "TestFirstName", "TestLastName", new ArrayList<>());
        given(accountRepository.findByUsername(any(String.class))).willReturn(java.util.Optional.of(account));
        //when
        assertDoesNotThrow(() -> accountService.getAccount(account.getUsername()));
        verify(accountRepository).findByUsername(usernameArgumentCaptor.capture());
        //then
        then(accountRepository).should().findByUsername(usernameArgumentCaptor.getValue());
        then(accountRepository).shouldHaveNoMoreInteractions();
        assertEquals(account.getUsername(), usernameArgumentCaptor.getValue());
    }

    @Test
    void getAccountWithBadUsername() {
        //given
        Account account = new Account("TestId", "TestUsername", "TestPassword",
                "TestFirstName", "TestLastName", new ArrayList<>());
        given(accountRepository.findByUsername(any(String.class))).willReturn(Optional.empty());
        //when
        assertThrows(AccountException.class, () -> accountService.getAccount(account.getUsername()));
        //then
        then(accountRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void getAll() {
        //given
        Account account = new Account("TestId", "TestUsername", "TestPassword",
                "TestFirstName", "TestLastName", new ArrayList<>());
        given(accountRepository.findAll()).willReturn(List.of(account));
        //when
        assertDoesNotThrow(() -> accountService.getAll());
        //then
        then(accountRepository).should().findAll();
        then(accountRepository).shouldHaveNoMoreInteractions();

    }

    @Test
    void editAccount() {
        //given
        Account account = new Account("TestId", "TestUsername", "TestPassword",
                "TestFirstName", "TestLastName", new ArrayList<>());
        Account account2 = new Account("TestId2", "TestUsername", "TestPassword2",
                "TestFirstName2", "TestLastName2", new ArrayList<>());
        given(accountRepository.findByUsername(any(String.class))).willReturn(java.util.Optional.of(account));
        //when
        assertDoesNotThrow(() -> accountService.editAccount(account.getUsername(), account2));
        verify(accountRepository).findByUsername(usernameArgumentCaptor.capture());
        //then
        then(accountRepository).should().findByUsername(usernameArgumentCaptor.getValue());
        then(accountRepository).should().save(account);
        then(accountRepository).shouldHaveNoMoreInteractions();
        assertEquals(account2.getUsername(), usernameArgumentCaptor.getValue());
        assertEquals(account.getFirstName(), account2.getFirstName());
        assertEquals(account.getLastName(), account2.getLastName());
    }

    @Test
    void deleteAccount() {
        //given
        Account account = new Account("TestId", "TestUsername", "TestPassword",
                "TestFirstName", "TestLastName", new ArrayList<>());
        given(accountRepository.findByUsername(any(String.class))).willReturn(java.util.Optional.of(account));
        //when
        assertDoesNotThrow(() -> accountService.deleteAccount(account.getUsername()));
        verify(accountRepository).findByUsername(usernameArgumentCaptor.capture());
        //then
        then(accountRepository).should().findByUsername(usernameArgumentCaptor.getValue());
        then(accountRepository).should().delete(account);
        then(accountRepository).shouldHaveNoMoreInteractions();
        assertTrue(accountRepository.findAll().isEmpty());
    }

}
