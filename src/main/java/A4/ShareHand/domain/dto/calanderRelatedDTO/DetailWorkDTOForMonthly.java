package A4.ShareHand.domain.dto.calanderRelatedDTO;

import lombok.Data;

@Data
public class DetailWorkDTOForMonthly {

    private long workId;
    private String workName;
    private String location;
    private int dDay;
    private String dow;
    private String time;

    public DetailWorkDTOForMonthly(long workId, String workName, String location, int dDay, String dow, String time) {
        this.workId = workId;
        this.workName = workName;
        this.location = location;
        this.dDay = dDay;
        this.dow = dow;
        this.time = time;
    }
}
