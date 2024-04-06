package gdsc.insangjinsolutionchallenge.domain.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ResponseCommentDto {
    private Long id;
    private String userName;
    private Integer userLevel;
    private String content;
    private LocalDateTime createAt;
}
