package A4.ShareHand.domain.dto.myPageDTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class myRecruitReturnerDTO {
    private long serviceCounter;
    List<serviceObject> serviceList;
    private long lastWorkId;

    public myRecruitReturnerDTO(long serviceCounter, List<serviceObject> serviceList, long lastWorkId) {
        this.serviceCounter = serviceCounter;
        this.serviceList = serviceList;
        this.lastWorkId = lastWorkId;
    }
}
