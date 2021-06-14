package pl.zzpj.spacer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.zzpj.spacer.util.Default;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("accounts")
public class Account {


    @Id
    @Getter
    private String id;

    @NotNull
    @Getter
    @Size(min = 1, max = 32)
    @Indexed(unique = true)
    private String username;

    @NotNull
    @Getter
    @Setter
    @Size(min = 60, max = 64)
    private String password;

    @NotNull
    @Getter
    @Setter
    private String firstName;

    @NotNull
    @Getter
    @Setter
    private String lastName;

    @NotNull
    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private Set<String> likedPictures = new HashSet<>();

    @Default
    public Account(String username, String password, String firstName, String lastName, String email) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
