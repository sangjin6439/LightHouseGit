package gdsc.insangjinsolutionchallenge.domain.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto {

    @Email(message = "이메일 형식을 확인해주세요.")
    @NotBlank
    private String email;

    @Size(min = 5, max = 20, message = "5자리 이상 20자리 이하의 비밀번호가 필요합니다.")
    @NotBlank
    private String password;

    private Authority authority;

    @NotBlank(message = "다시 확인해주세요")
    private String name;

    @NotNull(message = "다시 확인해주세요")
    private Integer age;

    @NotBlank(message = "다시 확인해주세요")
    private String school;

    private String country;

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .authority(authority)
                .age(age)
                .name(name)
                .school(school)
                .country(country)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
