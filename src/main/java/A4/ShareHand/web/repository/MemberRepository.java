package A4.ShareHand.web.repository;

import A4.ShareHand.domain.Member;
import A4.ShareHand.domain.MemberDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    //findBy는 규칙
    // select * from user where username = ?;
    //라는 sql 자동 생성

    Member findById(long id);

    Member findByEmail(String email);

    @Query("Select m from Member m where m.email = :email")
    Optional<Member> findByEmailOptional(@Param("email") String email);

    @Modifying
    @Query("delete from Member m where m.id=:id")
    void deleteById(@Param("id") long id);

    @Override
    void deleteById(Integer integer);
}
