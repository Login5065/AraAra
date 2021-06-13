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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Getter
    @Setter
    private List<String> likedPhotos = new ArrayList<>();

    @Default
    public Account(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
