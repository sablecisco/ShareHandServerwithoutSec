package A4.ShareHand.domain.dto.calanderRelatedDTO;

import lombok.Data;

@Data
public class DetailWorkDTOForDaily {

    private long workId;
    private String workName;
    private String nickname;
    private String location;
    private String startTime;
    private String endTime;

    public DetailWorkDTOForDaily(long workId, String workName, String nickname, String location, String startTime, String endTime) {
        this.workId = workId;
        this.workName = workName;
        this.nickname = nickname;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
