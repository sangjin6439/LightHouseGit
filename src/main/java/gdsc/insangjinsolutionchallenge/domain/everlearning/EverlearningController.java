package gdsc.insangjinsolutionchallenge.domain.everlearning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class EverlearningController {

    private final WebClient webClient;

    @Autowired
    public EverlearningController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://apis.data.go.kr/7010000").build();
    }

    @GetMapping("/everlearning/web/{pageNum}")
    public Mono<String> getLectureListWeb(@PathVariable("pageNum") int pageNum){
        //인증키를 URL에서 사용이 가능하도록 인코딩 된 인증키 값을 넣어야함
        String serviceKey = "TXz9igaEZjaoO84jGvXH3IpSnKKgsb6HG6HhFL23kS1FEb%2Bnn1%2BbgvEBDssl0bnMe%2BRsG7Gv5HMZHKpWHofd4Q%3D%3D";
        String pageNo = String.valueOf(pageNum);
        String numOfRows = "10";
        String url = String.format("/everlearning/getLectureList?ServiceKey=%s&pageNo=%s&numOfRows=%s",
                serviceKey, pageNo, numOfRows);

        return webClient.get()
                .uri(url) //API 엔드 포인트, 요청 URL설정
                .retrieve() //요청 보내기 메서드
                .bodyToMono(String.class); // 응답 데이터를 Mono<T>로 변환
    }

    @GetMapping("/everlearning/app/{pageNum}")
    public Mono<String> getLectureListApp(@PathVariable("pageNum") int pageNum){
        //인증키를 URL에서 사용이 가능하도록 인코딩 된 인증키 값을 넣어야함
        String serviceKey = "TXz9igaEZjaoO84jGvXH3IpSnKKgsb6HG6HhFL23kS1FEb%2Bnn1%2BbgvEBDssl0bnMe%2BRsG7Gv5HMZHKpWHofd4Q%3D%3D";
        String pageNo = String.valueOf(pageNum);
        String numOfRows = "30";
        String url = String.format("/everlearning/getLectureList?ServiceKey=%s&pageNo=%s&numOfRows=%s",
                serviceKey, pageNo, numOfRows);

        return webClient.get()
                .uri(url) //API 엔드 포인트, 요청 URL설정
                .retrieve() //요청 보내기 메서드
                .bodyToMono(String.class); // 응답 데이터를 Mono<T>로 변환
    }

}
