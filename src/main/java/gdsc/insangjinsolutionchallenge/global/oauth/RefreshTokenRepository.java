package gdsc.insangjinsolutionchallenge.global.oauth;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

//CrudRepository 인터페이스에 @Repository가 이미 추가 되어있음
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findById(String key);
}

