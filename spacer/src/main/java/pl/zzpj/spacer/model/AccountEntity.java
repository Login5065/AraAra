package pl.zzpj.spacer.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder(access = AccessLevel.PUBLIC)
@Data
@Document
public class AccountEntity {

    @Id
    private Long id;

    @NotNull(message = "login can't be empty")
    @Size(min = 6, max = 20)
    private String login;

    @NotNull(message = "password can't be empty")
    @Size(min = 8)
    private String password;

    public AccountEntity(Long id, @NotNull(message = "login can't be empty") @Size(min = 6, max = 20) String login, @NotNull(message = "password can't be empty") @Size(min = 8) String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }
}
