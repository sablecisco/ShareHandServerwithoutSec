package A4.ShareHand.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "Report")
public class Report extends JpaBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reportId")
    private long reportId;

    @Column(name = "reportedId")
    private long reportedId;

    @Column(name = "postType")
    private String postType;

    @Column(name = "reportType")
    private String reportType;

    @Column(name = "reportDetail")
    private String reportDetail;
}