package A4.ShareHand.web.repository;

import A4.ShareHand.domain.Member;
import A4.ShareHand.domain.Work;
import A4.ShareHand.domain.WorkApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WorkApplyRepository extends JpaRepository<WorkApply, Long> {
    Optional<WorkApply> findByWorkAndMember(Work work, Member member);

    List<WorkApply> findByMember(Member member);

//    @Modifying
//    @Query("select w from Work w where w.member = :member and w.status = :status")
//    List<WorkApply> findByMemberAndStatus(@Param("member") Member member, @Param("status") String Sttus);

    List<WorkApply> findTop5ByMemberEqualsOrderByCreatedDateDesc(Member member);
    List<WorkApply> findTop5ByMemberAndCreatedDateLessThanOrderByCreatedDateDesc(Member member, LocalDateTime createdDate);

    List<WorkApply> findTop5ByMemberAndReviewPermissionEqualsOrderByCreatedDateDesc(Member member, boolean permission);
    List<WorkApply> findTop5ByMemberAndReviewPermissionAndCreatedDateLessThanOrderByCreatedDateDesc(Member member, boolean permission, LocalDateTime createdDate);


    @Query("SELECT wa FROM WorkApply wa WHERE wa.reviewPermission = :reviewPermission AND wa.member = :member")
    List<WorkApply> findByReviewPermissionAndMemberId(@Param("reviewPermission") boolean reviewPermission, @Param("member") Member member);


    int countByMember(Member member);
    int countByMemberAndReviewPermissionEquals(Member member, boolean permission);
}
