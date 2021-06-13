package pl.zzpj.spacer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class RatingDto {
    private String owner;
    private String pictureId;
    private Integer rating;
    private String date;
}
