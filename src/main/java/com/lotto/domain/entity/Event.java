package com.lotto.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor @Getter
@Table(
        name = "tb_event",
        indexes = {
                @Index(name = "idx_event_dates", columnList = "start_at,end_at,announce_start_at,announce_end_at")
        }
)
public class Event {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

    @Column(name = "announce_start_at", nullable = false)
    private LocalDateTime announceStartAt;

    @Column(name = "announce_end_at", nullable = false)
    private LocalDateTime announceEndAt;


    public Event(String title, LocalDateTime startAt, LocalDateTime endAt, LocalDateTime announceStartAt,
                 LocalDateTime announceEndAt) {
        this.title = title;
        this.startAt = startAt;
        this.endAt = endAt;
        this.announceStartAt = announceStartAt;
        this.announceEndAt = announceEndAt;
    }
}
