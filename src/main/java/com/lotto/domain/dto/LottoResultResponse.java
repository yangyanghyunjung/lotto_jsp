package com.lotto.domain.dto;


public record LottoResultResponse(
        Integer viewCnt,
        Integer winRank,
        String message
) { }
