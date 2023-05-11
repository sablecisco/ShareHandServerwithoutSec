package A4.ShareHand.domain.dto.myPageDTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class serviceObject {

    private String imageUrl;
    private String profileUrl;
    private String nickname;
    private String serviceName;
    private String location;
    private String date;

    private long maxNum;
    private String dow;

    private long workId;
    private boolean userApplied;

    public serviceObject(String imageUrl, String profileUrl, String nickname, String serviceName, String location, String date, long maxNum, String dow, long workId, boolean userApplied) {
        this.imageUrl = imageUrl;
        this.profileUrl = profileUrl;
        this.nickname = nickname;
        this.serviceName = serviceName;
        this.location = location;
        this.date = date;
        this.maxNum = maxNum;
        this.dow = dow;
        this.workId = workId;
        this.userApplied = userApplied;
    }
}
