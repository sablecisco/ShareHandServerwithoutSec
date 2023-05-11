package A4.ShareHand.domain.dto.calanderRelatedDTO;

import lombok.Data;

@Data
public class SimpleWorkDTO {

    private String time;
    private String workName;
    private String location;

    public SimpleWorkDTO(String time, String workName, String location) {
        this.time = time;
        this.workName = workName;
        this.location = location;
    }
}
