package A4.ShareHand.web.controller;

import A4.ShareHand.web.config.auth.PrincipalDetails;
import A4.ShareHand.web.service.ManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ManagementController {

    private final ManagementService managementService;

    @PostMapping("/manage/scrap")
    public long scrapWork(Authentication authentication, long workId) {
        String email = getEmail(authentication);
        return managementService.saveScrap(email, workId);
    }

    @PostMapping("/manage/unscrap")
    public long unScrapWork(Authentication authentication, long workId) {
        String email = getEmail(authentication);
        return  managementService.unScrap(email, workId);
    }

    @PostMapping("/manage/like")
    public long likeWork(Authentication authentication, long workId) {
        String email = getEmail(authentication);
        return managementService.saveLike(email, workId);


    }

    @PostMapping("/manage/unlike")
    public long unLikeWork(Authentication authentication, long workId) {
        String email = getEmail(authentication);
        return managementService.unLike(email, workId);
    }



    public String getEmail(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return principal.getEmail();
    }
}
