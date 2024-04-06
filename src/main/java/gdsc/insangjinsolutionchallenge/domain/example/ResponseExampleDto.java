package gdsc.insangjinsolutionchallenge.domain.example;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ResponseExampleDto {
    private Long id;
    private String title;
    private String content;
    private String multipleChoice;
    private String imgPath;
    private String correct;
    private Double correctPercentage;
    private Long score;
    private String category;
    private String grade;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}