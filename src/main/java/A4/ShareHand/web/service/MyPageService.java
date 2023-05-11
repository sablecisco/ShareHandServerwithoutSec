package A4.ShareHand.web.service;

import A4.ShareHand.domain.*;
import A4.ShareHand.domain.dto.myPageDTO.*;
import A4.ShareHand.web.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MyPageService{

    private final MemberRepository memberRepository;
    private final WorkApplyRepository workApplyRepository;
    private final WorkRepository workRepository;
    private final WorkScrapRepository workScrapRepository;

    @Transactional
    public MyPagePreviewDTO getPreview(String email) {
        Member member = memberRepository.findByEmail(email);

        String nickname = member.getMemberDetails().getNickname();
        String profileUrl = member.getProfilePhotoData().getUrl();
        return new MyPagePreviewDTO(nickname, profileUrl);
    }

    @Transactional
    public MyPageDTO getMyPageMemberDetails(String email){
        Member findEmail = memberRepository.findByEmail(email);
        MemberDetails memberDetails = findEmail.getMemberDetails();
        ProfilePhotoData profilePhotoData = findEmail.getProfilePhotoData();
        List<String>interests = memberDetails.getInterests();
        String url = profilePhotoData.getUrl();
        MyPageDTO mypagedto = new MyPageDTO(email, memberDetails.getName(), memberDetails.getNickname(), memberDetails.getDob(), memberDetails.getLocation(), interests, url, memberDetails.getTel());
        return mypagedto;
    }

    @Transactional
    public myRecruitReturnerDTO getMyRecruit(String email, String last) {
        List<serviceObject> serviceList = new ArrayList<>();

        Member member = memberRepository.findByEmail(email);
        String profileUrl = member.getProfilePhotoData().getUrl();
        String nickname = member.getMemberDetails().getNickname();

        int totalWork = workRepository.countByMember(member);
        List<Work> workList = new ArrayList<>();
        if (last == null) {
            workList = workRepository.findTop5ByStatusAndMemberEqualsOrderByCreatedDateDesc("Active", member);
        } else {
            Work lastWork = workRepository.findByWorkId(Long.parseLong(last));
            LocalDateTime createdDate = lastWork.getCreatedDate();
            workList = workRepository.findTop5ByStatusAndMemberAndCreatedDateLessThanOrderByCreatedDateDesc("Active", member, createdDate);
        }

        for (Work work : workList) {
            String date = work.getStartDate() + " ~ " + work.getEndDate();
            serviceObject serviceObject = new serviceObject(work.getTopImage(), profileUrl, nickname, work.getTitle(),
                    work.getArea(), date, work.getRecruitNum(), work.getDow(), work.getWorkId(), true);
            serviceList.add(serviceObject);
        }

        int size = workList.size();
        long lastWorkId = workList.get(size - 1).getWorkId();
        return new myRecruitReturnerDTO(totalWork, serviceList, lastWorkId);
    }

    @Transactional
    public myApplyReturnerDTO geMyApply(String email, String last) {
        List<serviceObject> serviceList = new ArrayList<>();

        Member member = memberRepository.findByEmail(email);
        String profileUrl = member.getProfilePhotoData().getUrl();
        String nickname = member.getMemberDetails().getNickname();

        List<WorkApply> workApplyList = new ArrayList<>();

        if (last == null) {
            workApplyList = workApplyRepository.findTop5ByMemberEqualsOrderByCreatedDateDesc(member);
        } else {
            LocalDateTime createdDate = workApplyRepository.findById(Long.parseLong(last)).get().getCreatedDate();
            workApplyList = workApplyRepository.findTop5ByMemberAndCreatedDateLessThanOrderByCreatedDateDesc(member, createdDate);
        }

        for (WorkApply workApply : workApplyList) {
            Work work = workApply.getWork();
            String date = work.getStartDate() + " ~ " + work.getEndDate();
            serviceObject serviceObject = new serviceObject(work.getTopImage(), profileUrl, nickname, work.getTitle(),
                    work.getArea(), date, work.getRecruitNum(), work.getDow(), work.getWorkId(), true);
            serviceList.add(serviceObject);
        }
        int size = workApplyList.size();
        long lastApplyId = workApplyList.get(size - 1).getWorkApplyId();

        int memberApply = workApplyRepository.countByMember(member);
        return new myApplyReturnerDTO(memberApply, serviceList, lastApplyId);
    }

    @Transactional
    public myParticipateReturnerDTO getMyParticipate(String email, String last) {
        List<serviceObject> serviceList = new ArrayList<>();

        Member member = memberRepository.findByEmail(email);
        String profileUrl = member.getProfilePhotoData().getUrl();
        String nickname = member.getMemberDetails().getNickname();

        List<WorkApply> workApplyList = new ArrayList<>();

        if (last == null) {
            workApplyList = workApplyRepository.findTop5ByMemberAndReviewPermissionEqualsOrderByCreatedDateDesc(member, true); // 이거 쿼리 속도 차이 있나
        } else {
            LocalDateTime createdDate = workApplyRepository.findById(Long.parseLong(last)).get().getCreatedDate();
            workApplyList = workApplyRepository.findTop5ByMemberAndReviewPermissionAndCreatedDateLessThanOrderByCreatedDateDesc(member, true, createdDate);
        }

        for (WorkApply workApply : workApplyList) {
            Work work = workApply.getWork();
            String date = work.getStartDate() + " ~ " + work.getEndDate();
            serviceObject serviceObject = new serviceObject(work.getTopImage(), profileUrl, nickname, work.getTitle(),
                    work.getArea(), date, work.getRecruitNum(), work.getDow(), work.getWorkId(), true);
            serviceList.add(serviceObject);
        }

        int size = workApplyList.size();
        long lastApplyId = workApplyList.get(size - 1).getWorkApplyId();

        int participated = workApplyRepository.countByMemberAndReviewPermissionEquals(member, true);
        return new myParticipateReturnerDTO(participated, serviceList, lastApplyId);
    }

    @Transactional
    public myScrapReturnerDTO getMyScrap(String email, String last) {
        List<serviceObject> serviceList = new ArrayList<>();
        List<WorkScrap> workScrapList = new ArrayList<>();

        Member member = memberRepository.findByEmail(email);
        String profileUrl = member.getProfilePhotoData().getUrl();
        String nickname = member.getMemberDetails().getNickname();
        int myScrap = workScrapRepository.countByMember(member);


        if (last == null) { // 최초 조회
            workScrapList = workScrapRepository.findTop5ByMemberEqualsOrderByCreatedDateDesc(member);
        } else {
            Optional<WorkScrap> lastWorkScrap = workScrapRepository.findById(Long.parseLong(last));
            LocalDateTime createdDate = LocalDateTime.now(); // 나중에 오류 분기처리 해야함
            // 이 부분 나중에 리팩토링
            if (lastWorkScrap.isPresent()) {
                WorkScrap workScrap = lastWorkScrap.get();
                createdDate = workScrap.getCreatedDate();
            }
            workScrapList = workScrapRepository.findTop5ByMemberAndCreatedDateLessThanOrderByCreatedDateDesc(member, createdDate);
        }

        for (WorkScrap workScrap : workScrapList) {
            Work work = workScrap.getWork();

            boolean userApplied = false;
            Optional<WorkApply> workApply = workApplyRepository.findByWorkAndMember(work, member);
            if(workApply.isPresent()){
                userApplied = true;
            }
            String date = work.getStartDate() + " ~ " + work.getEndDate();
            serviceObject serviceObject = new serviceObject(work.getTopImage(), profileUrl, nickname, work.getTitle(),
                    work.getArea(), date, work.getRecruitNum(), work.getDow(), work.getWorkId(), userApplied);
            serviceList.add(serviceObject);
        }
        int size = workScrapList.size();
        long lastWorkScrapId = workScrapList.get(size - 1).getWorkScrapId();
        return new myScrapReturnerDTO(myScrap, serviceList, lastWorkScrapId);
    }
}



