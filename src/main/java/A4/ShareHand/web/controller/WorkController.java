package A4.ShareHand.web.controller;

import A4.ShareHand.domain.Work;
import A4.ShareHand.domain.dto.postRelatedDTO.CreateServiceDto;
import A4.ShareHand.domain.dto.postRelatedDTO.WorkDetailDto;
import A4.ShareHand.domain.dto.postRelatedDTO.WorkDto;
import A4.ShareHand.web.config.auth.PrincipalDetails;
import A4.ShareHand.web.service.WorkService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class WorkController {

    private final WorkService workService;


    @PostMapping(value = "/service/new-service", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public int workServiceWithCloud(Authentication authentication, @RequestPart CreateServiceDto createServiceDto, @RequestPart(required = false) List<MultipartFile> files) throws IOException {
        String email = getEmail(authentication);
        Work service = workService.createService(email, createServiceDto, files);
        if (service != null) {
            return 200;
        } else {
            return 400;
        }
    }

    // 모집글 리스트 호출 ("/service?category=전체&sort=최신&last={전에 호출한 글의 마지막 id}")
    @GetMapping("/service")
    public ResponseEntity<?> getWorkList(Authentication authentication
            , @RequestParam(value = "category", defaultValue = "1") String category
            , @RequestParam(value = "sort", defaultValue = "1") String sort
            , @RequestParam(value = "last", required = false) Long last) {

        String email = getEmail(authentication);
        WorkDto workDto;
        if (category.equals("1")) {
            if (sort.equals("1")) {
                if (last == null) {
                    workDto = workService.getLatestWorks(email);
                } else {
                    workDto = workService.getNextWorks(email, last);
                }
            } else {
                if (last == null) {
                    workDto = workService.getLatestWorksSorted(email, sort);
                } else {
                    workDto = workService.getNextWorksSorted(email, last, sort);
                }
            }
        } else {
            if (sort.equals("1")) {
                if (last == null) {
                    workDto = workService.getLatestWorksByCategory(email, category);
                } else {
                    workDto = workService.getNextWorksByCategory(email, category, last);
                }
            } else {
                if (last == null) {
                    workDto = workService.getLatestWorksSortedByCategory(email, category, sort);
                } else {
                    workDto = workService.getNextWorksSortedByCategory(email, last, category, sort);
                }
            }
        }
        return new ResponseEntity<>(workDto, HttpStatus.OK);
    }

    @GetMapping("/service/search")
    public WorkDto searchWork(Authentication authentication
            , HttpServletRequest request
            , @RequestParam(value = "last", required = false) Long last){
        String email = getEmail(authentication);
        WorkDto workDto;
        String keyword = request.getParameter("keyword");
        if (last == null) {
            workDto = workService.searchLatestWorks(email, keyword);
        }else{
            workDto = workService.searchNextWorks(email, keyword, last);
        }
        return workDto;
    }

    @GetMapping("/service/{workId}")
    public WorkDetailDto getWork(Authentication authentication, @PathVariable long workId){
        String email = getEmail(authentication);
        WorkDetailDto workDetailDto = workService.getWork(email, workId);
        return workDetailDto;

    }

    @PostMapping("/service/{workId}/apply")
    public int applyWork(Authentication authentication, @PathVariable long workId){
        String email = getEmail(authentication);
        if(!workService.applyWork(email, workId)){
            return 400;
        }
        return 200;
    }

    @PostMapping("/service/{workId}/cancel")
    public int cancelWork(Authentication authentication, @PathVariable long workId){
        String email = getEmail(authentication);
        if(!workService.cancelWork(email, workId)){
            return 400;
        }
        return 200;
    }

    @PostMapping("/service/delete/{workId}")
    public int deleteWork(Authentication authentication, @PathVariable long workId){
        String email = getEmail(authentication);
        workService.deleteWork(email, workId);
        return workService.checkStatus(workId);
    }

    public String getEmail(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return principal.getEmail();
    }
}
