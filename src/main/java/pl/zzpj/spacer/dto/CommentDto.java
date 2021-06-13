package pl.zzpj.spacer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class CommentDto {
    private String owner;
    private String pictureId;
    private String content;
    private String date;
}
