package com.lotto.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*  사전 지정 당첨자 - 운영팀에서 미리 지정한 1등 대상자 리스트입니다. */

@Entity
@Table(name = "tb_winner_whitelist")
@NoArgsConstructor
@Getter
public class WinnerWhiteList {
    @Id
    @Column(name = "phone_hash")
    private String phoneHash;

    @Column(name = "target_rank", nullable = false)
    private Integer targetRank = 1;

    @Column(name = "is_applied", nullable = false)
    private Boolean isApplied; // 당첨 적용 여부

    public void markAsApplied() {
        this.isApplied = true;
    }

    public WinnerWhiteList(String phoneHash, Boolean isApplied) {
        this.phoneHash = phoneHash;
        this.isApplied = isApplied;
    }

}
