package A4.ShareHand.web.controller;

import A4.ShareHand.domain.Member;
import A4.ShareHand.domain.MemberDetails;
import A4.ShareHand.domain.ProfilePhotoData;
import A4.ShareHand.domain.dto.memberRelatedDTO.interestsDto;
import A4.ShareHand.domain.dto.memberRelatedDTO.JwtReturner;
import A4.ShareHand.domain.dto.memberRelatedDTO.MemberDetailsDto;
import A4.ShareHand.web.config.jwt.CreateJwt;
import A4.ShareHand.web.repository.MemberRepository;
import A4.ShareHand.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OAuthJwtController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @PostMapping("/login/{serviceProvider}")
    public JwtReturner OauthLoginJwtReturner(@PathVariable String serviceProvider,
                                             @RequestParam String email, @RequestParam String profileUrl) {
        Member member = memberRepository.findByEmail(email); // check for user existence

        if (member == null) { // 완전 최초 접속일 때
            ProfilePhotoData profilePhotoData = ProfilePhotoData.builder().url(profileUrl).build();

            // Member Build
            Member newMember = Member.builder()
                    .email(email)
                    .oauth2(serviceProvider)
                    .profilePhotoData(profilePhotoData)
                    .build();

            member = memberRepository.save(newMember);
        }

        if(!memberService.checkForMemberDetails(member)) {
            return new JwtReturner("memberDetails", member.getEmail());
        }
        // 이 둘을 구분할게 필요할듯
        if (!memberService.checkInterests(member)) {
            return new JwtReturner("interests", member.getEmail());
        }

        String accessToken = CreateJwt.createAccessToken(member);

        return new JwtReturner(accessToken, member.getEmail());
    }

    @PostMapping("/user/data")
    public int getMemberDetailInfo(@RequestBody MemberDetailsDto memberDetailsDto) {
        MemberDetails newmemberDetails = MemberDetails.builder()
                .dob(memberDetailsDto.getDob())
                .location(memberDetailsDto.getLocation())
                .nickname(memberDetailsDto.getNickname())
                .name(memberDetailsDto.getName())
                .tel(memberDetailsDto.getTel())
                .build();

        Member member = memberService.firstUpdateMemberDetails(memberDetailsDto.getEmail(), newmemberDetails);

        if ((member.getMemberDetails() != null) && (member.getMemberDetails().getDob() != null)) {
            return 200;
        } else {
            return 400;
        }
    }

    @PostMapping("/user/data/interests") // 최종 로그인 처리
    public JwtReturner interests(@RequestBody interestsDto interestsDto) {
        List<String> interests = interestsDto.getInterests();

        String email = interestsDto.getEmail();
        Member member = memberService.updateMemberDetailsInterests(interests, email);

        String accessToken = CreateJwt.createAccessToken(member);
        return new JwtReturner(accessToken, member.getEmail());
    }
}
