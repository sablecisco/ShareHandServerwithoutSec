package A4.ShareHand.domain.dto.postRelatedDTO;

import A4.ShareHand.domain.PhotoMetaData;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class PhotoListDto {
    private String url;

    public static PhotoListDto of(PhotoMetaData e) {
        return PhotoListDto.builder()
                .url(e.getUrl()).build();
    }
}
