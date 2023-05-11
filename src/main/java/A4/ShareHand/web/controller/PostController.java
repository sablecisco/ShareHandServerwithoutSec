package A4.ShareHand.web.controller;

import A4.ShareHand.domain.TestObj;
import A4.ShareHand.domain.dto.postRelatedDTO.ImageTestBody;
import A4.ShareHand.web.repository.PostTestRepository;
import A4.ShareHand.web.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Component
public class PostController {

    private final PostTestRepository postTestRepository;
    private final PostService postService;

//    @Value("${/var/images}")
//    private String fileDir;

    @PostMapping(value = "/user/image/test", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public int getImageTest(@RequestPart("files") List<MultipartFile> files, @RequestPart ImageTestBody imageTestBody) throws IOException {
        for (MultipartFile multipartFile : files) {
            // 1. 멀티파트에서 파일 이름, 저장할 파일 이름 가지고 오기
            String originalFilename = multipartFile.getOriginalFilename();
            String storeFileName = createStoreFileName(originalFilename);

            //2. 이전하기
            multipartFile.transferTo(new File(getFullPath(storeFileName)));
            System.out.println("storeFileName = " + storeFileName);
            //업로드하기
            System.out.println("storeFileName = " + storeFileName);
        }
        postTestRepository.save(new TestObj(imageTestBody));
        return 200;
    }

    @PostMapping("/user/google/image")
    public int uploadImageGoogle(@RequestPart List<MultipartFile> files, @RequestPart ImageTestBody imageTestBody) throws IOException {
        postService.uploadGoogle(files);
        postTestRepository.save(new TestObj(imageTestBody));
        return 200;
    }


    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    public String getFullPath(String filename) {
        return "/var/images" + filename;  // 나중에 파일 디렉토리 바꿔야 함
    }
}
