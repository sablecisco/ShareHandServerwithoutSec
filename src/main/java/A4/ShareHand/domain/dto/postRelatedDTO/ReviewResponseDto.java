package A4.ShareHand.domain.dto.postRelatedDTO;

import A4.ShareHand.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class ReviewResponseDto {

    private long reviewAmount;
    private String workTitle;

    private double rateAvg;
    private double rate1Avg;
    private double rate2Avg;
    private double rate3Avg;

    private List<ReviewListDto> reviewLists;

    public void calculateAverages(List<Review>allReviewList) {
        if (allReviewList == null || allReviewList.isEmpty()) {
            rateAvg = 0;
            rate1Avg = 0;
            rate2Avg = 0;
            rate3Avg = 0;
            return;
        }

        int totalRate1 = 0;
        int totalRate2 = 0;
        int totalRate3 = 0;

        for (Review review : allReviewList) {
            totalRate1 += review.getRate1();
            totalRate2 += review.getRate2();
            totalRate3 += review.getRate3();
        }

        int reviewSize = allReviewList.size();

        rate1Avg = totalRate1 / reviewSize;
        rate2Avg = totalRate2 / reviewSize;
        rate3Avg = totalRate3 / reviewSize;
        rateAvg = (rate1Avg+rate2Avg+rate3Avg) / 3;
    }
}
