package com.lotto.domain.service;

import com.lotto.domain.EventStatus;
import com.lotto.domain.dto.LottoResultResponse;
import com.lotto.domain.entity.Event;
import com.lotto.domain.entity.LottoTicket;
import com.lotto.domain.entity.WinnerWhiteList;
import com.lotto.domain.entity.WinningSequence;
import com.lotto.domain.repository.EventRepository;
import com.lotto.domain.repository.LottoTicketRespository;
import com.lotto.domain.repository.WinnerWhiteListRepository;
import com.lotto.domain.repository.WinningSequenceRepository;
import com.lotto.domain.util.HashUtil;
import com.lotto.domain.util.LottoGenerator;
import com.lotto.global.exception.CustomException;
import com.lotto.global.exception.ErrorCode;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {
    private final WinningSequenceRepository winningSequenceRepository;
    private final WinnerWhiteListRepository winnerWhiteListRepository;
    private final LottoTicketRespository lottoTicketRespository;
    private final EventRepository eventRepository;

    @Transactional
    public LottoTicket participate(String phoneNum) {
        validateEventPeriod();
        String phoneHash = HashUtil.sha256(phoneNum);
        if (lottoTicketRespository.existsByPhoneHash(phoneHash)) {
            throw new CustomException(ErrorCode.DUPLICATE_PHONE_ENTRY);
        }

        // 참가 순번
        int seq = (int) lottoTicketRespository.count() + 1;
        if (seq >= 10000) {
            throw new CustomException(ErrorCode.PARTICIPANT_LIMIT_EXCEEDED);
        }
        // 당첨 등수 결정
        int rank = determineWinRank(phoneHash, seq);

        // 번호 생성
        List<Integer> numbers = LottoGenerator.generate(rank);

        // phonNum이 지정된거면 rank는 1
        LottoTicket lottoTicket = LottoTicket.builder()
                .phoneHash(phoneHash)
                .entryOrder(seq)
                .numbers(numbers)
                .winRank(rank)
                .build();

        log.info("참여 성공: 순번={}, 등수={}", seq, rank);
        return lottoTicketRespository.save(lottoTicket);
    }

    // 결과 확인(발표)
    @Transactional
    public LottoResultResponse checkResult(String phoneNum) {
        String phoneHash = HashUtil.sha256(phoneNum);
        LottoTicket ticket = lottoTicketRespository.findByPhoneHash(phoneHash)
                .orElseThrow(() -> new CustomException(ErrorCode.PARTICIPANT_NOT_FOUND));

        ticket.increaseViewCount();

        boolean isWinner = ticket.getWinRank() > 0;
        if (ticket.getViewCnt() == 1) {
            return new LottoResultResponse(ticket.getViewCnt(), ticket.getWinRank(), null);
        } else {
            return new LottoResultResponse(ticket.getViewCnt(), null, isWinner ? "당첨" : "미당첨");
        }
    }

    private int determineWinRank(String phoneHash,int seq) {
        // 사전 1등 확인
        Optional<WinnerWhiteList> whiteListOpt = winnerWhiteListRepository.findByPhoneHashAndIsAppliedFalse(
                phoneHash);
        if (whiteListOpt.isPresent()) {
            WinnerWhiteList target = whiteListOpt.get();
            target.markAsApplied();
            return target.getTargetRank();
        }

        // 2~4
        Optional<WinningSequence> winOpt = winningSequenceRepository.findByTargetSeqWithLock(
                seq);
        if (winOpt.isPresent()) {
            WinningSequence winningSequence = winOpt.get();
            winningSequence.markAsIssued();
            return winningSequence.getWinRank();
        }

        return 0;
    }

    private void validateEventPeriod() {
        eventRepository.findParticipatingEvent(LocalDateTime.now())
                .orElseThrow(() -> new CustomException(ErrorCode.EVENT_CLOSED));
    }

    public EventStatus getEventStatus(LocalDateTime now) {
        Optional<Event> eventOpt = eventRepository.findActiveOrAnnouncingEvent(now);

        if (eventOpt.isEmpty()) {
            log.info("eventOpt is Empty!!!!!!!!");

            return EventStatus.CLOSED;
        }

        Event event = eventOpt.get();
        if (now.isAfter(event.getStartAt()) && now.isBefore(event.getEndAt())) {
            return EventStatus.PARTICIPATING;
        } else if (now.isAfter(event.getAnnounceStartAt()) && now.isBefore(event.getAnnounceEndAt())) {
            return EventStatus.ANNOUNCING;
        }
        return EventStatus.CLOSED;
    }

}
