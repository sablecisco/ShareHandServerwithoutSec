package A4.ShareHand.web.repository;

import A4.ShareHand.domain.Review;
import A4.ShareHand.domain.Work;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    long countBy();
    List<Review> findByWork(Work work);
    List<Review> findTop3ByOrderByCreatedDateDesc();
    List<Review> findTop10ByOrderByCreatedDateDesc();
    List<Review> findTop10ByCreatedDateLessThanOrderByCreatedDateDesc(LocalDateTime createdDate);


}
