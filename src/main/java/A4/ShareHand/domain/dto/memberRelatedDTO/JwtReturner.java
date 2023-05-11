package A4.ShareHand.domain.dto.memberRelatedDTO;

import lombok.Data;

@Data
public class JwtReturner {

    private String accessToken;
    private String email;

    public JwtReturner(String accessToken, String email) {
        this.accessToken = accessToken;
        this.email = email;
    }
}
