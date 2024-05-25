package gdsc.insangjinsolutionchallenge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import gdsc.insangjinsolutionchallenge.domain.like.Like;
import gdsc.insangjinsolutionchallenge.domain.like.LikeRepository;
import gdsc.insangjinsolutionchallenge.domain.like.LikeService;
import gdsc.insangjinsolutionchallenge.domain.post.Post;
import gdsc.insangjinsolutionchallenge.domain.post.PostService;
import gdsc.insangjinsolutionchallenge.domain.user.User;
import gdsc.insangjinsolutionchallenge.domain.user.UserRepository;

@SpringBootTest
@Transactional
public class LikeServiceTest {

    @Autowired
    private LikeService likeService;

    @MockBean
    private LikeRepository likeRepository;

    @MockBean
    private PostService postService;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("게시판 좋아요 작동 테스트")
    public void testAddLike_whenUserAndPostExist_andLikeDoesNotExist_thenLikeIsAdded() {
        // Given
        Long userId = 1L;
        Long postId = 1L;
        User user = User.builder()
                .id(userId)
                .build();
        Post post = Post.builder()
                .id(postId)
                .likeCount(0)
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(postService.findPostDao(postId)).thenReturn(post);
        when(likeRepository.existsByUserAndPost(user, post)).thenReturn(false);
        when(likeRepository.countLikesByPostId(postId)).thenReturn(1);

        // When
        int likeCount = likeService.addLike(userId, postId);

        // Then
        assertThat(likeCount).isEqualTo(1);
        verify(likeRepository, times(1)).save(any(Like.class));
        assertThat(post.getLikeCount()).isEqualTo(1);
    }

    @DisplayName("이미 좋아요를 눌렀던 게시판에 다시 누르고 좋아요 지우는 테스트")
    @Test
    public void testAddLike_whenUserAndPostExist_andLikeAlreadyExists_thenLikeIsRemoved() {
        // Given
        Long userId = 1L;
        Long postId = 1L;
        User user = User.builder()
                .id(userId)
                .build();
        Post post = Post.builder()
                .id(postId)
                .likeCount(1)
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(postService.findPostDao(postId)).thenReturn(post);
        when(likeRepository.existsByUserAndPost(user, post)).thenReturn(true);
        when(likeRepository.countLikesByPostId(postId)).thenReturn(0);

        // When
        int likeCount = likeService.addLike(userId, postId);

        // Then
        assertThat(likeCount).isEqualTo(0);
        verify(likeRepository, times(1)).deleteByUserAndPost(user, post);
        assertThat(post.getLikeCount()).isEqualTo(0);
    }

    @DisplayName("존재하지 않는 유저가 좋아요 누를 때 예외발생 테스트")
    @Test
    public void testAddLike_whenUserDoesNotExist(){
        // Given
        Long userId = 1L;
        Long postId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When/Then

        assertThatThrownBy(() -> likeService.addLike(userId, postId))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
