package pl.zzpj.spacer.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.zzpj.spacer.util.Default;

import javax.validation.constraints.NotNull;
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

    @NotNull
    @Getter
    @Setter
    private UUID owner;

    @NotNull
    @Getter
    @Setter
    private UUID parentPost;

    @NotNull
    @Getter
    @Setter
    private String content;

    @NotNull
    @Getter
    @Setter
    private Date date = new Date(System.currentTimeMillis());

    @Default
    public Comment(UUID owner, UUID parentPost, String content) {
        this.owner = owner;
        this.parentPost = parentPost;
        this.content = content;
    }
}
