package pl.zzpj.spacer.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AccountDto {

    @NotBlank
    private long id;

    @NotBlank
    @Size(min = 8, max = 20)
    private String login;

    @NotBlank
    @Size(min = 8)
    private String password;

}
