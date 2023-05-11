package A4.ShareHand.web.repository;

import A4.ShareHand.domain.Member;
import A4.ShareHand.domain.Work;
import A4.ShareHand.domain.WorkScrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WorkScrapRepository extends JpaRepository<WorkScrap, Long> {

    int countByMember(Member member);
    WorkScrap findByWorkScrapId(long id);

    @Override
    Optional<WorkScrap> findById(Long id);

    List<WorkScrap> findWorkScrapByMember(Member member);

    List<WorkScrap> findTop5ByMemberEqualsOrderByCreatedDateDesc(Member member);

    List<WorkScrap> findTop5ByMemberAndCreatedDateLessThanOrderByCreatedDateDesc(Member member, LocalDateTime createdDate);

    Optional<WorkScrap> findByWorkAndMember(Work work, Member member);
}
