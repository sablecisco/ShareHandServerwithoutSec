package A4.ShareHand.web.service;


import A4.ShareHand.domain.dto.postRelatedDTO.FileDto;
import A4.ShareHand.web.utils.DataBucketUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final DataBucketUtil dataBucketUtil;
    private final String cloudURL = "https://storage.googleapis.com/sharehandpostiamge_test/";

    public void uploadGoogle(List<MultipartFile> files) throws IOException {

        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            Path path = new File(originalFilename).toPath();

            String contentType = Files.probeContentType(path);
            FileDto fileDto = dataBucketUtil.uploadFile(file, originalFilename, contentType);

            String fileUrl = fileDto.getFileUrl();
            String crop = crop(fileUrl);
            String new_fileUrl = cloudURL + crop;

            System.out.println("fileDto.getFileName() = " + fileDto.getFileName());
            System.out.println("reading url = " + new_fileUrl);
        }
    }

    private String crop(String fileUrl) {
        List<String> myList = new ArrayList<String>(Arrays.asList(fileUrl.split("/")));
        int size = myList.size();
        String s = myList.get(size - 1);
        List<String> myList2 = new ArrayList<String>(Arrays.asList(s.split("&")));
        return myList2.get(0);
    }
}

