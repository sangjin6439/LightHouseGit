package gdsc.insangjinsolutionchallenge.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gdsc.insangjinsolutionchallenge.domain.like.Like;
import gdsc.insangjinsolutionchallenge.domain.post.Post;
import gdsc.insangjinsolutionchallenge.domain.submission.Submission;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String country;

    private int age;

    private String school;

    private int totalScore;

    //buider 패턴으로 기본값을 1로 설정
    private int level;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Authority authority;

    // user삭제 시 해당 유저 문제 제출 삭제
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Submission> submissions = new ArrayList<>();

    @Builder
    public User(String email, String password, Authority authority) {
        this.email = email;
        this.password = password;
        this.authority = authority;
    }

    // 이름, 나이, 학교 바꿀 수 있게 열어둠
    public void updateUser(RequestUserDto requestUserDto) {
        this.name = requestUserDto.getName();
        this.age = requestUserDto.getAge();
        this.school = requestUserDto.getSchool();
        this.country = requestUserDto.getCountry();
    }

    //점수 획득 및 레벨 상승
    public void addTotalScoreAndUpdateLevel(Long score) {
        this.totalScore += score;
        this.level = (this.totalScore / 100);
    }
}