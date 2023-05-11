package A4.ShareHand.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "WorkManage")
public class WorkManage extends JpaBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WorkManageId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "WorkId")
    private Work work;

    @ElementCollection
    private List<String> applicant = new ArrayList<>();

    @ElementCollection
    private List<String> recommend = new ArrayList<>();

}
