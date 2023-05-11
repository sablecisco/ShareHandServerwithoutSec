package A4.ShareHand.web.service;

import A4.ShareHand.domain.Member;
import A4.ShareHand.domain.MemberDetails;
import A4.ShareHand.domain.Work;
import A4.ShareHand.domain.dto.MemberFixDetailDTO;
import A4.ShareHand.domain.dto.ProfileDto;
import A4.ShareHand.web.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberDetailsRepository memberDetailsRepository;
    private final WorkRepository workRepository;
    private final WorkApplyRepository workApplyRepository;
    private final ProfilePhotoDataRepository profilePhotoDataRepository;

    @Transactional
    public void updateMemberDetails(MemberFixDetailDTO memberDetailsDto, String email) {
        Member member = memberRepository.findByEmail(email);
        MemberDetails memberDetails = member.getMemberDetails();
        memberDetails.setContent(memberDetailsDto);
    }

    @Transactional
    public Member updateMemberDetailsInterests(List<String> interests, String email) {
        Member member = memberRepository.findByEmail(email);
        MemberDetails memberDetails = member.getMemberDetails();
        memberDetails.setinterests(interests);
        // 검증로직 필요
        return member;
    }

    public boolean checkForMemberDetails(Member member) {
        MemberDetails memberDetails = member.getMemberDetails();
        if (memberDetails != null) {
            return true;
        }
        return false;
    }

    public boolean checkInterests(Member member) {
        MemberDetails memberDetails = member.getMemberDetails();
        List<String> interests = memberDetails.getInterests();

        try {
            String s = interests.get(0);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Transactional
    public MemberDetails getMemberDetails(Member member) {
        return member.getMemberDetails();
    }

    @Transactional
    public Member firstUpdateMemberDetails(String email, MemberDetails newmemberDetails) {
        Member member = memberRepository.findByEmail(email);
        MemberDetails memberDetails = memberDetailsRepository.save(newmemberDetails); // MemberDetails 저장
        member.updateMemberDetail(memberDetails); // memberUpdate
        return member;
        // 검증로직 필요
    }

    @Transactional
    public ProfileDto getProfile(String email, long userId) {
        Member member = memberRepository.findByEmail(email); //로그인한 유저
        Member user = memberRepository.findById(userId);

        int managedWorkCnt = workRepository.countByMember(user);
        int appliedWorkCnt = workApplyRepository.countByMember(user);
        int participatedWorkCnt = workApplyRepository.countByMemberAndReviewPermissionEquals(user, true);

        ProfileDto profileDto = ProfileDto.builder()
                .nickname(user.getMemberDetails().getNickname())
                .level(1)
                .avgRate(5.0)
                .location(user.getMemberDetails().getLocation())
                .managedWork(managedWorkCnt)
                .appliedWork(appliedWorkCnt)
                .participatedWork(participatedWorkCnt)
                .isAuthor(user==member)
                .profileUrl(user.getProfilePhotoData().getUrl())
                .build();

        return profileDto;
    }

    @Transactional
    public int withdrawForever(String email) {
        Member member = memberRepository.findByEmail(email);
        long memberId = member.getId();

        List<Work> writtenByMe = workRepository.findByMember(member);
        if (writtenByMe != null) {
            for (Work work : writtenByMe) {
                work.changeStatus();
            }
        }
        memberRepository.deleteById(memberId);
        memberDetailsRepository.deleteById(member.getMemberDetails().getMemberDetailId());
        profilePhotoDataRepository.deleteById((int) member.getProfilePhotoData().getId());

        return 200;
//        Optional<Member> byEmailOptional = memberRepository.findByEmailOptional(email);
//
//        if(!byEmailOptional.isPresent()) {
//            return 200;
//        } else {
//            return 400;
//        }
    }
}

