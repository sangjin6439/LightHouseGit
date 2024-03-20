package gdsc.insangjinsolutionchallenge.global.oauth;

import gdsc.insangjinsolutionchallenge.domain.user.LoginUserDto;
import gdsc.insangjinsolutionchallenge.global.Token.TokenDto;
import gdsc.insangjinsolutionchallenge.global.Token.TokenRequestDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "로그인 API", description = "로그인관련 API.")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public String signup(@RequestBody @Valid LoginUserDto loginUserDto) {
        return authService.signup(loginUserDto);
    }

    //로그인 할때에는 @valid빼기 -> 로그인시에는 이름, 나이를 넣지 않아서 검증에 걸림
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginUserDto loginUserDto) {
        return ResponseEntity.ok(authService.login(loginUserDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }
}
