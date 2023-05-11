package A4.ShareHand.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Invitation")
@RequiredArgsConstructor
@Getter
public class Invitation extends JpaBaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invitationId")
    private long id;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @Column(name = "status")
    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "workId")
    private Work work;
}
