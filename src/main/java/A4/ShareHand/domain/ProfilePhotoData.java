package A4.ShareHand.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Setter @Getter
@RequiredArgsConstructor
public class ProfilePhotoData extends JpaBaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "imageId")
    private long id;

    @Column(name = "url")
    private String url;

//    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "member_ID")
//    private Member member;

    @Builder
    public ProfilePhotoData(String url) {
        this.url = url;
    }
}
