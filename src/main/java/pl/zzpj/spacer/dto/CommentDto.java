package pl.zzpj.spacer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class CommentDto {

    private String username;
    private String content;
    private String date;
}
