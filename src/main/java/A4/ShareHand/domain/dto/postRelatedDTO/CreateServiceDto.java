package A4.ShareHand.domain.dto.postRelatedDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@RequiredArgsConstructor
public class CreateServiceDto {

    private String category;

    private String title;

    private String introduce;

    private String applyDeadline;

    private String area;

    private String startDate;

    private String endDate;

    private String dow;

    private String startTime;

    private String endTime;

    private int recruitNum;

    private int cost;

    private String content;

    private String extra;

    private String email;

    private String tel;

}
