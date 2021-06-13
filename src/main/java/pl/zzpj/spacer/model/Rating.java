package pl.zzpj.spacer.model;

import lombok.*;
import org.springframework.data.annotation.Id;
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
@Document("ratings")
public class Rating {

    @Id
    @Getter
    private UUID id = UUID.randomUUID();

    @NotNull
    @Getter
    @Setter
    private String owner;

    @NotNull
    @Getter
    @Setter
    private String pictureId;

    @NotNull
    @Getter
    @Setter
    private int rating;

    @NotNull
    @Getter
    @Setter
    private LocalDateTime date = LocalDateTime.now();

    @Default
    public Rating(String owner, String pictureId, int rating) {
        this.owner = owner;
        this.pictureId = pictureId;
        this.rating = rating;
    }

}
