package A4.ShareHand.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "Works")
@RequiredArgsConstructor
public class Work extends JpaBaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "workId")
    private long workId;

    @ManyToOne
    @JoinColumn(name = "memberId", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @Column(name = "category")
    private String category;

    @Column(name = "title")
    private String title;

    @Column(name = "introduce")
    private String introduce;

    @Column(name = "applyDeadline")
    private String applyDeadline;

    @Column(name = "area")
    private String area;

    @Column(name = "startDate")
    private String startDate;

    @Column(name = "endDate")
    private String endDate;

    @Column(name = "dow")
    private String dow;

    @Column(name = "startTime")
    private String startTime;

    @Column(name = "endTime")
    private String endTime;

    @Column(name = "recruitNum")
    private int recruitNum;

    @Column(name = "cost")
    private int cost;

    @Column(length = 10000)
    private String content;

    @Column(name = "extra")
    private String extra;

    @Column(name = "email")
    private String email;

    @Column(name = "tel")
    private String tel;

    @Column(name = "topImage")
    private String topImage;

    // 여기서부터는 DB에서만 관리하는 영역

    @Column(name = "hitCnt")
    private long hitCnt;

    @Column(name = "scrapCnt")
    private long scrapCnt;

    @Column(name = "likeCnt")
    private long likeCnt;

    private String status;

    @Builder
    public Work(Member member, String category, String title, String introduce,
                String applyDeadline, String area, String startDate, String endDate, String dow,
                String startTime, String endTime, int recruitNum, int cost,
                String content, String extra, String email, String tel, String status) {
        this.member = member;
        this.category = category;
        this.title = title;
        this.introduce = introduce;
        this.applyDeadline = applyDeadline;
        this.area = area;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dow = dow;
        this.startTime = startTime;
        this.endTime = endTime;
        this.recruitNum = recruitNum;
        this.cost = cost;
        this.content = content;
        this.extra = extra;
        this.email = email;
        this.tel = tel;
        this.status = status;
    }

    public void plusHitCnt() { this.hitCnt+=1; }

    public void plusLikeCnt(){
        this.likeCnt+=1;
    }

    public void minusLikeCnt(){
        this.likeCnt-=1;
    }

    public void plusScrapCnt(){this.scrapCnt+=1; }

    public void minusScrapCnt(){
        this.scrapCnt-=1;
    }

    public void setTopImage(String topImage) {
        this.topImage = topImage;
    }

    public void changeStatus() {
        this.status = "DeActive";
    }
}