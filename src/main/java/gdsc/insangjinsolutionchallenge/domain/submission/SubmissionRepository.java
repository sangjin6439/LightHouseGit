package gdsc.insangjinsolutionchallenge.domain.submission;

import gdsc.insangjinsolutionchallenge.domain.example.Example;
import gdsc.insangjinsolutionchallenge.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    boolean existsByUserAndExampleAndCorrect(User user, Example example, Boolean correct);
}
