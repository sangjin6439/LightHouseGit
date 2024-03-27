package gdsc.insangjinsolutionchallenge.domain.post;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ResponsePostLikeListDto {
    // verifyLike제외
    private Long id;
    private String userName;
    private Integer userLevel;
    private String title;
    private String content;
    private int likeCount;
    private LocalDateTime createAt;
}
