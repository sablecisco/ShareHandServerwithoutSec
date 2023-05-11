package A4.ShareHand.web.service;

import A4.ShareHand.domain.*;
import A4.ShareHand.domain.dto.postRelatedDTO.*;
import A4.ShareHand.web.exception.WorkNotFoundException;
import A4.ShareHand.web.repository.*;
import A4.ShareHand.web.utils.DataBucketUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
@RequiredArgsConstructor
public class WorkService {

    private final WorkRepository workRepository;
    private final MemberRepository memberRepository;
    private final PhotoMetaDataRepository photoMetaDataRepository;
    private final DataBucketUtil dataBucketUtil;
    private String cloudURL = "https://storage.googleapis.com/sharehandpostiamge_test/";
    private final ReviewRepository reviewRepository;
    private final WorkApplyRepository workApplyRepository;
    private final WorkScrapRepository workScrapRepository;
    private final WorkLikeRepository workLikeRepository;

    private final Map<String, String> categoryList = new HashMap<String,String>(){{
        put("1","ALL");
        put("2","교육");
        put("3","문화");
        put("4","보건");
        put("5","환경");
        put("6","기술");
        put("7","기타");
    }};

    @Transactional
    public Work createService(String email, CreateServiceDto createServiceDto, List<MultipartFile> files) throws IOException {
        Member member = memberRepository.findByEmail(email);
        List<String> strings = new ArrayList<>();

        Work work = Work.builder()
                .member(member)
                .category(createServiceDto.getCategory())
                .title(createServiceDto.getTitle())
                .introduce(createServiceDto.getIntroduce())
                .applyDeadline(createServiceDto.getApplyDeadline())
                .area(createServiceDto.getArea())
                .startDate(createServiceDto.getStartDate())
                .endDate(createServiceDto.getEndDate())
                .dow(createServiceDto.getDow())
                .startTime(createServiceDto.getStartTime())
                .endTime(createServiceDto.getEndTime())
                .recruitNum(createServiceDto.getRecruitNum())
                .cost(createServiceDto.getCost())
                .content(createServiceDto.getContent())
                .extra(createServiceDto.getExtra())
                .email(createServiceDto.getEmail())
                .tel(createServiceDto.getTel())
                .status("Active")
                .build();

        String title = work.getTitle();
        System.out.println("title = " + title);

        Work savedWork = workRepository.save(work);

        if(files != null) {
            strings = uploadGoogle(files, savedWork);
        }
        String s = strings.get(0);
        work.setTopImage(strings.get(0));
        return work;
    }

    @Transactional
    public Member callMember(String email) {
        Member byEmail = memberRepository.findByEmail(email);
        MemberDetails memberDetails = byEmail.getMemberDetails();
        ProfilePhotoData profilePhotoData = byEmail.getProfilePhotoData();

        List<String> interests = memberDetails.getInterests();
        String url = profilePhotoData.getUrl();
        return byEmail;
    }



    public List<String> uploadGoogle(List<MultipartFile> files, Work work) throws IOException {
        long orderCounter = 0;
        List<String> urls = new ArrayList<>(); // 추후 삭제
        for (MultipartFile file : files) {
            orderCounter++;

            String originalFilename = file.getOriginalFilename();
            Path path = new File(originalFilename).toPath();

            String contentType = Files.probeContentType(path);
            FileDto fileDto = dataBucketUtil.uploadFile(file, originalFilename, contentType);

            String fileUrl = fileDto.getFileUrl();
            String crop = crop(fileUrl);
            String new_fileUrl = cloudURL + crop;

            PhotoMetaData pd = new PhotoMetaData(work, new_fileUrl, orderCounter, fileDto.getFileName());
            photoMetaDataRepository.save(pd);
            urls.add(new_fileUrl);
        }
        return urls;
    }

    private String crop(String fileUrl) {
        List<String> myList = new ArrayList<String>(Arrays.asList(fileUrl.split("/")));
        int size = myList.size();
        String s = myList.get(size - 1);
        List<String> myList2 = new ArrayList<String>(Arrays.asList(s.split("&")));
        return myList2.get(0);
    }

