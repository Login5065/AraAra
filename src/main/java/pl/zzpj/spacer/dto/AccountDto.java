package pl.zzpj.spacer.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class AccountDto {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
}
