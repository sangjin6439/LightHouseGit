package gdsc.insangjinsolutionchallenge.domain.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseUserDto {
    private String name;
    private Integer age;
    private String email;
    private String school;
    private String country;
    private Integer level;
    private Integer totalScore;
    private Authority role;
}