    @Transactional
    public WorkDetailDto getWork(String email, long workId) {
        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new WorkNotFoundException("해당 모집글 정보가 없습니다."));

        Member member = memberRepository.findByEmail(email);

        List<Review> threeReviewList = reviewRepository.findTop3ByOrderByCreatedDateDesc();
        List<ReviewListDto> threeReviewLists = new ArrayList<>();
        threeReviewList.stream().forEach((e) -> {
            threeReviewLists.add(ReviewListDto.of(e, work, member));
        });

        //지원했는지 여부 확인
        Optional<WorkApply> workApply = workApplyRepository.findByWorkAndMember(work, member);

        //스크랩했는지 여부 확인
        Optional<WorkScrap> workScrap = workScrapRepository.findByWorkAndMember(work, member);

        //좋아요했는지 여부 확인
        Optional<WorkLike> workLike = workLikeRepository.findByWorkAndMember(work, member);

        //저장되어 있는 이미지 가져오기
        List<PhotoMetaData> photoMetaDataList = photoMetaDataRepository.findByWork(work);
        List<String> photoList = new ArrayList<>();
        for (PhotoMetaData photoMetaData : photoMetaDataList) {
            photoList.add(photoMetaData.getUrl());
        }

        //중복 조회수 일단 허용
        work.plusHitCnt();

        WorkDetailDto workDetailDto = WorkDetailDto.builder()
                .userId(work.getMember().getId())
                .nickname(work.getMember().getMemberDetails().getNickname())
                .profileUrl(work.getMember().getProfilePhotoData().getUrl())
                .level(1) // 하드코딩했음 추후 수정
                .userRate(3.5) // 하드코딩했음 추후 수정
                .photoList(photoList)
                .workTitle(work.getTitle())
                .category(work.getCategory())
                .intro(work.getIntroduce())
                .applydeadline(work.getApplyDeadline())
                .area(work.getArea())
                .startDate(work.getStartDate())
                .endDate(work.getEndDate())
                .dow(work.getDow())
                .startTime(work.getStartTime())
                .endTime(work.getEndTime())
                .recruitNum(work.getRecruitNum())
                .cost(work.getCost())
                .content(work.getContent())
                .hitCnt(work.getHitCnt())
                .scrapCnt(work.getScrapCnt())
                .likeCnt(work.getLikeCnt())
                .tel(work.getTel())
                .email(work.getEmail())
                .contactEtc(work.getExtra())
                .isAuthor(work.getMember() == member)
                .didApply(workApply.isPresent())
                .didLike(workLike.isPresent())
                .didScrap(workScrap.isPresent())
                .status(work.getStatus().equals("Active"))
                .reviewLists(threeReviewLists)
                .build();

