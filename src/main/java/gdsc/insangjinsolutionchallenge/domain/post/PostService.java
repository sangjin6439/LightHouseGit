package gdsc.insangjinsolutionchallenge.domain.post;

import gdsc.insangjinsolutionchallenge.domain.comment.CommentService;
import gdsc.insangjinsolutionchallenge.domain.comment.ResponseCommentDto;
import gdsc.insangjinsolutionchallenge.domain.like.LikeRepository;
import gdsc.insangjinsolutionchallenge.domain.user.User;
import gdsc.insangjinsolutionchallenge.domain.user.UserRepository;
import gdsc.insangjinsolutionchallenge.global.exception.ApplicationErrorException;
import gdsc.insangjinsolutionchallenge.global.exception.ApplicationErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CommentService commentService;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final static long POST_SCORE=15;

    @Transactional
    public String save(Long userId, RequestPostDto requestPostDto) {
        User userInfo = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("없는 유저입니다."));
        Post post = Post.builder()
                .title(requestPostDto.getTitle())
                .content(requestPostDto.getContent())
                .user(userInfo)
                .build();
            userInfo.addTotalScoreAndUpdateLevel(POST_SCORE);
//        userRepository.save(userInfo);
        postRepository.save(post);
        return "저장 완료!";
    }

    // all이면 전체 , 아니면 likeCount로 검색
    @Transactional(readOnly = true)
    public List<ResponsePostListDto> findAll(Long userId) {
        List<Post> posts = postRepository.findAll();

        List<ResponsePostListDto> responsePostDtos = posts.stream()
                .map(post -> ResponsePostListDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .likeCount(post.getLikeCount())
                        .userName(post.getUser().getName())
                        .userLevel(post.getUser().getLevel())
                        .createAt(post.getCreateAt())
                        .verifyLike(verifyUserLike(userId, post.getId()))
                        .build())
                .toList();

        return responsePostDtos;
    }

    @Transactional(readOnly = true)
    public List<ResponsePostLikeListDto> findAllByLikeCount() {
        List<Post> posts = postRepository.
                findAll(Sort.by(Sort.Direction.DESC, "likeCount"));

        List<ResponsePostLikeListDto> responsePostLikeDtos = posts.stream()
                .map(post -> ResponsePostLikeListDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .likeCount(post.getLikeCount())
                        .userName(post.getUser().getName())
                        .userLevel(post.getUser().getLevel())
                        .createAt(post.getCreateAt())
                        .build())
                .toList();

        return responsePostLikeDtos;
    }

    // 검색어로 검색
    @Transactional(readOnly = true)
    public List<ResponsePostListDto> findSearchTerm(String searchTerm) {
        List<Post> posts = postRepository.findByTitleContaining(searchTerm);
        List<ResponsePostListDto> responsePostDtos = posts.stream()
                .map(post -> ResponsePostListDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .likeCount(post.getLikeCount())
                        .userName(post.getUser().getName())
                        .userLevel(post.getUser().getLevel())
                        .createAt(post.getCreateAt())
                        .build())
                .toList();
        return responsePostDtos;
    }

    @Transactional(readOnly = true)
    public ResponsePostDto find(Long userId, Long id) {
        Post post = findPostDao(id);
        List<ResponseCommentDto> commentDtos = commentService.findAll(id);

        ResponsePostDto responsePostDto = ResponsePostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userName(post.getUser().getName())
                .userLevel(post.getUser().getLevel())
                .comments(commentDtos)
                .verifyLike(verifyUserLike(userId, id))
                .createAt(post.getCreateAt())
                .updateAt(post.getUpdateAt())
                .likeCount(post.getLikeCount())
                .build();
        return responsePostDto;
    }


    @Transactional
    public String update(Long userId, Long id, RequestPostDto requestPostDto) {
        String userEmail = findPostDao(id).getUser().getEmail();
        String myEmail = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("없는 사용자입니다")).getEmail();
        if (!userEmail.equals(myEmail)) {
            throw new ApplicationErrorException(ApplicationErrorType.UNAUTHORIZED, "권한이 없는 사용자입니다.");
        }
        Post post = findPostDao(id);
        post.update(requestPostDto);
        return "수정 완료!";
    }

    @Transactional
    public String delete(Long userId, Long id) {
        Long authorId = Long.valueOf(String.valueOf(findPostDao(id).getUser().getId()));
        if (!authorId.equals(userId)) {
            throw new ApplicationErrorException(ApplicationErrorType.UNAUTHORIZED, "권한이 없는 사용자입니다.");
        }
        postRepository.delete(findPostDao(id));
        return "삭제 완료";
    }


    public Post findPostDao(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("다시 확인하세요."));
    }

    //유저가 해당 게시물에 좋아요를 하면 true 아니면 false반환인데 프론트에서 0과 1로 반환 부탁함 -> 프론트에서 하트에 불 들어오는거 구현
    public int verifyUserLike(Long userId, Long postId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("잘못된 유저 정보입니다."));
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("없는 게시물입니다"));

        if (likeRepository.existsByUserAndPost(user, post) == true) {
            return 1;
        } else return 0;
    }
}

