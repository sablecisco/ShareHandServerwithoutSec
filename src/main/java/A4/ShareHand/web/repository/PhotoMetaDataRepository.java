package A4.ShareHand.web.repository;

import A4.ShareHand.domain.PhotoMetaData;
import A4.ShareHand.domain.Work;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoMetaDataRepository extends JpaRepository<PhotoMetaData, Integer> {

    List<PhotoMetaData> findByWork(Work work);
}
