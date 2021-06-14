package pl.zzpj.spacer.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zzpj.spacer.dto.AccountDto;
import pl.zzpj.spacer.dto.NewAccountDto;
import pl.zzpj.spacer.dto.mapper.AccountMapper;
import pl.zzpj.spacer.dto.mapper.NewAccountMapper;
import pl.zzpj.spacer.exception.AccountException;
import pl.zzpj.spacer.exception.AppBaseException;
import pl.zzpj.spacer.exception.PictureException;
import pl.zzpj.spacer.service.interfaces.AccountService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    private final NewAccountMapper newAccountMapper;

    private final AccountMapper accountMapper;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody NewAccountDto accountDto) {
        try {
            accountService.addAccount(newAccountMapper.newAccountDtoToEntity(accountDto));
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (AppBaseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("account/{username}")
    public ResponseEntity getOwnAccount(@PathVariable("username") String username) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(accountMapper.accountToAccountDto(accountService.getAccount(username)));
        } catch (AccountException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }


    }

    @GetMapping("accounts")
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAll().stream()
                .map(accountMapper::accountToAccountDto)
                .collect(Collectors.toList()));
    }

    @PutMapping("account/{username}")
    public ResponseEntity<String> editOwnAccount(@PathVariable("username") String username,
                                                 @RequestBody
                                                         NewAccountDto accountDto) {
        try {
            accountService.editAccount(username, newAccountMapper.newAccountDtoToEntity(accountDto));
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (AppBaseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("account/{username}")
    public ResponseEntity<String> deleteOwnAccount(@PathVariable("username") String username) {
        try {
            accountService.deleteAccount(username);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (AppBaseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("account/{username}")
    public ResponseEntity<String> addOwnLikedPicture(@PathVariable("username") String username, @RequestBody
            String pictureId) {
        try {
            accountService.addLikedPicture(username, pictureId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (AccountException | PictureException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("account/{username}")
    public ResponseEntity<String> removeOwnLikedPicture(@PathVariable("username") String username, @RequestBody
            String pictureId) {
        try {
            accountService.removeLikedPicture(username, pictureId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (AccountException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
