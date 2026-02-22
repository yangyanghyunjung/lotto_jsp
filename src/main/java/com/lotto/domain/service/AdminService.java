package com.lotto.domain.service;

import com.lotto.domain.dto.EventCreateRequest;
import com.lotto.domain.entity.Event;
import com.lotto.domain.entity.WinnerWhiteList;
import com.lotto.domain.entity.WinningSequence;
import com.lotto.domain.repository.EventRepository;
import com.lotto.domain.repository.WinnerWhiteListRepository;
import com.lotto.domain.repository.WinningSequenceRepository;
import com.lotto.domain.util.HashUtil;
import com.lotto.global.exception.CustomException;
import com.lotto.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AdminService {
    private final WinningSequenceRepository winningSequenceRepository;
    private final EventRepository eventRepository;
    private final WinnerWhiteListRepository winnerWhiteListRepository;

    @Transactional
    public void initSlot() {
        if (winningSequenceRepository.count() > 0) return;

        List<Integer> allPool = new ArrayList<>();
        for (int i = 1; i <= 10000; i++) {
            allPool.add(i);
        }

        List<WinningSequence> finalEntities = new ArrayList<>();
        finalEntities.addAll(pickAndRemove(allPool, 2000, 7000, 5, 2));

        // 3등: 1000~8000번 사이 중 44개 선점
        finalEntities.addAll(pickAndRemove(allPool, 1000, 8000, 44, 3));

        // 1등: 남은 전체 중 1개 (지정번호 미참여 대비용)
        finalEntities.addAll(pickAndRemove(allPool, 1, 10000, 1, 1));

        // 4등: 남은 전체 중 950개
        finalEntities.addAll(pickAndRemove(allPool, 1, 10000, 950, 4));

        // 4. DB 일괄 저장 (Batch Insert)
        winningSequenceRepository.saveAll(finalEntities);
    }

    @Transactional
    public Long createEvent(EventCreateRequest request) {
        // 1. 날짜 유효성 검증
        validateEventDates(request);

        // 2. 엔티티 생성 및 저장
        Event event = new Event(
                request.title(),
                request.startAt(),
                request.endAt(),
                request.announceStartAt(),
                request.announceEndAt()
        );

        return eventRepository.save(event).getEventId();
    }

    private void validateEventDates(EventCreateRequest request) {
        if (request.startAt().isAfter(request.endAt())) {
            throw new IllegalArgumentException("이벤트 시작일은 종료일보다 빨라야 합니다.");
        }
        if (request.endAt().isAfter(request.announceStartAt())) {
            throw new IllegalArgumentException("결과 발표는 이벤트 종료 후 시작되어야 합니다.");
        }
    }

    private List<WinningSequence> pickAndRemove(List<Integer> pool, int min, int max, int count, int rank) {
        // 해당 범위 내에 있는 숫자들만 필터링
        List<Integer> candidates = pool.stream()
                .filter(n -> n >= min && n <= max)
                .collect(Collectors.toList());

        if (candidates.size() < count) {
            throw new RuntimeException(rank + "등 슬롯을 할당하기에 남은 번호가 부족합니다.");
        }

        // 섞어서 필요한 만큼 추출
        Collections.shuffle(candidates);
        List<Integer> selected = candidates.subList(0, count);

        // [중요] 전체 Pool에서 선택된 번호를 제거하여 다음 등수 생성 시 중복 방지
        pool.removeAll(selected);

        return selected.stream()
                .map(seq -> new WinningSequence(seq, rank))
                .collect(Collectors.toList());
    }

    public void registerWhitelist(String phoneNum) {
        String phoneHash = HashUtil.sha256(phoneNum);
        log.info("[registerWhitelist] raw='{}', len={}, bytes={}",
                phoneNum, phoneNum.length(), phoneNum.getBytes(StandardCharsets.UTF_8));

        if (winnerWhiteListRepository.existsById(phoneHash)) {
            throw new CustomException(ErrorCode.ADMIN_DUPLICATE_PHONE_ENTRY);
        }

        WinnerWhiteList winnerWhiteList = new WinnerWhiteList(phoneHash, false);

        log.info("whitelist-phonehash = {}", phoneHash);
        winnerWhiteListRepository.save(winnerWhiteList);
    }
}
