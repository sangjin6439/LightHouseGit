package gdsc.insangjinsolutionchallenge.domain.comment;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentSseResponse {

    Long postId;
    String userName;
    LocalDateTime commentedTime;
}
