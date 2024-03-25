package gdsc.insangjinsolutionchallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.reactive.function.client.WebClient;

@EnableJpaAuditing
@SpringBootApplication
@EnableCaching
public class InSangJinSolutionChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(InSangJinSolutionChallengeApplication.class, args);
    }

    @Bean
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }

}
