package A4.ShareHand.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {

    private String nickname;
    private long level;
    private double avgRate;
    private String location;
    private int managedWork;
    private int appliedWork;
    private int participatedWork;
    private boolean isAuthor;
    private String profileUrl;
}