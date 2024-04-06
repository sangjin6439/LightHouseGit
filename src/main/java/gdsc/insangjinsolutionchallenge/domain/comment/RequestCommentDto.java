package gdsc.insangjinsolutionchallenge.domain.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestCommentDto {
    @NotBlank(message = "글자를 입력해 주세요.")
    private String content;
}
