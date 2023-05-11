package A4.ShareHand.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class MemberFixDetailDTO {
    private String nickname;
    private String location;
    private String tel;
    private List<String> interests;
}
