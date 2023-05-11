package A4.ShareHand.web.repository;


import A4.ShareHand.domain.Member;
import A4.ShareHand.domain.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface WorkRepository extends JpaRepository<Work, Long> {

    int countByMember(Member member);

    long countByStatus(String status);
    long countByStatusAndCategory(String status, String category);
    long countByStatusAndTitleContaining(String status, String keyword);

    Work findByWorkId(long workId);

    @Modifying
    @Query("delete from Work w where w.member=:member")
    void deleteAllByMember(@Param("member") Member member);

    List<Work> findWorkByMember(Member member);

    List<Work> findByCategory(String category);

    List<Work> findTop5ByStatusAndMemberEqualsOrderByCreatedDateDesc(String status, Member member);

    // 다음 10개 불러오기(카테고리:전체, sort:최신순)
    List<Work> findTop5ByStatusAndMemberAndCreatedDateLessThanOrderByCreatedDateDesc(String status, Member member, LocalDateTime createdDate);

    // 초기 10개 불러오기(카테고리:전체, sort:최신순)
    List<Work> findTop10ByStatusEqualsOrderByCreatedDateDesc(String status);

    // 다음 10개 불러오기(카테고리:전체, sort:최신순)
    List<Work> findTop10ByStatusAndCreatedDateLessThanOrderByCreatedDateDesc(String status, LocalDateTime createdDate);

    // 초기 10개 불러오기(카테고리:넘어온 값 , sort:최신순)
    List<Work> findTop10ByStatusAndCategoryOrderByCreatedDateDesc(String status, String category);

    // 다음 10개 불러오기(카테고리:넘어온 값 , sort:최신순)
    List<Work> findTop10ByStatusAndCategoryAndCreatedDateLessThanOrderByCreatedDateDesc(String status, String category, LocalDateTime createdDate);

    // 초기 10개 불러오기(카테고리:전체, sort:조회순)
    List<Work> findTop10ByStatusEqualsOrderByHitCntDescCreatedDateDesc(String status);

    // 초기 10개 불러오기(카테고리:전체, sort:스크랩순)
    List<Work> findTop10ByStatusEqualsOrderByScrapCntDescCreatedDateDesc(String status);

    // 다음 10개 불러오기(카테고리:전체, sort:조회순)
    List<Work> findTop10ByStatusAndCreatedDateLessThanOrderByHitCntDescCreatedDateDesc(String status, LocalDateTime createdDate);

    // 다음 10개 불러오기(카테고리:전체, sort:스크랩순)
    List<Work> findTop10ByStatusAndCreatedDateLessThanOrderByScrapCntDescCreatedDateDesc(String status, LocalDateTime createdDate);

    // 초기 10개 불러오기(카테고리:넘어온 값 , sort:조회순)
    List<Work> findTop10ByStatusAndCategoryOrderByScrapCntDescCreatedDateDesc(String status, String category);

    // 초기 10개 불러오기(카테고리:넘어온 값 , sort:스크랩순)
    List<Work> findTop10ByStatusAndCategoryOrderByHitCntDescCreatedDateDesc(String status, String category);

    // 다음 10개 불러오기(카테고리:넘어온 값 , sort:조회순)
    List<Work> findTop10ByStatusAndCategoryAndCreatedDateLessThanOrderByHitCntDescCreatedDateDesc(String status, String category, LocalDateTime createdDate);

    // 다음 10개 불러오기(카테고리:넘어온 값 , sort:스크랩순)
    List<Work> findTop10ByStatusAndCategoryAndCreatedDateLessThanOrderByScrapCntDescCreatedDateDesc(String status, String category, LocalDateTime createdDate);

    List<Work> findTop10ByStatusAndTitleContainingOrderByCreatedDateDesc(String status, String keyword);
    List<Work> findTop10ByStatusAndCreatedDateLessThanAndTitleContainingOrderByCreatedDateDesc(String status, LocalDateTime createdDate, String keyword);

    List<Work> findByMember(Member member);

    @Modifying
    @Query("select wo from Work wo where wo.member = :member and wo.status = :status")
    List<Work> findByMemberAndStatus (@Param("member") Member member, @Param("status") String status);
}
