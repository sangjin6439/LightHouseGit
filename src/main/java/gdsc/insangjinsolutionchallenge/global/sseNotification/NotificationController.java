package gdsc.insangjinsolutionchallenge.global.sseNotification;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    public static Map<Long, SseEmitter> sseEmitters = new ConcurrentHashMap<>();		// 1. 모든 Emitters를 저장하는 ConcurrentHashMap

    // sse연결을 위한 구독
    @GetMapping(value = "/subscribe/{userId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@PathVariable("userId") Long userId) {
        SseEmitter sseEmitter = notificationService.subscribe(userId);
        return sseEmitter;
    }

//    @GetMapping(value = "/subscribe/{userId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public SseEmitter subscribe(@PathVariable("userId") Long userId){
//        return notificationService.subscride(userId);
//    }
}
