package A4.ShareHand.web.utils;

import A4.ShareHand.domain.dto.postRelatedDTO.FileDto;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.UUID;

@Component
public class DataBucketUtil {

    @Value("${gcp.config.file}")
    private String gcpConfigFile;

    @Value("${gcp.project.id}")
    private String gcpProjectId;

    @Value("${gcp.bucket.id}")
    private String gcpBucketId;

    private File file;

    public FileDto uploadFile(MultipartFile multipartFile, String fileName, String contentType) {

        try{
            // 1. 넘어온 파일 변환
            byte[] fileData = FileUtils.readFileToByteArray(convert(multipartFile));

            // 2. 인풋스트림에 넣기
            InputStream inputStream = new ClassPathResource(gcpConfigFile).getInputStream();

            // 3. 빌더 프로젝트 아이디 설정, 크레덴셜 설정
            StorageOptions options = StorageOptions.newBuilder().setProjectId(gcpProjectId)
                    .setCredentials(GoogleCredentials.fromStream(inputStream)).build();

            // 스토리지 옵션에서 스토리지 꺼냄(이름으로 찾는듰)
            Storage storage = options.getService();
            // 버켓 만들어서 스토리지에서 버켓 이름으로 꺼냄
            Bucket bucket = storage.get(gcpBucketId,Storage.BucketGetOption.fields());

            String uuid = UUID.randomUUID().toString(); // Google Cloud Storage에 저장될 파일 이름
            // 블롭 생성, 디렉토리 / 파일이름 + uuid
            Blob blob = bucket.create( fileName + "-" + uuid, fileData, contentType);

            if(blob != null){
                // url 반환
                file.delete();
                return new FileDto(blob.getName(), blob.getMediaLink());
            }

        } catch (IOException e) {
            throw new RuntimeException(e); // Exception 처리 해야함
        }
        throw new RuntimeException();
    }

    private File convert(MultipartFile multipartFile) throws IOException {
        file = new File(multipartFile.getOriginalFilename());
        InputStream inputStream = multipartFile.getInputStream();
        Files.copy(inputStream, file.toPath());
        return file;
    }

    // 이쪽코드는 저장을 해버림
    private File convertFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        FileOutputStream outputStream = new FileOutputStream(convertedFile);
        outputStream.write(file.getBytes());
        outputStream.close();
        return convertedFile;
    }
}
