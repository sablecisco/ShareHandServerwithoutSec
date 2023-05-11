package A4.ShareHand.web.controller;

import A4.ShareHand.domain.dto.MemberFixDetailDTO;
import A4.ShareHand.domain.dto.ProfileDto;
import A4.ShareHand.web.config.auth.PrincipalDetails;
import A4.ShareHand.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/my-page/edit")
    public int MemberEdit(Authentication authentication, @RequestBody MemberFixDetailDTO memberDetailsDto) {
        String email = getEmail(authentication);
        memberService.updateMemberDetails(memberDetailsDto, email);
        return 200; // 오류분기처리 안함
    }

    @PostMapping("/my-page/withdraw")
    public String withdraw(Authentication authentication) {
        String email = getEmail(authentication);
        int response = memberService.withdrawForever(email);
        if(response == 200) {
            return "Deleted";
        } else {
            return "failed";
        }
    }

    @GetMapping("/profile")
    public ProfileDto getProfile(Authentication authentication, @RequestParam("userId") long userId){
        String email = getEmail(authentication);
        return memberService.getProfile(email, userId);
    }

    public String getEmail(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return principal.getEmail();
    }
}
