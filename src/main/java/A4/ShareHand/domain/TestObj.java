package A4.ShareHand.domain;

import A4.ShareHand.domain.dto.postRelatedDTO.ImageTestBody;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Data
public class TestObj {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private int age;
    private int test;

    public TestObj(ImageTestBody imageTestBody) {
        this.name = imageTestBody.getName();
        this.age = imageTestBody.getAge();
    }
}
