package A4.ShareHand.domain.dto.calanderRelatedDTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class DailyWorkReturnerDTO {

    private int workCounter;
    private List<DetailWorkDTOForDaily> workList;

    public DailyWorkReturnerDTO(int workCounter, List<DetailWorkDTOForDaily> workList) {
        this.workCounter = workCounter;
        this.workList = workList;
    }
}
