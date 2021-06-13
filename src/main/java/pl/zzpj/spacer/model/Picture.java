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
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("pictures")
public class Picture {
    @Id
    @Getter
    private String id;

    @NotNull
    @Getter
    private String url;

    @NotNull
    @Getter
    @Indexed(unique = true)
    private String title;

    @Getter
    @Setter
    private Set<String> tags = new HashSet<>();

    @Getter
    @Setter
    private Date creationDate = new Date(System.currentTimeMillis());

    @Default
    public Picture(String url, String title, Set<String> tags) {
        this.url = url;
        this.title = title;
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", tags=" + tags +
                ", addDate=" + creationDate +
                '}';
    }
}
