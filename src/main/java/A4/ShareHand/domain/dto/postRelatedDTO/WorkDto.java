package A4.ShareHand.domain.dto.postRelatedDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkDto {

    private long workCounter;
    private List<WorkListDto> workList;

}