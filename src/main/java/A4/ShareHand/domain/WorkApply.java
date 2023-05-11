package A4.ShareHand.domain;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "WorkApply")
public class WorkApply extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WorkApplyId")
    private Long WorkApplyId;

    @ManyToOne
    @JoinColumn(name = "WorkId")
    private Work work;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @Column(name = "reviewPermission")
    private boolean reviewPermission;

    @Builder
    public WorkApply(Work work, Member member, boolean reviewPermission) {
        this.work = work;
        this.member = member;
        this.reviewPermission = reviewPermission;
    }
    public void updateReviewPermission(boolean permission){
        this.reviewPermission = permission;
    }

}
