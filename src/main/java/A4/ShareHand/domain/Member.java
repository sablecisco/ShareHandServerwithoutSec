package A4.ShareHand.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "Members")
@Getter
@RequiredArgsConstructor
public class Member extends JpaBaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "memberId")
    private long id;

    @Column(name = "email")
    private String email;

    @Column(name = "oauth2")
    private String oauth2;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "member_details_ID")
    private MemberDetails memberDetails;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ProfilePhotoData")
    private ProfilePhotoData profilePhotoData;

    public void updateMemberDetail(MemberDetails memberDetails) {
        this.memberDetails = memberDetails;
    }

    @Builder
    public Member(String email, String oauth2, MemberDetails memberDetails,  ProfilePhotoData profilePhotoData) {
        this.email = email;
        this.oauth2 = oauth2;
        this.memberDetails = memberDetails;
        this.profilePhotoData = profilePhotoData;

    }
}
