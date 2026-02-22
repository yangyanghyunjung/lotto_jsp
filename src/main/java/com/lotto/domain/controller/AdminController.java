package com.lotto.domain.controller;

import com.lotto.domain.dto.EventCreateRequest;
import com.lotto.domain.dto.WhitelistRequest;
import com.lotto.domain.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    // 슬롯 초기화
    @GetMapping("/slots")
    public ResponseEntity<String> initSlot() {
        adminService.initSlot();
        return ResponseEntity.ok("슬롯이 정상적으로 초기화되었습니다.");
    }

    // 이벤트 등록
    @PostMapping("/events")
    public ResponseEntity<Long> createEvent(@RequestBody EventCreateRequest request) {
        Long eventId = adminService.createEvent(request);
        return ResponseEntity.ok(eventId);
    }

    // 1등 사전 지정자 등록
    @PostMapping("/whitelist")
    public ResponseEntity<String> registerWhitelist(@RequestBody WhitelistRequest req) {
        adminService.registerWhitelist(req.phoneNum());
        return ResponseEntity.ok("전화번호가 정상적으로 등록되었습니다.");
    }

}
