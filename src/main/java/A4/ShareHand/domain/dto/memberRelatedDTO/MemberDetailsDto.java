package A4.ShareHand.domain.dto.memberRelatedDTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class MemberDetailsDto {

    private String email;
    private String name;
    private String nickname;
    private String dob;
    private String location;
    private String tel;
}
