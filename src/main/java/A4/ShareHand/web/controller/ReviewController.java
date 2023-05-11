package A4.ShareHand.web.controller;

import A4.ShareHand.domain.dto.postRelatedDTO.ReviewDTO;
import A4.ShareHand.domain.dto.postRelatedDTO.ReviewResponseDto;
import A4.ShareHand.web.config.auth.PrincipalDetails;
import A4.ShareHand.web.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/review/all")
    public ReviewResponseDto getAllReviews(Authentication authentication
            , @RequestParam("workId") long workId
            , @RequestParam(value = "last", required = false) Long last) {

        String email = getEmail(authentication);
        ReviewResponseDto reviewResponseDto;

        if(last == null){
            reviewResponseDto = reviewService.getLatestReviews(workId, email);
        } else {
            reviewResponseDto = reviewService.getNextReviews(workId, last, email);
        }
        return reviewResponseDto;
    }

    @PostMapping("/review/new-review")
    public int createReview(Authentication authentication, @RequestBody ReviewDTO reviewDTO, @RequestParam("workId") long workId) {
        String email = getEmail(authentication);
        if(!reviewService.createReview(reviewDTO, workId, email)){
            return 400;
        }
        return 200;
    }

    // 확인 필요
    @PutMapping("/service/{workId}/applicants/{userId}")
    public int giveReviewPermission(Authentication authentication, @PathVariable Long workId, @PathVariable Long userId){
        String email = getEmail(authentication);
        if(!reviewService.giveReviewPermission(email, workId, userId)){
            return 400;
        }
        return 200;
    }

    public String getEmail(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return principal.getEmail();
    }
}
