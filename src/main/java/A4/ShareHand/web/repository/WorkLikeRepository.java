package A4.ShareHand.web.repository;

import A4.ShareHand.domain.Member;
import A4.ShareHand.domain.Work;
import A4.ShareHand.domain.WorkLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkLikeRepository extends JpaRepository<WorkLike, Long> {

    Optional<WorkLike> findByWorkAndMember(Work work, Member member);
}
