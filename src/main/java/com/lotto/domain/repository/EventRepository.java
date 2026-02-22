package com.lotto.domain.repository;

import com.lotto.domain.entity.Event;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EventRepository extends JpaRepository<Event, Long> {
    /** 1. 현재 참여 가능한 이벤트 조회 (startAt <= now <= endAt) */
    @Query("SELECT e FROM Event e WHERE :now BETWEEN e.startAt AND e.endAt")
    Optional<Event> findParticipatingEvent(LocalDateTime now);

    /** 2. 진행 중이거나 발표 중인 이벤트 조회 (startAt <= now <= announceEndAt) */
    @Query("SELECT e FROM Event e WHERE :now BETWEEN e.startAt AND e.announceEndAt")
    Optional<Event> findActiveOrAnnouncingEvent(LocalDateTime now);
}
