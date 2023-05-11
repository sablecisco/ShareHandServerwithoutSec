package A4.ShareHand.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Getter
@Table(name = "WorkScrap")
public class WorkScrap extends JpaBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WorkScrapId")
    private long workScrapId;

    @ManyToOne
    @JoinColumn(name = "WorkId")
    private Work work;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @Builder
    public WorkScrap(Work work, Member member) {
        this.work = work;
        this.member = member;
    }
}
