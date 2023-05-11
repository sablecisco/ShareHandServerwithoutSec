package A4.ShareHand.domain.dto.postRelatedDTO;

import A4.ShareHand.domain.Member;
import A4.ShareHand.domain.Review;
import A4.ShareHand.domain.Work;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class ReviewListDto {

    private long workId;
    private String memberName;
    private String profileUrl;
    private long level;
    private double rateAvg;
    private String content;
    private String updatedDate;
    private boolean isAuthor;

    public static ReviewListDto of(Review e, Work work, Member member) {

        return ReviewListDto.builder()
                .workId(work.getWorkId())
                .memberName(e.getMember().getMemberDetails().getName())
                .profileUrl(e.getMember().getProfilePhotoData().getUrl())
                .level(1)
                .rateAvg((e.getRate1()+e.getRate2()+e.getRate3())/3)
                .content(e.getContent())
                .updatedDate(e.getUpdatedDate().toString())
                .isAuthor(member == e.getMember())
                .build();
    }
}

