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
    public ResponseEntity<AccountEntity> getUserById(@PathVariable("id") Long id) {
        AccountEntity entity = service.findAccount(id);
        if (entity != null) return new ResponseEntity<>(entity, HttpStatus.OK);

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);


    }

    @PostMapping("")
    public ResponseEntity<AccountEntity> createAccount(@RequestBody AccountEntity entity) {
        try {
            AccountEntity accountEntity = service.createAccount(AccountEntity.builder()
                    .id(entity.getId())
                    .login(entity.getLogin())
                    .password(entity.getPassword())
                    .build());
            return new ResponseEntity<>(accountEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
