package A4.ShareHand.web.repository;

import A4.ShareHand.domain.MemberDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberDetailsRepository extends JpaRepository<MemberDetails, Integer> {

    @Modifying
    @Query("delete from MemberDetails m where m.memberDetailId=:id")
    void deleteById(@Param("id") long id);
}
