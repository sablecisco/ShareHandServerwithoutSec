package A4.ShareHand.web.repository;

import A4.ShareHand.domain.TestObj;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTestRepository extends JpaRepository<TestObj, Integer> {

}
