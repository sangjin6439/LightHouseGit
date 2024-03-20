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
    // 웹으로 몇번째 post인지 알게해야함 그래야 이 포스트를 수정하게함
    private Long id;
    private String userName;
    private Integer userLevel;
    private String title;
    private String content;
    private Integer likeCount;
    private List<ResponseCommentDto> comments;
    //유저가 해당 게시물에 좋아요를 하면 true 아니면 false반환 -> 프론트에서 하트에 불 들어오는거 구현용
    private boolean verifyLike;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public static ResponsePostDto toDto(Post post){
        return ResponsePostDto.builder()
                .id(post.getId())
                .userName(post.getUser().getName())
                .userLevel(post.getUser().getLevel())
                .title(post.getTitle())
                .content(post.getContent())
                .createAt(post.getCreateAt())
                .updateAt(post.getUpdateAt())
                .build();
    }
}
