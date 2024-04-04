package gdsc.insangjinsolutionchallenge.global.sseNotification;

import gdsc.insangjinsolutionchallenge.domain.post.Post;
import gdsc.insangjinsolutionchallenge.domain.post.PostRepository;
import gdsc.insangjinsolutionchallenge.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class NotificationService {
    //dddddddd
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final EmitterRepository emitterRepository;

    private static final Long DEFAULT_TIMEOUT = 600L * 1000 * 60;

    // 메시지 알림
    public SseEmitter subscribe(Long userId) {

        // 1. 현재 클라이언트를 위한 sseEmitter 객체 생성
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);

        // 2. 연결
        try {
            sseEmitter.send(SseEmitter.event().name("connect"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 3. 저장
        NotificationController.sseEmitters.put(userId, sseEmitter);

        // 4. 연결 종료 처리
        sseEmitter.onCompletion(() -> NotificationController.sseEmitters.remove(userId));	// sseEmitter 연결이 완료될 경우
        sseEmitter.onTimeout(() -> NotificationController.sseEmitters.remove(userId));		// sseEmitter 연결에 타임아웃이 발생할 경우
        sseEmitter.onError((e) -> NotificationController.sseEmitters.remove(userId));		// sseEmitter 연결에 오류가 발생할 경우

        return sseEmitter;
    }

    // 댓글 알림 - 게시글 작성자 에게
    public void notifyComment(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );

        Long userId = post.getUser().getId();

        if (NotificationController.sseEmitters.containsKey(userId)) {
            SseEmitter sseEmitter = NotificationController.sseEmitters.get(userId);
            try {
                sseEmitter.send(SseEmitter.event().name("addComment").data(post.getTitle()+"게시물에 댓글이 달렸습니다."));
            } catch (Exception e) {
                NotificationController.sseEmitters.remove(userId);
            }
        }
    }

//    //클라이언트에서 구독하기 위한 메서드
//    public SseEmitter subscride(Long userId) {
//        SseEmitter emitter = createEmitter(userId);
//
//        /*처음 구독 시, 즉시 subcribe메서드에서 sendToClient()를 통해 데이터를 전송한다.
//        처음 sse응답시 아무런 이벤트가 없으면 재연결 요청을 보내거나 503(서버문제임, 서버 요청 처리 이슈)오류를 발생시키기 때문
//        */
//        sendToClient(userId, "EventStream Created. [userId= " + userId + "]", "sse 접속 성공");
//        return emitter;
//    }
//
//    public void notify(Long userId, Object data, String comment) {
//        sendToClient(userId, data, comment);
//    }
//
//    public <T> void customNotify(Long userId, T data, String comment, String type) {
//        sendToClient(userId, data, comment, type);
//    }
//
//    private void sendToClient(Long userId, Object data, String comment) {
//        SseEmitter emitter = emitterRepository.get(userId);
//        if (emitter != null) {
//            try {
//                emitter.send(SseEmitter.event()
//                        .id(String.valueOf(userId))
//                        .name("sse")
//                        .data(data)
//                        .comment(comment));
//            } catch (IOException e) {
//                emitterRepository.deleteById(userId);
//                emitter.completeWithError(e);
//            }
//        }
//    }
//
//    private <T> void sendToClient(Long userId, T data, String comment, String type) {
//        SseEmitter emitter = emitterRepository.get(userId);
//        if (emitter != null) {
//            try {
//                emitter.send(SseEmitter.event()
//                        .id(String.valueOf(userId))
//                        .name(type)
//                        .data(data)
//                        .comment(comment));
//            } catch (IOException e) {
//                emitterRepository.deleteById(userId);
//                emitter.completeWithError(e);
//            }
//        }
//    }
//
//    private SseEmitter createEmitter(Long userId) {
//        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
//        emitterRepository.save(userId, emitter);
//
//        emitter.onCompletion(() -> emitterRepository.deleteById(userId));
//        emitter.onTimeout(() -> emitterRepository.deleteById(userId));
//
//        return emitter;
//    }
//
//    private User validUser(Long userId){
//        return userRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 사용자입니다."));
//    }

}
