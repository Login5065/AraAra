package pl.zzpj.spacer.controller;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.zzpj.spacer.dto.NewAccountDto;
import pl.zzpj.spacer.dto.mapper.NewAccountMapper;
import pl.zzpj.spacer.exception.AppBaseException;
import pl.zzpj.spacer.service.AccountServiceImpl;

@RestController
@AllArgsConstructor
public class AccountController {

    private final AccountServiceImpl accountService;

    private final NewAccountMapper newAccountMapper;

    @PostMapping("/register")
    public void register(@RequestBody NewAccountDto accountDto) throws AppBaseException{
        accountService.addAccount(newAccountMapper.newAccountDtoToEntity(accountDto));
    }

}
