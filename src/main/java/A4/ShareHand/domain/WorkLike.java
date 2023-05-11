package A4.ShareHand.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@RequiredArgsConstructor
@Getter
@Table(name = "WorkLike")
public class WorkLike extends  JpaBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WorkLikeId")
    private long workLikeId;

    @ManyToOne
    @JoinColumn(name = "WorkId")
    private Work work;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @Builder
    public WorkLike(Work work, Member member){
        this.work = work;
        this.member = member;
    }
}
