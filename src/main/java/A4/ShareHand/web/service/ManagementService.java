package A4.ShareHand.web.service;

import A4.ShareHand.domain.Member;
import A4.ShareHand.domain.Work;
import A4.ShareHand.domain.WorkLike;
import A4.ShareHand.domain.WorkScrap;
import A4.ShareHand.web.repository.MemberRepository;
import A4.ShareHand.web.repository.WorkLikeRepository;
import A4.ShareHand.web.repository.WorkRepository;
import A4.ShareHand.web.repository.WorkScrapRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagementService {

    private final MemberRepository memberRepository;
    private final WorkRepository workRepository;
    private final WorkScrapRepository workScrapRepository;
    private final WorkLikeRepository workLikeRepository;

    @Transactional
    public long saveScrap(String email, long workId) {
        Member member = memberRepository.findByEmail(email);
        Work work = workRepository.findByWorkId(workId);

        WorkScrap workScrap = WorkScrap.builder()
                .member(member)
                .work(work)
                .build();

        if (workScrapRepository.findByWorkAndMember(work, member).isPresent()) {
            return 400;
        } else {
            WorkScrap save = workScrapRepository.save(workScrap);
            work.plusScrapCnt();
            return save.getWorkScrapId();
        }
    }

    @Transactional
    public long unScrap(String email, long workId) {
        Member member = memberRepository.findByEmail(email);
        Work work = workRepository.findByWorkId(workId);

        Optional<WorkScrap> workScrap = workScrapRepository.findByWorkAndMember(work, member);
        if (workScrap.isPresent()) {
            workScrapRepository.deleteById(workScrap.get().getWorkScrapId());
            work.minusScrapCnt();
            return 200;
        } else {
            return 400;
        }

    }

    @Transactional
    public long saveLike(String email, long workId) {
            Member member = memberRepository.findByEmail(email);
            Work work = workRepository.findByWorkId(workId);

            WorkLike workLike = WorkLike.builder()
                    .member(member)
                    .work(work)
                    .build();

            if (workLikeRepository.findByWorkAndMember(work, member).isPresent()) {
                return 400;
            }else {
                WorkLike save = workLikeRepository.save(workLike);
                work.plusLikeCnt();
                return save.getWorkLikeId();
            }

    }
    @Transactional
    public long unLike(String email, long workId){
        Member member = memberRepository.findByEmail(email);
        Work work = workRepository.findByWorkId(workId);

        Optional<WorkLike> workLike = workLikeRepository.findByWorkAndMember(work, member);
        if (workLike.isPresent()) {
            workLikeRepository.deleteById(workLike.get().getWorkLikeId());
            work.minusLikeCnt();
            return 200;
        }else{
            return 400;
        }
    }
    }



