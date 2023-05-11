package A4.ShareHand.domain.dto.myPageDTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class myScrapReturnerDTO {
    private long serviceCounter;
    List<serviceObject> serviceList;
    private long lastScrapId;

    public myScrapReturnerDTO(long serviceCounter, List<serviceObject> serviceList, long lastScrapId) {
        this.serviceCounter = serviceCounter;
        this.serviceList = serviceList;
        this.lastScrapId = lastScrapId;
    }
}
