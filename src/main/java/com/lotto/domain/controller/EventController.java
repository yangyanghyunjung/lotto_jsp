package com.lotto.domain.controller;

import com.lotto.domain.EventStatus;
import com.lotto.domain.dto.LottoResultResponse;
import com.lotto.domain.entity.LottoTicket;
import com.lotto.domain.service.EventService;
import com.lotto.global.exception.CustomException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class EventController {
    private final EventService eventService;

    /** 메인  페이지 */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /** 홈 화면  */
    @GetMapping("/event/lotto")
    public String participatePage() {
        LocalDateTime now = LocalDateTime.now();
        EventStatus status = eventService.getEventStatus(now);

        log.info("status: {}", status.name());
        if (status == EventStatus.PARTICIPATING) {
            return "event/lotto"; // 참여 폼
        } else if (status == EventStatus.ANNOUNCING) {
            return "event/result-form"; // 결과 조회 폼 (휴대폰 번호 입력창)
        }

        return "redirect:/";
    }

    @PostMapping("/event/lotto/participate")
    public String issue(@RequestParam("phoneNum") String phoneNum, Model model) {
        try {
            LottoTicket ticket = eventService.participate(phoneNum);
            model.addAttribute("message", "로또가 정상적으로 발권되었습니다.");
            model.addAttribute("resOrder", ticket.getEntryOrder());
            model.addAttribute("status", "DONE");
            return "event/participate-result";
        } catch (CustomException e) {
            model.addAttribute("errorMessage", e.getErrorCode().getMessage());
            return "event/lotto";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "알 수 없는 오류가 발생했습니다.");
            return "event/lotto";
        }
    }

    @PostMapping("/event/lotto/result")
    public String getResult(@RequestParam("phoneNum") String phoneNum, Model model) {
        try {
            LottoResultResponse result = eventService.checkResult(phoneNum);

            model.addAttribute("result", result);
            model.addAttribute("status", "DONE");
            return "event/result";
        }  catch (CustomException e) {
            model.addAttribute("errorMessage", e.getErrorCode().getMessage());
            return "event/lotto";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "알 수 없는 오류가 발생했습니다.");
            return "event/lotto";
        }
    }
}
