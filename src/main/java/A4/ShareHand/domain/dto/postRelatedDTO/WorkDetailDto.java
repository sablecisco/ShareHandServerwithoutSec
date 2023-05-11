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
public class WorkDetailDto {

    private long userId;
    private String nickname;
    private String profileUrl;
    private long level;
    private double userRate;
    private String workTitle;
    private List<String> photoList;
    private String category;
    private String intro;
    private String applydeadline;
    private String area;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private String dow;
    private int recruitNum;
    private int cost;
    private String content;
    private long hitCnt;
    private long scrapCnt;
    private long likeCnt;
    private String tel;
    private String email;
    private String contactEtc;
    private boolean isAuthor;
    private boolean didApply;
    private boolean didLike;
    private boolean didScrap;
    private boolean status;


    private List<ReviewListDto> reviewLists;

}