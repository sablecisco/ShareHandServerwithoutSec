package A4.ShareHand.domain.dto.postRelatedDTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class TestImageURLReturner {

    private List<String> urls;

    public TestImageURLReturner(List<String> urls) {
        this.urls = urls;
    }
}
