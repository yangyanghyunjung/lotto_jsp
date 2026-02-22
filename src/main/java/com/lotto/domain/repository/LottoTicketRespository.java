package com.lotto.domain.repository;

import com.lotto.domain.entity.LottoTicket;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LottoTicketRespository extends JpaRepository<LottoTicket, Long> {
    // 휴대폰 해시로 참여 여부를 확인합니다.
    Optional<LottoTicket> findByPhoneHash(String phoneHash);

    boolean existsByPhoneHash(String phoneHash);

    // [결과 발표용] 당첨자 중 아직 결과를 확인하지 않은 사용자 목록 조회 (배치용)
    List<LottoTicket> findAllByWinRankGreaterThanAndViewCnt(int rank, int viewCount);

    // 현재 총 참여자 수를 조회 (seq 생성 보조용)
    long count();
}
