package gdsc.insangjinsolutionchallenge.domain.like;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gdsc.insangjinsolutionchallenge.domain.post.Post;
import gdsc.insangjinsolutionchallenge.domain.post.PostService;
import gdsc.insangjinsolutionchallenge.domain.user.User;
import gdsc.insangjinsolutionchallenge.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostService postService;
    private final UserRepository userRepository;

    @Transactional
    public int addLike(Long userId, Long postId) {
        User userInfo = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("올바른 유저 정보를 입력해 주세요."));
        Post post = postService.findPostDao(postId);

        if (!likeRepository.existsByUserAndPost(userInfo, post)) {
            post.increaseLikeCount();
            Like like = Like.builder()
                    .user(userInfo)
                    .post(post)
                    .build();
            likeRepository.save(like);
        } else {
            post.decreaseLikeCount();
            likeRepository.deleteByUserAndPost(userInfo, post);
        }

        return likeRepository.countLikesByPostId(postId);
    }
}
