package gdsc.insangjinsolutionchallenge.domain.example;

import lombok.Data;

@Data
public class RequestExampleDto {
    private String title;
    private String content;
    private String multipleChoice;
    private String imgPath;
    private String correct;
    private String category;
    private String grade;
    private Long score;
}
