package A4.ShareHand.web.controller;

import A4.ShareHand.domain.dto.myPageDTO.*;
import A4.ShareHand.web.config.auth.PrincipalDetails;
import A4.ShareHand.web.service.MyPageService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/my-page/preview")
    public MyPagePreviewDTO getMyPagePreview(Authentication authentication) {
        String email = getEmail(authentication);
        return myPageService.getPreview(email);
    }


    @GetMapping("/my-page")
    public MyPageDTO getMyPageMemberDetails(Authentication authentication) {
        String email = getEmail(authentication);
        MyPageDTO myPageDTO = myPageService.getMyPageMemberDetails(email);
        return myPageDTO;
    }

    @GetMapping("/my-page/recruit-list")
    public myRecruitReturnerDTO getMyRecruit(Authentication authentication,
                                             @RequestParam(required = false) String last) { // 내가 작성한 글 불러오기
        String email = getEmail(authentication);
        return myPageService.getMyRecruit(email, last);
    }

    @GetMapping("/my-page/apply-list")
    public myApplyReturnerDTO getMyApply(Authentication authentication,
                                         @RequestParam(required = false) String last) {
        String email = getEmail(authentication);
        return myPageService.geMyApply(email, last);
    }

    @GetMapping("/my-page/participate-list")
    public myParticipateReturnerDTO getMyParticipated(Authentication authentication,
                                                      @RequestParam(required = false) String last) {
        String email = getEmail(authentication);
        return myPageService.getMyParticipate(email, last);
    }

    @GetMapping("/my-page/scrap-list")
    public myScrapReturnerDTO getMyScrap(Authentication authentication,
                                                @RequestParam(required = false) String last) {
        String email = getEmail(authentication);
        return myPageService.getMyScrap(email, last);
    }

    public String getEmail(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return principal.getEmail();

    }
}
