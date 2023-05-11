package A4.ShareHand.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "PhotoMetaData")
@RequiredArgsConstructor @Getter
public class PhotoMetaData extends JpaBaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imageId")
    private Long imageId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "workId")
    private Work work;

    @Column(name = "url")
    private String url;

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "uploadOrder")
    private long uploadOrder;

    public PhotoMetaData(Work work, String url, long uploadOrder, String fileName) {
        this.work = work;
        this.url = url;
        this.uploadOrder = uploadOrder;
        this.fileName = fileName;
    }
}
