package pl.zzpj.spacer.controller;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.zzpj.spacer.dto.AccountDto;
import pl.zzpj.spacer.dto.NewAccountDto;
import pl.zzpj.spacer.dto.mapper.AccountMapper;
import pl.zzpj.spacer.dto.mapper.NewAccountMapper;
import pl.zzpj.spacer.exception.AppBaseException;
import pl.zzpj.spacer.service.AccountServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class AccountController {

    private final AccountServiceImpl accountService;

    private final NewAccountMapper newAccountMapper;

    private final AccountMapper accountMapper;

    @PostMapping("/register")
    public void register(@RequestBody NewAccountDto accountDto) throws AppBaseException {
        accountService.addAccount(newAccountMapper.newAccountDtoToEntity(accountDto));
    }

    @GetMapping("account/{username}")
    public AccountDto getOwnAccount(@PathVariable("username") String username) throws AppBaseException {
        return accountMapper.accountToAccountDto(accountService.getAccount(username));
    }

    @GetMapping("accounts")
    public List<AccountDto> getAllAccounts() {
        return accountService.getAll().stream().map(accountMapper::accountToAccountDto).collect(Collectors.toList());
    }

    @PutMapping("account/{username}")
    public void editOwnAccount(@PathVariable("username") String username,
                               @RequestBody
                               NewAccountDto accountDto) throws AppBaseException {
        accountService.editAccount(username, newAccountMapper.newAccountDtoToEntity(accountDto));
    }


}