        return workDetailDto;

    }

    @Transactional
    public boolean applyWork(String email, long workId) {
        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new WorkNotFoundException("해당 모집글 정보가 없습니다."));

        Member member = memberRepository.findByEmail(email);

        //이미 지원한적이 있는지 확인하기
        Optional<WorkApply> optionalWorkApply = workApplyRepository.findByWorkAndMember(work, member);
        if (optionalWorkApply.isPresent()){
            return false;
        }

        WorkApply workApply = WorkApply.builder()
                .work(work)
                .member(member)
                .reviewPermission(false)
                .build();

        workApplyRepository.save(workApply);
        return true;
    }

    @Transactional
    public boolean cancelWork(String email, long workId) {
        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new WorkNotFoundException("해당 모집글 정보가 없습니다."));

        Member member = memberRepository.findByEmail(email);

        Optional<WorkApply> optionalWorkApply = workApplyRepository.findByWorkAndMember(work, member);
        if (!optionalWorkApply.isPresent()){
            return false;
        }
        WorkApply workApply = optionalWorkApply.get();
        workApplyRepository.delete(workApply);
        return true;
    }

    // 초기 10개 불러오기(카테고리:전체, sort:최신순)
    @Transactional
    public WorkDto getLatestWorks(String email) {
        Member member = memberRepository.findByEmail(email);
        long workListSize = workRepository.countByStatus("Active");
        List<Work> workList = workRepository.findTop10ByStatusEqualsOrderByCreatedDateDesc("Active");

        return getWorkLists(member, workListSize, workList);
    }

    // 다음 10개 불러오기(카테고리:전체, sort:최신순)
    @Transactional
    public WorkDto getNextWorks(String email, long last) {
        Member member = memberRepository.findByEmail(email);
        long workListSize = workRepository.countByStatus("Active");

        Work lastWork = workRepository.findById(last).orElse(null);
        if (lastWork == null){
            return getLatestWorks(email);
        }

        List<Work> workList = workRepository.findTop10ByStatusAndCreatedDateLessThanOrderByCreatedDateDesc("Active", lastWork.getCreatedDate());
        return getWorkLists(member, workListSize, workList);
    }

    // 초기 10개 불러오기(카테고리:전체, sort:조회순 or 스크랩순)
    @Transactional
    public WorkDto getLatestWorksSorted(String email, String sort) {
        Member member = memberRepository.findByEmail(email);
        long workListSize = workRepository.countByStatus("Active");

        List<Work> workList = new ArrayList<>();
        if (sort.equals("2")) {
            workList = workRepository.findTop10ByStatusEqualsOrderByHitCntDescCreatedDateDesc("Active");
        } else if (sort.equals("3")){
            workList = workRepository.findTop10ByStatusEqualsOrderByScrapCntDescCreatedDateDesc("Active");
        }
        return getWorkLists(member, workListSize, workList);
    }

    // 다음 10개 불러오기(카테고리:전체, sort:조회순 or 스크랩순)
    @Transactional
    public WorkDto getNextWorksSorted(String email, long last, String sort) {
        Member member = memberRepository.findByEmail(email);
        long workListSize = workRepository.countByStatus("Active");

        List<Work> workList = new ArrayList<>();

        Work lastWork = workRepository.findById(last).orElse(null);
        if (lastWork == null){
            if (sort.equals("2")) {
                workList = workRepository.findTop10ByStatusEqualsOrderByHitCntDescCreatedDateDesc("Active");
            } else if (sort.equals("3")){
                workList = workRepository.findTop10ByStatusEqualsOrderByScrapCntDescCreatedDateDesc("Active");
            }
        }
        if (sort.equals("2")) {
            workList = workRepository.findTop10ByStatusAndCreatedDateLessThanOrderByHitCntDescCreatedDateDesc("Active", lastWork.getCreatedDate());
        } else if(sort.equals("3")) {
            workList = workRepository.findTop10ByStatusAndCreatedDateLessThanOrderByScrapCntDescCreatedDateDesc("Active", lastWork.getCreatedDate());
        }
        return getWorkLists(member, workListSize, workList);

    }
    // 초기 10개 불러오기(카테고리:넘어온 값 , sort:최신순)
    @Transactional
    public WorkDto getLatestWorksByCategory(String email, String category) {
        Member member = memberRepository.findByEmail(email);
        long workListSize = workRepository.countByStatusAndCategory("Active", categoryList.get(category));
        List<Work> workList = workRepository.findTop10ByStatusAndCategoryOrderByCreatedDateDesc("Active", categoryList.get(category));

        return getWorkLists(member, workListSize, workList);
    }

    // 다음 10개 불러오기(카테고리:넘어온 값 , sort:최신순)
    @Transactional
    public WorkDto getNextWorksByCategory(String email, String category, long last) {
        Member member = memberRepository.findByEmail(email);
        long workListSize = workRepository.countByStatusAndCategory("Active", categoryList.get(category));
        Work lastWork = workRepository.findById(last).orElse(null);
        if (lastWork == null){
            return getLatestWorksByCategory(email, category);
        }
        List<Work> workList = workRepository
                .findTop10ByStatusAndCategoryAndCreatedDateLessThanOrderByCreatedDateDesc("Active", categoryList.get(category), lastWork.getCreatedDate());

        return getWorkLists(member, workListSize, workList);
    }

    // 초기 10개 불러오기(카테고리:넘어온 값 , sort:넘어온 값(조회순 or 스크랩순))
    @Transactional
    public WorkDto getLatestWorksSortedByCategory(String email, String category, String sort) {
        Member member = memberRepository.findByEmail(email);
        long workListSize = workRepository.countByStatusAndCategory("Active", categoryList.get(category));
        List<Work> workList = new ArrayList<>();
        if (sort.equals("2")){
            workList = workRepository.findTop10ByStatusAndCategoryOrderByHitCntDescCreatedDateDesc("Active", categoryList.get(category));
        } else if(sort.equals("3")){
            workList = workRepository.findTop10ByStatusAndCategoryOrderByScrapCntDescCreatedDateDesc("Active", categoryList.get(category));
        }

        return getWorkLists(member, workListSize, workList);
    }


    // 다음 10개 불러오기(카테고리:넘어온 값 , sort:넘어온 값(조회순 or 스크랩순))
    @Transactional
    public WorkDto getNextWorksSortedByCategory(String email, long last, String category, String sort) {
        Member member = memberRepository.findByEmail(email);
        long workListSize = workRepository.countByStatusAndCategory("Active", categoryList.get(category));
        List<Work> workList = new ArrayList<>();
        Work lastWork = workRepository.findById(last).orElse(null);

        if (lastWork == null){
            if (sort.equals("2")) {
                workList = workRepository.findTop10ByStatusAndCategoryOrderByHitCntDescCreatedDateDesc("Active", categoryList.get(category));
            } else if (sort.equals("3")){
                workList = workRepository.findTop10ByStatusAndCategoryOrderByScrapCntDescCreatedDateDesc("Active", categoryList.get(category));
            }
        }

        if (sort.equals("2")) {
            workList = workRepository
                    .findTop10ByStatusAndCategoryAndCreatedDateLessThanOrderByHitCntDescCreatedDateDesc("Active", categoryList.get(category), lastWork.getCreatedDate());
        } else if(sort.equals("3")) {
            workList = workRepository
                    .findTop10ByStatusAndCategoryAndCreatedDateLessThanOrderByScrapCntDescCreatedDateDesc("Active", categoryList.get(category), lastWork.getCreatedDate());
        }
        return getWorkLists(member, workListSize, workList);

    }

    @Transactional
    public WorkDto searchLatestWorks(String email, String keyword) {
        Member member = memberRepository.findByEmail(email);
        long workListSize = workRepository.countByStatusAndTitleContaining("Active", keyword);
        List<Work> workList = workRepository.findTop10ByStatusAndTitleContainingOrderByCreatedDateDesc("Active", keyword);
        return getWorkLists(member, workListSize, workList);
    }

    @Transactional
    public WorkDto searchNextWorks(String email, String keyword, Long last) {
        Member member = memberRepository.findByEmail(email);
        long workListSize = workRepository.countByStatusAndTitleContaining("Active", keyword);
        Work lastWork = workRepository.findById(last).orElse(null);
        List<Work> workList = workRepository.findTop10ByStatusAndCreatedDateLessThanAndTitleContainingOrderByCreatedDateDesc("Active", lastWork.getCreatedDate(), keyword);
        return getWorkLists(member, workListSize, workList);

    }

    private WorkDto getWorkLists(Member member, long workListSize, List<Work> workList) {
        List<WorkListDto> workLists = new ArrayList<>();

        workList.stream().forEach((e) -> {
            boolean userApplied = false;
            boolean isAuthor = false;

            Optional<WorkApply> workApply = workApplyRepository.findByWorkAndMember(e, member);
            if(workApply.isPresent()){
                userApplied = true;
            }
            if (e.getMember() == member) {
                isAuthor = true;
            }
            workLists.add(WorkListDto.of(e, userApplied, isAuthor));
        });
        WorkDto workDto = WorkDto.builder()
                .workCounter(workListSize)
                .workList(workLists).build();

        return workDto;
    }


    @Transactional
    public void deleteWork(String email, long workId) {
        Work work = workRepository.findByWorkId(workId);
        if (work.getMember() == memberRepository.findByEmail(email)) {
            work.changeStatus();
        }
    }

    public int checkStatus(long workId) {
        if (workRepository.findByWorkId(workId).getStatus() == "Active") {
            return 400;
        } else {
            return 200;
        }

    }
}
