package pl.zzpj.spacer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zzpj.spacer.controller.dto.AccountDto;
import pl.zzpj.spacer.controller.mapper.AccountMapper;
import pl.zzpj.spacer.model.AccountEntity;
import pl.zzpj.spacer.service.impl.AccountServiceImpl;

import java.util.Optional;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountServiceImpl service;

    @Autowired
    public AccountController(AccountServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getUserById(@PathVariable("id") Long id) {
        AccountDto entity = AccountMapper.INSTANCE.accountEntityToDto(service.findAccount(id));
        if (entity != null) return new ResponseEntity<>(entity, HttpStatus.OK);

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);


    }

    @PostMapping("")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto entity) {
        try {
            AccountDto createdAccount = AccountMapper.INSTANCE.accountEntityToDto
                    (service.createAccount(AccountMapper.INSTANCE.accountDtoToEntity(entity)));
            return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
