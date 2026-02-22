package com.lotto.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_001", "서버 내부 오류가 발생했습니다."),


    // ===== 참가자 =====
    DUPLICATE_PHONE_ENTRY(HttpStatus.CONFLICT, "PARTICIPANT_001", "이미 참여한 휴대폰 번호입니다."),
    PARTICIPANT_NOT_FOUND(HttpStatus.NOT_FOUND, "PARTICIPANT_002", "참가자를 찾을 수 없습니다."),
    PARTICIPANT_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "PARTICIPANT_003", "최대 참가자 수를 초과했습니다."),

    // ===== 이벤트 =====
    EVENT_CLOSED(HttpStatus.BAD_REQUEST, "EVENT_001", "이벤트가 마감되었습니다."),
    EVENT_ALREADY_COMPLETED(HttpStatus.BAD_REQUEST, "EVENT_002", "이미 추첨이 완료된 이벤트입니다."),
    EVENT_NOT_FOUND(HttpStatus.NOT_FOUND, "EVENT_003", "이벤트를 찾을 수 없습니다."),

    // ===== 슬롯 =====
    SLOT_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "SLOT_001", "해당 등수의 잔여 슬롯이 없습니다."),

    // ===== 해싱 =====
    HASH_EMPTY(HttpStatus.BAD_REQUEST, "HASH_001", "해싱할 텍스트가 비어있습니다."),
    HASH_ALGORITHM_NOT_FOUND(HttpStatus.NOT_FOUND, "HASH_002", "해싱 알고리즘을 찾을 수 없습니다"),

    // ===== 관리자 =====
    ADMIN_DUPLICATE_PHONE_ENTRY(HttpStatus.NOT_FOUND, "ADMIN_001", "이미 등록한 휴대폰 번호입니다.");

    private final HttpStatus status;    // HTTP 상태
    private final String code;          // API 응답에 사용할 커스텀 에러 코드 (HTTP 상태 코드와 동일하게)
    private final String message;       // API 응답에 사용할 에러 메시지
}

