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
import pl.zzpj.spacer.model.Picture;
import pl.zzpj.spacer.repositories.AccountRepository;
import pl.zzpj.spacer.repositories.PictureRepository;

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
    private PictureRepository pictureRepository;

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
                "TestFirstName", "TestLastName", new HashSet<>());
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
                "TestFirstName", "TestLastName", new HashSet<>());
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
                "TestFirstName", "TestLastName", new HashSet<>());
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
                "TestFirstName", "TestLastName", new HashSet<>());
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
                "TestFirstName", "TestLastName", new HashSet<>());
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
                "TestFirstName", "TestLastName", new HashSet<>());
        Account account2 = new Account("TestId2", "TestUsername", "TestPassword2",
                "TestFirstName2", "TestLastName2", new HashSet<>());
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
                "TestFirstName", "TestLastName", new HashSet<>());
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

    @Test
    void addLikedPicture() {
        //given
        Account account = new Account("TestId", "TestUsername", "TestPassword",
                "TestFirstName", "TestLastName", new HashSet<>());
        given(accountRepository.findByUsername(any(String.class))).willReturn(java.util.Optional.of(account));
        Picture picture = new Picture("TestID", "www.testurl.it",
                "TestTitle",
                new HashSet<>(Collections.singletonList("test")),
                new Date());
        given(pictureRepository.findById(any(String.class))).willReturn(java.util.Optional.of(picture));
        //when
        assertDoesNotThrow(() -> accountService.addLikedPicture(account.getUsername(), picture.getId()));
        //then
        then(accountRepository).should().findByUsername(account.getUsername());
        then(pictureRepository).should().findById(picture.getId());
        then(accountRepository).should().save(account);
        then(accountRepository).shouldHaveNoMoreInteractions();
        then(pictureRepository).shouldHaveNoMoreInteractions();
        assertTrue(account.getLikedPictures().contains(picture.getId()));
    }

    @Test
    void removeLikedPicture() {
        //given
        Account account = new Account("TestId", "TestUsername", "TestPassword",
                "TestFirstName", "TestLastName", new HashSet<>(Collections.singletonList("TestID")));
        given(accountRepository.findByUsername(any(String.class))).willReturn(java.util.Optional.of(account));
        //when
        assertDoesNotThrow(() -> accountService.removeLikedPicture(account.getUsername(), "TestID"));
        //then
        then(accountRepository).should().findByUsername(account.getUsername());
        then(accountRepository).should().save(account);
        then(accountRepository).shouldHaveNoMoreInteractions();
        assertTrue(account.getLikedPictures().isEmpty());
    }

}
