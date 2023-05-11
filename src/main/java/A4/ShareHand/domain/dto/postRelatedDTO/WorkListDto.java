package A4.ShareHand.domain.dto.postRelatedDTO;

import A4.ShareHand.domain.Work;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkListDto {

    private long workId;
    private String imageUrl;
    private String profileUrl;
    private String nickname;
    //    private boolean userCertified;  //DB에 반영안되어있음
    private boolean userApplied;
    @JsonProperty("isAuthor")
    private boolean isAuthor;
    private String workName;
    private String location;
    private String maxNum;
    private String date;
    private String dow;


    public static WorkListDto of(Work e, boolean userApplied, boolean isAuthor) {
        return WorkListDto.builder()
                .imageUrl(e.getTopImage())
                .profileUrl(e.getMember().getProfilePhotoData().getUrl())
                .nickname(e.getMember().getMemberDetails().getNickname())
                .workName(e.getTitle())
                .location(e.getArea())
                .maxNum(String.valueOf(e.getRecruitNum()))
                .date(e.getStartDate() + "-" + e.getEndDate())
                .dow(e.getDow())
                .workId(e.getWorkId())
                .userApplied(userApplied)
                .isAuthor(isAuthor)
                .build();
    }
}