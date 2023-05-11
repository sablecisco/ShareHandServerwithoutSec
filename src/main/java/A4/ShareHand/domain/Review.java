package A4.ShareHand.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "Review")
public class Review extends JpaBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewId")
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workId")
    private Work work;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @Column(name = "rate1")
    private int rate1;

    @Column(name = "rate2")
    private int rate2;

    @Column(name = "rate3")
    private int rate3;

    @Column(name = "content")
    private String content;

    @Builder
    public Review(Work work, Member member, int rate1, int rate2, int rate3, String content) {
        this.work = work;
        this.member = member;
        this.rate1 = rate1;
        this.rate2 = rate2;
        this.rate3 = rate3;
        this.content = content;
    }
}