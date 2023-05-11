package A4.ShareHand.domain.dto.calanderRelatedDTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class MonthlyWorkReturnerDTO {

    private int workCounter;
    private List<DetailWorkDTOForMonthly> workList;

    public MonthlyWorkReturnerDTO(int workCounter, List<DetailWorkDTOForMonthly> workList) {
        this.workCounter = workCounter;
        this.workList = workList;
    }
}
