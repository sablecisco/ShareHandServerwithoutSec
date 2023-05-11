package A4.ShareHand.domain.dto.calanderRelatedDTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class TodayWorkReturnerDTO {

    private int workCounter;
    private List<SimpleWorkDTO> workList;

    public TodayWorkReturnerDTO(int workCounter, List<SimpleWorkDTO> workList) {
        this.workCounter = workCounter;
        this.workList = workList;
    }
}
