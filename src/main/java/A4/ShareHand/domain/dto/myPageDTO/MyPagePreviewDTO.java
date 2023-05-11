package A4.ShareHand.domain.dto.myPageDTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class MyPagePreviewDTO {

    private String nickname;
    private long level = 3;
    private String profileUrl;

    public MyPagePreviewDTO(String nickname, String profileUrl) {
        this.nickname = nickname;
        this.profileUrl = profileUrl;
    }
}
