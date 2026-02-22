package com.lotto.domain.dto;

import java.time.LocalDateTime;

public record EventCreateRequest(
        String title,
        LocalDateTime startAt,
        LocalDateTime endAt,
        LocalDateTime announceStartAt,
        LocalDateTime announceEndAt
) {}
