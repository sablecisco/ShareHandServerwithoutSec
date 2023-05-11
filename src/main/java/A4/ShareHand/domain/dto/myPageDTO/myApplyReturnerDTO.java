package A4.ShareHand.domain.dto.myPageDTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class myApplyReturnerDTO {
    private long serviceCounter;
    List<serviceObject> serviceList;
    private Long lastApplyId;

    public myApplyReturnerDTO(long serviceCounter, List<serviceObject> serviceList, long lastApplyId) {
        this.serviceCounter = serviceCounter;
        this.serviceList = serviceList;
        this.lastApplyId = lastApplyId;
    }
}
