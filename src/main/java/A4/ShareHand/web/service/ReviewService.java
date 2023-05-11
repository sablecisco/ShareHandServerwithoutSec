package A4.ShareHand.web.service;

import A4.ShareHand.domain.Member;
import A4.ShareHand.domain.Review;
import A4.ShareHand.domain.WorkApply;
import A4.ShareHand.domain.Work;
import A4.ShareHand.domain.dto.postRelatedDTO.ReviewDTO;
import A4.ShareHand.domain.dto.postRelatedDTO.ReviewListDto;
import A4.ShareHand.domain.dto.postRelatedDTO.ReviewResponseDto;
import A4.ShareHand.web.exception.WorkNotFoundException;
import A4.ShareHand.web.repository.MemberRepository;
import A4.ShareHand.web.repository.ReviewRepository;
import A4.ShareHand.web.repository.WorkApplyRepository;
import A4.ShareHand.web.repository.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final WorkRepository workRepository;
    private final MemberRepository memberRepository;
    private final WorkApplyRepository workApplyRepository;

    @Transactional
    public boolean createReview(ReviewDTO reviewDTO, long workId, String email) {
        Optional<Work> optionalWork = workRepository.findById(workId);
        if(!optionalWork.isPresent()){
            return false;
        }
        Work work = optionalWork.get();
        Member member = memberRepository.findByEmail(email);

        Optional<WorkApply> workApply = workApplyRepository.findByWorkAndMember(work, member);
        if (!workApply.isPresent() || workApply.get().isReviewPermission() == false){
            return false;
        }

        Review review = Review.builder()
                .member(member)
                .work(work)
                .content(reviewDTO.getContent())
                .rate1(reviewDTO.getRate1())
                .rate2(reviewDTO.getRate2())
                .rate3(reviewDTO.getRate3())
                .build();

        reviewRepository.save(review);
        return true;
    }

    @Transactional
    public boolean giveReviewPermission(String email, Long workId, Long userId) {
        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new WorkNotFoundException("해당 모집글 정보가 없습니다."));
        Member member = memberRepository.findByEmail(email);

        // 현재 로그인한 유저가 모집글 작성자가 아닐경우
        if(work.getMember()!=member){
            return false;
        }

        Member applicant = memberRepository.findById(userId);

        Optional<WorkApply> optionalWorkApply = workApplyRepository.findByWorkAndMember(work, applicant);

        //지원한 적이 없거나 이미 리뷰 허용 권한을 주었을 때
        if (!optionalWorkApply.isPresent() || optionalWorkApply.get().isReviewPermission() == true){
            return false;
        }

        WorkApply workApply = optionalWorkApply.get();
        workApply.updateReviewPermission(true);

        return true;
    }

    @Transactional
    public ReviewResponseDto getLatestReviews(long workId, String email) {
        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new WorkNotFoundException("해당 모집글 정보가 없습니다."));

        Member member = memberRepository.findByEmail(email);
        List<Review> allReviewList = reviewRepository.findByWork(work);
        List<Review> reviewList = reviewRepository.findTop10ByOrderByCreatedDateDesc();

        return getReviewLists(member, reviewList, work, allReviewList);
    }

    @Transactional
    public ReviewResponseDto getNextReviews(long workId, Long last, String email) {
        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new WorkNotFoundException("해당 모집글 정보가 없습니다."));

        Member member = memberRepository.findByEmail(email);
        List<Review> allReviewList = reviewRepository.findByWork(work);

        Review lastReview = reviewRepository.findById(last).orElse(null);
        if (lastReview == null){
            return getLatestReviews(workId, email);
        }
        List<Review> reviewList = reviewRepository.findTop10ByCreatedDateLessThanOrderByCreatedDateDesc(lastReview.getCreatedDate());

        return getReviewLists(member, reviewList, work, allReviewList);
    }

    private ReviewResponseDto getReviewLists(Member member, List<Review> reviewList, Work work, List<Review> allReviewLists) {
        List<ReviewListDto> reviewLists = new ArrayList<>();

        reviewList.stream().forEach((e) -> {
            reviewLists.add(ReviewListDto.of(e, work, member));
        });

        ReviewResponseDto reviewResponseDto = ReviewResponseDto.builder()
                .reviewAmount(allReviewLists.size())
                .workTitle(work.getTitle())
                .reviewLists(reviewLists)
                .build();

        reviewResponseDto.calculateAverages(allReviewLists);

        return reviewResponseDto;
    }
}
