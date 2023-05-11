package A4.ShareHand.domain.dto.postRelatedDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    private String content;
    private int rate1;
    private int rate2;
    private int rate3;

}