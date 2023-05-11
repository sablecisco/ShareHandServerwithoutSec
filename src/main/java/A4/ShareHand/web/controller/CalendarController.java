package A4.ShareHand.web.controller;

import A4.ShareHand.domain.dto.calanderRelatedDTO.DailyWorkReturnerDTO;
import A4.ShareHand.domain.dto.calanderRelatedDTO.MonthlyWorkReturnerDTO;
import A4.ShareHand.domain.dto.calanderRelatedDTO.TodayWorkReturnerDTO;
import A4.ShareHand.web.config.auth.PrincipalDetails;
import A4.ShareHand.web.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping("/today")
    public TodayWorkReturnerDTO getTodayWorks(Authentication authentication) {
        String email = getEmail(authentication);
        return calendarService.getTodayWorks(email);
    }

    @GetMapping("/monthly")
    public MonthlyWorkReturnerDTO getMonthlyWorks(Authentication authentication,
                                                  @RequestParam int year, @RequestParam int month) {
        String email = getEmail(authentication);
        return calendarService.getMonthlyWorks(email, year, month);
    }

    @GetMapping("/daily")
    public DailyWorkReturnerDTO getDailyWorks(Authentication authentication,
                                                @RequestParam int year, @RequestParam int month, @RequestParam int day) {
        String email = getEmail(authentication);
        return calendarService.getDailyWorks(email, year, month, day);
    }

    public String getEmail(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return principal.getEmail();
    }
}
