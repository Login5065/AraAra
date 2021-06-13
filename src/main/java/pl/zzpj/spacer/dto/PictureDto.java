package pl.zzpj.spacer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
public class PictureDto {

    private UUID id;

    private String url;

    private String title;

    private Set<String> tags;

    private Date creationDate;

}
