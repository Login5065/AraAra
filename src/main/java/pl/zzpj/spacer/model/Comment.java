package pl.zzpj.spacer.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.zzpj.spacer.util.Default;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("comments")
public class Comment {

    @Id
    @Getter
    private UUID id = UUID.randomUUID();

    @DBRef
    @Getter
    @Setter
    private Account owner;

    @DBRef
    @Getter
    @Setter
    private Picture parentPicture;

    @NotNull
    @Getter
    @Setter
    private String content;

    @NotNull
    @Getter
    @Setter
    private LocalDateTime date = LocalDateTime.now();

    @Default
    public Comment(Account owner, Picture parentPicture, String content) {
        this.owner = owner;
        this.parentPicture = parentPicture;
        this.content = content;
    }
}
