package A4.ShareHand.web.service;

import A4.ShareHand.domain.Member;
import A4.ShareHand.domain.Work;
import A4.ShareHand.domain.WorkApply;
import A4.ShareHand.domain.dto.calanderRelatedDTO.*;
import A4.ShareHand.web.repository.MemberRepository;
import A4.ShareHand.web.repository.WorkApplyRepository;
import A4.ShareHand.web.repository.WorkRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.IsoChronology;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final MemberRepository memberRepository;
    private final WorkApplyRepository workApplyRepository;

    @Transactional
    public TodayWorkReturnerDTO getTodayWorks(String email) {
        Member member = memberRepository.findByEmail(email);
        LocalDate today = LocalDate.now();

        List<SimpleWorkDTO> workList = new ArrayList<>();
        int workCounter = 0;

        List<WorkApply> myWorks = workApplyRepository.findByMember(member);

        for (WorkApply myWork : myWorks) {
            Work work = myWork.getWork();
            String startDate = work.getStartDate();
            String endDate = work.getEndDate();

            List<Integer> sdaySplitter = splitter(startDate);
            List<Integer> edaySplitter = splitter(endDate);
            LocalDate eventStart = LocalDate.of(sdaySplitter.get(0), sdaySplitter.get(1), sdaySplitter.get(2));
            LocalDate eventEnd = LocalDate.of(edaySplitter.get(0), edaySplitter.get(1), edaySplitter.get(2));

            ChronoLocalDate eventStartDate = getChronoLocalDate(eventStart);
            ChronoLocalDate eventEndDate = getChronoLocalDate(eventEnd);

            if (today.isEqual(eventStartDate) || today.isEqual(eventEndDate) ||
                    (today.isAfter(eventStartDate) && today.isBefore(eventEndDate))) {
                workList.add(new SimpleWorkDTO(work.getStartTime(), work.getTitle(), work.getArea()));
                workCounter++;
            }
        }
        return new TodayWorkReturnerDTO(workCounter, workList);
    }

    @Transactional
    public MonthlyWorkReturnerDTO getMonthlyWorks(String email, int year, int month) {
        Member member = memberRepository.findByEmail(email);
        List<WorkApply> myWorks = workApplyRepository.findByMember(member);
        List<DetailWorkDTOForMonthly> workList = new ArrayList<>();

        int workCounter = 0;

        for (WorkApply myWork : myWorks) {
            Work work = myWork.getWork();

            String startDate = work.getStartDate();
            String endDate = work.getEndDate();

            List<Integer> sdaySplitter = splitter(startDate);
            List<Integer> edaySplitter = splitter(endDate);

            int sYear = sdaySplitter.get(0);
            int sMonth = sdaySplitter.get(1);
            int sDay = sdaySplitter.get(2);

            int eYear = edaySplitter.get(0);
            int eMonth = edaySplitter.get(1);
            int eDay = edaySplitter.get(2);

            if (year == sYear && month == sMonth) {
                workCounter++;
                int today = LocalDate.now().getDayOfMonth();
                int dDay = today - sDay;
                workList.add(new DetailWorkDTOForMonthly(work.getWorkId(), work.getTitle(), work.getArea(), dDay, work.getDow(), work.getStartTime()));
            } else if (year == eYear && month == eMonth) {
                workCounter++;
                int today = LocalDate.now().getDayOfMonth();
                int dDay = today - eDay;
                workList.add(new DetailWorkDTOForMonthly(work.getWorkId(), work.getTitle(), work.getArea(), dDay, work.getDow(), work.getStartTime()));
            }
        }
        return new MonthlyWorkReturnerDTO(workCounter, workList);
    }

    @Transactional
    public DailyWorkReturnerDTO getDailyWorks(String email, int year, int month, int day) {
        Member member = memberRepository.findByEmail(email);
        List<WorkApply> myWorks = workApplyRepository.findByMember(member);
        List<DetailWorkDTOForDaily> workList = new ArrayList<>();

        LocalDate myTime = LocalDate.of(year, month, day);


        int workCounter = 0;

        for (WorkApply myWork : myWorks) {
            Work work = myWork.getWork();
            String startDate = work.getStartDate();
            String endDate = work.getEndDate();

            List<Integer> sdaySplitter = splitter(startDate);
            List<Integer> edaySplitter = splitter(endDate);
            LocalDate eventStart = LocalDate.of(sdaySplitter.get(0), sdaySplitter.get(1), sdaySplitter.get(2));
            LocalDate eventEnd = LocalDate.of(edaySplitter.get(0), edaySplitter.get(1), edaySplitter.get(2));

            ChronoLocalDate eventStartDate = getChronoLocalDate(eventStart);
            ChronoLocalDate eventEndDate = getChronoLocalDate(eventEnd);

            if (myTime.isEqual(eventStartDate) || myTime.isEqual(eventEndDate) ||
                    (myTime.isAfter(eventStartDate) && myTime.isBefore(eventEndDate))) {
                workList.add(new DetailWorkDTOForDaily(work.getWorkId(), work.getTitle(),
                        work.getMember().getMemberDetails().getNickname(), work.getArea(),
                        work.getStartTime(), work.getEndTime()));
                workCounter++;
            }
        }

        return new DailyWorkReturnerDTO(workCounter, workList);
    }

    private static ChronoLocalDate getChronoLocalDate(LocalDate event) {
        IsoChronology isoChronology = IsoChronology.INSTANCE;
        ChronoLocalDate chronoLocalDate = isoChronology.date(event);

        return chronoLocalDate;
    }

    private List<Integer> splitter(String date) {
        List<Integer> ydm = new ArrayList<>();

        ydm.add(Integer.parseInt(date.substring(0, 4)));
        ydm.add(Integer.parseInt(date.substring(6, 8)));
        ydm.add(Integer.parseInt(date.substring(10, 12)));
        return ydm;
    }


}

