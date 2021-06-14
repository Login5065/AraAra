package pl.zzpj.spacer.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
public class AccountDto {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
}
