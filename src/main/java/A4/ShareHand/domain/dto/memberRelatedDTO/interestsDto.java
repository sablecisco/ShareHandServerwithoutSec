package A4.ShareHand.domain.dto.memberRelatedDTO;

import lombok.Data;

import java.util.List;

@Data
public class interestsDto {

    private String email;
    private List<String> interests;
}
