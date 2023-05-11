package A4.ShareHand.domain.dto.myPageDTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.util.List;



@Data
@RequiredArgsConstructor
public class MyPageDTO {

    private String email;
    private String name;
    private String nickname;
    private String dob;
    private String location;
    private List<String> interests;
    private String profileUrl;
    private String tel;

    // 나중에 직접 코드 짜 넣어야함
    private long level = 1;
    private int levelPercent = 5;
    private int reviewPercent = 5;
    private int participate = 20;
    private int review = 17;
    private double reviewScore = 4.9;
    private int recruit = 1;
    private int volunteer = 3;
    private int complete = 2;


    public MyPageDTO(String email, String name, String nickname, String dob, String location, List<String> interests, String url, String tel) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.dob = dob;
        this.location = location;
        this.interests = interests;
        this.profileUrl = url;
        this.tel = tel;
    }
}
