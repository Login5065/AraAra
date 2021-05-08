package pl.zzpj.spacer.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AccountDto {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
}
