package A4.ShareHand.web.repository;

import A4.ShareHand.domain.ProfilePhotoData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfilePhotoDataRepository extends JpaRepository<ProfilePhotoData, Integer> {

    @Modifying
    @Query("delete from ProfilePhotoData m where m.id=:id")
    void deleteById(@Param("id") long id);
}
