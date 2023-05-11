package A4.ShareHand.domain;

import A4.ShareHand.domain.dto.MemberFixDetailDTO;
import A4.ShareHand.domain.dto.memberRelatedDTO.MemberDetailsDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@Getter
@Table(name = "member_details")

@RequiredArgsConstructor
public class MemberDetails extends JpaBaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "memberDetailId")
    private long memberDetailId;

    @Column(name = "nickname", unique = true)
    private String nickname;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "dob")
    private String dob;

    @Column(name = "tel")
    private String tel;

//    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "member_ID")
//    private Member member;

    @Column(name = "interests")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> interests;

    public void setinterests(List<String> interests) {
        this.interests = interests;
    }

    public void setContent(MemberFixDetailDTO memberDetailsDto) {
        this.nickname = memberDetailsDto.getNickname();
        this.location = memberDetailsDto.getLocation();
        this.tel = memberDetailsDto.getTel();
        this.interests = memberDetailsDto.getInterests();
    }

    public MemberDetails(MemberDetailsDto memberDetailsDto) {
        this.nickname = memberDetailsDto.getNickname();
        this.location = memberDetailsDto.getLocation();
        this.tel = memberDetailsDto.getTel();
    }

    @Builder
    public MemberDetails(String nickname, String name, String location, String dob, String tel) {
        this.nickname = nickname;
        this.name = name;
        this.location = location;
        this.dob = dob;
        this.tel = tel;
    }





}