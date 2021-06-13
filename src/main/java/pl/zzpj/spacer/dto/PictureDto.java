package pl.zzpj.spacer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
public class PictureDto {

    private String id;

    private String url;

    private String title;

    private Set<String> tags;

    private Date creationDate;

}
