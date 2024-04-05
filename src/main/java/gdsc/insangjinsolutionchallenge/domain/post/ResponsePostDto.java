package gdsc.insangjinsolutionchallenge.domain.post;

import gdsc.insangjinsolutionchallenge.domain.comment.ResponseCommentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ResponsePostDto {

    private Long id;
    private String userName;
    private Integer userLevel;
    private String title;
    private String content;
    private Integer likeCount;
    private List<ResponseCommentDto> comments;
    private int verifyLike;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;


}
